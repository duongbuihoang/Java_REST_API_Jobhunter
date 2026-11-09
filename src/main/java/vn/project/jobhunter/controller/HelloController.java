package vn.project.jobhunter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    // @CrossOrigin
    public ResponseEntity<String> getHelloWorld() {
        return ResponseEntity.ok("Hello World from Spring Boot!");
    }
}
