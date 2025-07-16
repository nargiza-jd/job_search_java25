package kg.attractor.job_search_java25.controller;

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
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final FileUtil fileUtil;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return userService.getUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
        return userService.updateUser(id, user)
                .map(updatedUser -> new ResponseEntity<>(updatedUser, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        if (userService.deleteUser(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/{userId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadUserAvatar(@PathVariable int userId, @RequestParam("file") MultipartFile file) {
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>("Пользователь не найден", HttpStatus.NOT_FOUND);
        }

        String filename = fileUtil.saveFile(file, "avatars");
        User user = userOpt.get();
        user.setAvatar(filename);
        userService.updateUser(userId, user);

        return new ResponseEntity<>("Аватар успешно загружен: " + filename, HttpStatus.OK);
    }

    @GetMapping("/{userId}/avatar")
    public ResponseEntity<?> downloadUserAvatar(@PathVariable int userId) {
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>("Пользователь не найден", HttpStatus.NOT_FOUND);
        }

        User user = userOpt.get();
        String avatarFilename = user.getAvatar();

        if (avatarFilename == null || avatarFilename.isEmpty()) {
            return new ResponseEntity<>("Аватар не установлен для этого пользователя", HttpStatus.NO_CONTENT);
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
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchApplicants(@RequestParam String query) {
        return ResponseEntity.ok(userService.searchApplicants(query));
    }
}