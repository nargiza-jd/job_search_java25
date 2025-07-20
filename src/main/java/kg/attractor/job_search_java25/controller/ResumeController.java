package kg.attractor.job_search_java25.controller;

import kg.attractor.job_search_java25.dto.ResumeCreateDto;
import kg.attractor.job_search_java25.dto.ResumeUpdateDto;
import kg.attractor.job_search_java25.model.Resume;
import kg.attractor.job_search_java25.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @GetMapping
    public ResponseEntity<List<Resume>> getAll() {
        return ResponseEntity.ok(resumeService.getAllResumes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resume> getById(@PathVariable int id) {
        return resumeService.getResumeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Resume>> getByCategory(@PathVariable int categoryId) {
        return ResponseEntity.ok(resumeService.findByCategory(categoryId));
    }

    @GetMapping("/author/{applicantId}")
    public ResponseEntity<List<Resume>> getByApplicant(@PathVariable int applicantId) {
        return ResponseEntity.ok(resumeService.getResumesByApplicantId(applicantId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Resume>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(resumeService.searchByName(name));
    }

    @PostMapping
    public ResponseEntity<Resume> createResume(@RequestBody ResumeCreateDto dto) {
        Resume created = resumeService.createResume(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resume> updateResume(@PathVariable int id, @RequestBody ResumeUpdateDto dto) {
        return resumeService.updateResume(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable int id) {
        boolean deleted = resumeService.deleteResume(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}