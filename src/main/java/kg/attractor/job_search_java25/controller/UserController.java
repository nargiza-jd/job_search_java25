package kg.attractor.job_search_java25.controller;

import kg.attractor.job_search_java25.exceptions.UserNotFoundException;
import kg.attractor.job_search_java25.model.User;
import kg.attractor.job_search_java25.service.UserService;
import kg.attractor.job_search_java25.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final FileUtil fileUtil;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/{userId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadUserAvatar(@PathVariable int userId, @RequestParam("file") MultipartFile file) {
        try {
            User user = userService.getUserById(userId);
            String filename = fileUtil.saveFile(file, "avatars");
            user.setAvatar(filename);
            userService.updateUser(userId, user);
            return ResponseEntity.ok("Аватар успешно загружен: " + filename);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
        }
    }

    @GetMapping("/{userId}/avatar")
    public ResponseEntity<?> downloadUserAvatar(@PathVariable int userId) {
        try {
            User user = userService.getUserById(userId);
            String avatarFilename = user.getAvatar();

            if (avatarFilename == null || avatarFilename.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Аватар не установлен для этого пользователя");
            }

            MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
            if (avatarFilename.endsWith(".jpg") || avatarFilename.endsWith(".jpeg")) {
                mediaType = MediaType.IMAGE_JPEG;
            } else if (avatarFilename.endsWith(".png")) {
                mediaType = MediaType.IMAGE_PNG;
            } else if (avatarFilename.endsWith(".gif")) {
                mediaType = MediaType.IMAGE_GIF;
            }

            return fileUtil.getFile(avatarFilename, "avatars", mediaType);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchApplicants(@RequestParam String query) {
        return ResponseEntity.ok(userService.searchApplicants(query));
    }

    @GetMapping("/search/phone")
    public ResponseEntity<List<User>> searchByPhone(@RequestParam String phone) {
        return ResponseEntity.ok(userService.findByPhoneNumber(phone));
    }

    @GetMapping("/search/email")
    public ResponseEntity<User> searchByEmail(@RequestParam String email) {
        Optional<User> user = Optional.ofNullable(userService.findByEmail(email));
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<User>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(userService.findByName(name));
    }
}