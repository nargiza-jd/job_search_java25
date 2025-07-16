package kg.attractor.job_search_java25.controller;

import kg.attractor.job_search_java25.model.RespondedApplicant;
import kg.attractor.job_search_java25.service.RespondedApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/responses")
@RequiredArgsConstructor
public class RespondedApplicantController {

    private final RespondedApplicantService service;

    @PostMapping
    public ResponseEntity<RespondedApplicant> respond(
            @RequestParam int resumeId,
            @RequestParam int vacancyId) {
        return ResponseEntity.ok(service.createResponse(resumeId, vacancyId));
    }

    @GetMapping
    public ResponseEntity<List<RespondedApplicant>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/vacancy/{vacancyId}")
    public ResponseEntity<List<RespondedApplicant>> getByVacancy(@PathVariable int vacancyId) {
        return ResponseEntity.ok(service.getByVacancyId(vacancyId));
    }

    @GetMapping("/resume/{resumeId}")
    public ResponseEntity<List<RespondedApplicant>> getByResume(@PathVariable int resumeId) {
        return ResponseEntity.ok(service.getByResumeId(resumeId));
    }
}
