package vn.project.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import vn.project.jobhunter.domain.User;
import vn.project.jobhunter.domain.dto.Meta;
import vn.project.jobhunter.domain.dto.ResultPaginationDTO;
import vn.project.jobhunter.repository.UserRepository;

@RestController
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    public void handleDeleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    public User handleUpdateUser(User reqUser) {
        User currentuser = this.fetchUserById(reqUser.getId());
        if (currentuser != null) {
            currentuser.setUsername(reqUser.getUsername());
            currentuser.setPassword(reqUser.getPassword());
            currentuser.setEmail(reqUser.getEmail());
            // update
            currentuser = this.userRepository.save(currentuser);
        }
        return currentuser;
    }

    public User fetchUserById(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            return null;
        }
    }

    public ResultPaginationDTO fetchAllUser(Pageable pageable) {
        Page<User> userPage = this.userRepository.findAll(pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        Meta meta = new Meta();

        meta.setPage(userPage.getNumber() + 1);
        meta.setPageSize(userPage.getSize());

        meta.setPages(userPage.getTotalPages());
        meta.setTotal(userPage.getTotalElements());

        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(userPage.getContent());

        return resultPaginationDTO;
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByEmail(username);
    }

}
