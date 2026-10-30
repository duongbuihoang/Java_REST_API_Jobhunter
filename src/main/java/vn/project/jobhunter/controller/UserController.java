package vn.project.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.project.jobhunter.domain.User;
import vn.project.jobhunter.repository.UserRepository;
import vn.project.jobhunter.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/create")
    public String createNewUser() {
        User user = new User();
        user.setUsername("duongbui");
        user.setPassword("123456");
        user.setEmail("duongbui@example.com");

        this.userService.handleCreateUser(user);

        return "User created successfully!";
    }
}
