package kg.attractor.job_search_java25.service.impl;

import kg.attractor.job_search_java25.dao.CategoryDao;
import kg.attractor.job_search_java25.dao.UserDao;
import kg.attractor.job_search_java25.dao.VacancyDao;
import kg.attractor.job_search_java25.dto.VacancyCreateDto;
import kg.attractor.job_search_java25.dto.VacancyUpdateDto;
import kg.attractor.job_search_java25.exceptions.NotFoundException;
import kg.attractor.job_search_java25.model.Vacancy;
import kg.attractor.job_search_java25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private final VacancyDao vacancyDao;
    private final CategoryDao categoryDao;
    private final UserDao userDao;

    @Override
    public List<Vacancy> getAllActiveVacancies() {
        return vacancyDao.getAllActive();
    }

    @Override
    public Optional<Vacancy> getVacancyById(int id) {
        try {
            return Optional.ofNullable(vacancyDao.getById(id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Vacancy createVacancy(VacancyCreateDto vacancyDto) {
        if (!categoryDao.existsById(vacancyDto.getCategoryId())) {
            throw new NotFoundException("Категория с ID " + vacancyDto.getCategoryId() + " не найдена.");
        }

        if (userDao.findById(vacancyDto.getAuthorId()).isEmpty()) {
            throw new NotFoundException("Автор с ID " + vacancyDto.getAuthorId() + " не найден.");
        }

        Vacancy vacancy = new Vacancy();
        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setCategoryId(vacancyDto.getCategoryId());
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setExpFrom(vacancyDto.getExpFrom());
        vacancy.setExpTo(vacancyDto.getExpTo());
        vacancy.setAuthorId(vacancyDto.getAuthorId());
        vacancy.setActive(true);
        vacancy.setCreatedDate(LocalDateTime.now());
        vacancy.setUpdateTime(LocalDateTime.now());

        vacancyDao.save(vacancy);
        return vacancy;
    }

    @Override
    public Optional<Vacancy> updateVacancy(int id, VacancyUpdateDto updatedVacancyDto) {
        Optional<Vacancy> optional = getVacancyById(id);
        if (optional.isEmpty()) return Optional.empty();

        if (!categoryDao.existsById(updatedVacancyDto.getCategoryId())) {
            throw new NotFoundException("Категория с ID " + updatedVacancyDto.getCategoryId() + " не найдена.");
        }

        Vacancy vacancy = optional.get();
        vacancy.setName(updatedVacancyDto.getName());
        vacancy.setDescription(updatedVacancyDto.getDescription());
        vacancy.setSalary(updatedVacancyDto.getSalary());
        vacancy.setExpFrom(updatedVacancyDto.getExpFrom());
        vacancy.setExpTo(updatedVacancyDto.getExpTo());
        vacancy.setCategoryId(updatedVacancyDto.getCategoryId());
        vacancy.setActive(updatedVacancyDto.isActive());
        vacancy.setUpdateTime(LocalDateTime.now());

        vacancyDao.update(vacancy);
        return Optional.of(vacancy);
    }

    @Override
    public boolean deleteVacancy(int id) {
        vacancyDao.delete(id);
        return true;
    }

    @Override
    public List<Vacancy> getVacanciesByCategoryId(int categoryId) {
        return vacancyDao.getByCategoryId(categoryId);
    }

    @Override
    public List<Vacancy> getVacanciesByAuthorId(int authorId) {
        return vacancyDao.getByAuthorId(authorId);
    }

    @Override
    public Optional<Vacancy> toggleVacancyActiveStatus(int id, boolean isActive) {
        Optional<Vacancy> optional = getVacancyById(id);
        if (optional.isEmpty()) return Optional.empty();

        vacancyDao.toggleStatus(id, isActive);
        Vacancy vacancy = optional.get();
        vacancy.setActive(isActive);
        vacancy.setUpdateTime(LocalDateTime.now());
        return Optional.of(vacancy);
    }

    @Override
    public List<Vacancy> getVacanciesByIds(List<Integer> ids) {
        return vacancyDao.getByIds(ids);
    }
}