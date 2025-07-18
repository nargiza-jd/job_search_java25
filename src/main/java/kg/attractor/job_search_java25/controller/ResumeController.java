package kg.attractor.job_search_java25.controller;

import kg.attractor.job_search_java25.model.Resume;
import kg.attractor.job_search_java25.service.ResumeService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Resume>> getByCategory(@PathVariable int categoryId) {
        return ResponseEntity.ok(resumeService.getResumesByCategory(categoryId));
    }

    @GetMapping("/author/{applicantId}")
    public ResponseEntity<List<Resume>> getByApplicant(@PathVariable int applicantId) {
        return ResponseEntity.ok(resumeService.getResumesByApplicantId(applicantId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resume> getById(@PathVariable int id) {
        return resumeService.getResumeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}