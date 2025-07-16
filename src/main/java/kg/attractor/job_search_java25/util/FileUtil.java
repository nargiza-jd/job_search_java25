package kg.attractor.job_search_java25.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kg.attractor.job_search_java25.model.RespondedApplicant;
import kg.attractor.job_search_java25.model.Resume;
import kg.attractor.job_search_java25.model.User;
import kg.attractor.job_search_java25.model.Vacancy;
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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class FileUtil {

    private final ObjectMapper objectMapper;

    public FileUtil() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public List<Resume> loadResumes() {
        return loadList("data/resumes.json", new TypeReference<>() {});
    }

    public void saveResumes(List<Resume> resumes) {
        saveList("data/resumes.json", resumes);
    }

    public List<User> loadUsers() {
        return loadList("data/users.json", new TypeReference<>() {});
    }

    public void saveUsers(List<User> users) {
        saveList("data/users.json", users);
    }

    public List<Vacancy> loadVacancies() {
        return loadList("data/vacancies.json", new TypeReference<>() {});
    }

    public void saveVacancies(List<Vacancy> vacancies) {
        saveList("data/vacancies.json", vacancies);
    }

    @SneakyThrows
    private <T> List<T> loadList(String path, TypeReference<List<T>> typeRef) {
        File file = new File(path);
        if (!file.exists()) {
            return Collections.emptyList();
        }
        return objectMapper.readValue(file, typeRef);
    }

    @SneakyThrows
    private <T> void saveList(String path, List<T> list) {
        File file = new File(path);
        objectMapper.writeValue(file, list);
    }

    public <T> int generateId(List<T> list) {
        return list.size() == 0 ? 1 : list.size() + 1;
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

        Path pathDir = Paths.get("uploads/" + subDir);
        Files.createDirectories(pathDir);

        Path filePath = pathDir.resolve(resultFileName);
        try (OutputStream os = Files.newOutputStream(filePath)) {
            os.write(file.getBytes());
        }

        return resultFileName;
    }

    @SneakyThrows
    public ResponseEntity<?> getFile(String filename, String subDir, MediaType mediaType) {
        try {
            Path filePath = Paths.get("uploads/" + subDir + "/" + filename);
            byte[] fileBytes = Files.readAllBytes(filePath);
            Resource resource = new ByteArrayResource(fileBytes);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .contentLength(resource.contentLength())
                    .contentType(mediaType)
                    .body(resource);
        } catch (NoSuchFileException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Файл не найден");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при чтении файла");
        }
    }

    public List<RespondedApplicant> loadRespondedApplicants() {
        return loadList("responded_applicants.json", new TypeReference<>() {});
    }

    public void saveRespondedApplicants(List<RespondedApplicant> applicants) {
        saveList("responded_applicants.json", applicants);
    }
}