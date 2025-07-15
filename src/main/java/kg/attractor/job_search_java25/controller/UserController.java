package kg.attractor.job_search_java25.controller;

import kg.attractor.job_search_java25.dto.UserRegistrationDto;
import kg.attractor.job_search_java25.model.User;
import kg.attractor.job_search_java25.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")




public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRegistrationDto userDto) {
        userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created");
    }
}