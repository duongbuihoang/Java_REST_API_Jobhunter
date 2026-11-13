package vn.project.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import vn.project.jobhunter.domain.User;
import vn.project.jobhunter.domain.dto.ResultPaginationDTO;
import vn.project.jobhunter.repository.UserRepository;
import vn.project.jobhunter.service.UserService;
import vn.project.jobhunter.util.annotation.ApiMessage;
import vn.project.jobhunter.util.error.IdInvalidException;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // @GetMapping("/users/create")
    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@RequestBody User postManUser) {
        String hashPassword = this.passwordEncoder.encode(postManUser.getPassword());
        postManUser.setPassword(hashPassword);

        // User newUser = new User();
        // user.setUsername("duongbui");
        // user.setPassword("123456");
        // user.setEmail("duongbui@example.com");
        User abcUser = this.userService.handleCreateUser(postManUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(abcUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) throws IdInvalidException {
        if (id <= 0) {
            throw new IdInvalidException("Invalid user ID");
        }

        this.userService.handleDeleteUser(id);
        // return ResponseEntity.status(HttpStatus.OK).body("abcUser");
        return ResponseEntity.ok("abcUser");
    }

    // fetch user by id
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        User fetchedUser = this.userService.fetchUserById(id);
        // return ResponseEntity.ok(this.userService.fetchUserById(id));
        return ResponseEntity.status(HttpStatus.OK).body(fetchedUser);
    }

    // fetch all user
    @GetMapping("/users")
    @ApiMessage("Fetch all users with optional filtering and pagination")
    public ResponseEntity<ResultPaginationDTO> getAllUsers(
            @Filter Specification<User> specification,
            Pageable pageable) {

        // @RequestParam("current") Optional<String> currentOptional,
        // @RequestParam("pageSize") Optional<String> pageSizeOptional){

        // String current = currentOptional.isPresent() ? currentOptional.get() : "";
        // String pageSize = pageSizeOptional.isPresent() ? pageSizeOptional.get() : "";
        // Pageable pageable = PageRequest.of(Integer.parseInt(current) - 1,
        // Integer.parseInt(pageSize));

        return ResponseEntity.ok(this.userService.fetchAllUser(specification, pageable));
    }

    // update user info
    @PutMapping("/users")
    public ResponseEntity<User> updateUserInfo(@RequestBody User user) {
        // User updatedUser = this.userService.handleUpdateUser(user);
        // return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.handleUpdateUser(user));
    }

}
