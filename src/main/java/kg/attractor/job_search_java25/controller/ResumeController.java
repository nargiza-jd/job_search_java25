package kg.attractor.job_search_java25.controller;

import kg.attractor.job_search_java25.dto.ResumeCreateDto;
import kg.attractor.job_search_java25.dto.ResumeUpdateDto;
import kg.attractor.job_search_java25.model.Resume;
import kg.attractor.job_search_java25.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping
    public ResponseEntity<Resume> create(@RequestBody ResumeCreateDto dto) {
        return ResponseEntity.ok(resumeService.createResume(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resume> update(@PathVariable int id,
                                         @RequestBody ResumeUpdateDto dto) {
        return resumeService.updateResume(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return resumeService.deleteResume(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Resume>> getAll() {
        return ResponseEntity.ok(resumeService.getAllResumes());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Resume>> getByCategory(@PathVariable int categoryId) {
        return ResponseEntity.ok(resumeService.getResumesByCategoryId(categoryId));
    }

    @GetMapping("/author/{applicantId}")
    public ResponseEntity<List<Resume>> getByApplicant(@PathVariable int applicantId) {
        return ResponseEntity.ok(resumeService.getResumesByAuthorId(applicantId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resume> getById(@PathVariable int id) {
        return resumeService.getResumeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}