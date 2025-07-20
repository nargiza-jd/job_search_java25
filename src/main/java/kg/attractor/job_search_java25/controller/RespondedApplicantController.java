package kg.attractor.job_search_java25.controller;

import kg.attractor.job_search_java25.model.RespondedApplicant;
import kg.attractor.job_search_java25.model.User;
import kg.attractor.job_search_java25.model.Vacancy;
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
        return ResponseEntity.status(201).body(service.createResponse(resumeId, vacancyId));
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

    @GetMapping("/applicant/{applicantId}/vacancies")
    public ResponseEntity<List<Vacancy>> getVacanciesRespondedByApplicant(@PathVariable int applicantId) {
        List<Integer> vacancyIds = service.getVacancyIdsByApplicantId(applicantId);
        return ResponseEntity.ok(service.getVacanciesByIds(vacancyIds));
    }

    @GetMapping("/vacancy/{vacancyId}/applicants")
    public ResponseEntity<List<User>> getApplicantsByVacancy(@PathVariable int vacancyId) {
        return ResponseEntity.ok(service.getApplicantsByVacancyId(vacancyId));
    }
}