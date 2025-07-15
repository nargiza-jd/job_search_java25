package kg.attractor.job_search_java25.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import kg.attractor.job_search_java25.model.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class FileUtil {

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private final AtomicInteger maxUserId = new AtomicInteger(0);

    private final String UPLOAD_BASE_DIR = "uploads/";
    private final String USER_DATA_PATH = "data/users.json";

    public FileUtil() {
        createDirIfNotExists("data");
        createDirIfNotExists(UPLOAD_BASE_DIR + "avatars");
    }

    private void createDirIfNotExists(String path) {
        try {
            Files.createDirectories(Paths.get(path));
            log.info("Папка проверена или создана: {}", path);
        } catch (IOException e) {
            log.error("Не удалось создать папку {}: {}", path, e.getMessage());
        }
    }

    public List<User> loadUsers() {
        Type listType = new TypeToken<Map<String, List<User>>>() {}.getType();
        try (Reader reader = new FileReader(USER_DATA_PATH)) {
            Map<String, List<User>> data = gson.fromJson(reader, listType);
            List<User> users = data != null && data.containsKey("users") ? data.get("users") : Collections.emptyList();

            users.forEach(user -> {
                if (user.getId() > maxUserId.get()) {
                    maxUserId.set(user.getId());
                }
            });
            maxUserId.incrementAndGet();
            log.info("Загружено {} пользователей", users.size());
            return users;
        } catch (IOException e) {
            log.error("Ошибка загрузки users.json: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public void saveUsers(List<User> users) {
        Map<String, List<User>> data = Collections.singletonMap("users", users);
        try (Writer writer = new FileWriter(USER_DATA_PATH)) {
            gson.toJson(data, writer);
            log.info("Пользователи успешно сохранены: {} записей", users.size());
        } catch (IOException e) {
            log.error("Ошибка сохранения users.json: {}", e.getMessage());
        }
    }

    public int getNextUserId() {
        return maxUserId.getAndIncrement();
    }

    @SneakyThrows
    public String saveFile(MultipartFile file, String subDir) {
        String uuidFile = UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String resultFileName = uuidFile + fileExtension;

        Path pathDir = Paths.get(UPLOAD_BASE_DIR + subDir);
        Files.createDirectories(pathDir);

        Path filePath = pathDir.resolve(resultFileName);
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }

        try (OutputStream os = Files.newOutputStream(filePath)) {
            os.write(file.getBytes());
            log.info("Файл {} успешно сохранен в {}", resultFileName, pathDir);
        } catch (IOException e) {
            log.error("Ошибка при сохранении файла: {}", e.getMessage());
            throw new IOException("Не удалось сохранить файл", e);
        }

        return resultFileName;
    }

    @SneakyThrows
    public ResponseEntity<?> getFile(String filename, String subDir, MediaType mediaType) {
        try {
            Path filePath = Paths.get(UPLOAD_BASE_DIR + subDir + "/" + filename);
            byte[] fileBytes = Files.readAllBytes(filePath);
            Resource resource = new ByteArrayResource(fileBytes);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .contentLength(resource.contentLength())
                    .contentType(mediaType)
                    .body(resource);
        } catch (NoSuchFileException e) {
            log.warn("Файл не найден: {}", filename);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Файл не найден");
        } catch (IOException e) {
            log.error("Ошибка при чтении файла: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка сервера при получении файла");
        }
    }
}