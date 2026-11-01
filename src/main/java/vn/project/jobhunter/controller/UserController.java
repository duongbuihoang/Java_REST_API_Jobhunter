package vn.project.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.project.jobhunter.domain.User;
import vn.project.jobhunter.repository.UserRepository;
import vn.project.jobhunter.service.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // @GetMapping("/user/create")
    @PostMapping("/user")
    public User createNewUser(
            @RequestBody User postManUser) {

        // User newUser = new User();
        // user.setUsername("duongbui");
        // user.setPassword("123456");
        // user.setEmail("duongbui@example.com");

        User abcUser = this.userService.handleCreateUser(postManUser);

        return abcUser;
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        this.userService.handleDeleteUser(id);
        return "ericUser";
    }

    // fetch user by id
    @GetMapping("user/{id}")
    public User getUserById(@PathVariable("id") long id) {
        return this.userService.fetchUserById(id);
    }

    // fetch all user
    @GetMapping("/user")
    public List<User> getAllUsers() {
        return this.userService.fetchAllUser();
    }

    // update user info
    @PutMapping("/user")
    public User updateUserInfo(@RequestBody User user) {
        User updatedUser = this.userService.handleUpdateUser(user);
        return updatedUser;
    }

}
