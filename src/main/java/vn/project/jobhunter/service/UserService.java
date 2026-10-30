package vn.project.jobhunter.service;

import org.springframework.web.bind.annotation.RestController;

import vn.project.jobhunter.domain.User;
import vn.project.jobhunter.repository.UserRepository;

@RestController
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void handleCreateUser(User user) {
        this.userRepository.save(user);
    }

}
