package vn.project.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.project.jobhunter.domain.User;
import vn.project.jobhunter.repository.UserRepository;
import vn.project.jobhunter.service.UserService;
import vn.project.jobhunter.util.error.IdInvalidException;

import java.util.List;

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
import org.springframework.web.bind.annotation.PutMapping;

@RestController
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
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(this.userService.fetchAllUser());
    }

    // update user info
    @PutMapping("/users")
    public ResponseEntity<User> updateUserInfo(@RequestBody User user) {
        // User updatedUser = this.userService.handleUpdateUser(user);
        // return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.handleUpdateUser(user));
    }

}
