package kg.attractor.job_search_java25.controller;

import kg.attractor.job_search_java25.dto.VacancyCreateDto;
import kg.attractor.job_search_java25.dto.VacancyUpdateDto;
import kg.attractor.job_search_java25.model.Vacancy;
import kg.attractor.job_search_java25.service.RespondedApplicantService;
import kg.attractor.job_search_java25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacancies")
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;
    private final RespondedApplicantService respondedApplicantService;

    @GetMapping
    public ResponseEntity<List<Vacancy>> getAllActive() {
        return ResponseEntity.ok(vacancyService.getAllActiveVacancies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getById(@PathVariable int id) {
        return vacancyService.getVacancyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Vacancy> create(@RequestBody VacancyCreateDto dto) {
        Vacancy created = vacancyService.createVacancy(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacancy> update(@PathVariable int id, @RequestBody VacancyUpdateDto dto) {
        return vacancyService.updateVacancy(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return vacancyService.deleteVacancy(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Vacancy>> getByCategory(@PathVariable int categoryId) {
        return ResponseEntity.ok(vacancyService.getVacanciesByCategoryId(categoryId));
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Vacancy>> getByAuthor(@PathVariable int authorId) {
        return ResponseEntity.ok(vacancyService.getVacanciesByAuthorId(authorId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Vacancy> toggleStatus(@PathVariable int id, @RequestParam boolean isActive) {
        return vacancyService.toggleVacancyActiveStatus(id, isActive)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}