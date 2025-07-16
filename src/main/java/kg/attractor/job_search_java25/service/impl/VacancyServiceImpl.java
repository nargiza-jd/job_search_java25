package kg.attractor.job_search_java25.service.impl;

import kg.attractor.job_search_java25.dto.VacancyCreateDto;
import kg.attractor.job_search_java25.dto.VacancyUpdateDto;
import kg.attractor.job_search_java25.model.Vacancy;
import kg.attractor.job_search_java25.service.VacancyService;
import kg.attractor.job_search_java25.util.FileUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class VacancyServiceImpl implements VacancyService {
    private final FileUtil fileUtil;
    private List<Vacancy> vacancies;

    public VacancyServiceImpl(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @PostConstruct
    public void init() {
        vacancies = new CopyOnWriteArrayList<>(fileUtil.loadVacancies());
    }

    @Override
    public List<Vacancy> getAllActiveVacancies() {
        return vacancies.stream()
                .filter(Vacancy::isActive)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Vacancy> getVacancyById(int id) {
        return vacancies.stream().filter(vacancy -> vacancy.getId() == id).findFirst();
    }

    @Override
    public Vacancy createVacancy(VacancyCreateDto vacancyDto) {
        Vacancy vacancy = new Vacancy();
        vacancy.setId(fileUtil.getNextVacancyId());
        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setCategoryId(vacancyDto.getCategoryId());
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setExpFrom(vacancyDto.getExpFrom());
        vacancy.setExpTo(vacancyDto.getExpTo());
        vacancy.setAuthorId(vacancyDto.getAuthorId());

        vacancy.setCreatedDate(LocalDateTime.now());
        vacancy.setUpdateTime(LocalDateTime.now());
        vacancy.setActive(true);

        vacancies.add(vacancy);
        fileUtil.saveVacancies(vacancies);
        return vacancy;
    }

    @Override
    public Optional<Vacancy> updateVacancy(int id, VacancyUpdateDto updatedVacancyDto) {
        Optional<Vacancy> existingVacancyOpt = getVacancyById(id);
        if (existingVacancyOpt.isPresent()) {
            Vacancy existingVacancy = existingVacancyOpt.get();
            existingVacancy.setName(updatedVacancyDto.getName());
            existingVacancy.setSalary(updatedVacancyDto.getSalary());
            existingVacancy.setDescription(updatedVacancyDto.getDescription());
            existingVacancy.setExpFrom(updatedVacancyDto.getExpFrom());
            existingVacancy.setExpTo(updatedVacancyDto.getExpTo());
            existingVacancy.setCategoryId(updatedVacancyDto.getCategoryId());
            existingVacancy.setUpdateTime(LocalDateTime.now());
            existingVacancy.setActive(updatedVacancyDto.isActive());

            fileUtil.saveVacancies(vacancies);
            return Optional.of(existingVacancy);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteVacancy(int id) {
        boolean removed = vacancies.removeIf(vacancy -> vacancy.getId() == id);
        if (removed) {
            fileUtil.saveVacancies(vacancies);
        }
        return removed;
    }

    @Override
    public List<Vacancy> getVacanciesByCategoryId(int categoryId) {
        return vacancies.stream()
                .filter(Vacancy::isActive)
                .filter(vacancy -> vacancy.getCategoryId() == categoryId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Vacancy> getVacanciesByAuthorId(int authorId) {
        return vacancies.stream()
                .filter(vacancy -> vacancy.getAuthorId() == authorId)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Vacancy> toggleVacancyActiveStatus(int id, boolean isActive) {
        Optional<Vacancy> existingVacancyOpt = getVacancyById(id);
        if (existingVacancyOpt.isPresent()) {
            Vacancy existingVacancy = existingVacancyOpt.get();
            existingVacancy.setActive(isActive);
            existingVacancy.setUpdateTime(LocalDateTime.now());
            fileUtil.saveVacancies(vacancies);
            return Optional.of(existingVacancy);
        }
        return Optional.empty();
    }
}