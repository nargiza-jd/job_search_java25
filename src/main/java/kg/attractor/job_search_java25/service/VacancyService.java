package kg.attractor.job_search_java25.service;

import kg.attractor.job_search_java25.dto.VacancyCreateDto;
import kg.attractor.job_search_java25.dto.VacancyUpdateDto;
import kg.attractor.job_search_java25.model.Vacancy;
import java.util.List;
import java.util.Optional;

public interface VacancyService {
    List<Vacancy> getAllActiveVacancies();
    Optional<Vacancy> getVacancyById(int id);
    Vacancy createVacancy(VacancyCreateDto vacancyDto);
    Optional<Vacancy> updateVacancy(int id, VacancyUpdateDto updatedVacancyDto);
    boolean deleteVacancy(int id);
    List<Vacancy> getVacanciesByCategoryId(int categoryId);
    List<Vacancy> getVacanciesByAuthorId(int authorId);
    Optional<Vacancy> toggleVacancyActiveStatus(int id, boolean isActive);
}