package kg.attractor.job_search_java25.service.impl;

import kg.attractor.job_search_java25.dao.CategoryDao;
import kg.attractor.job_search_java25.dto.CategoryCreateDto;
import kg.attractor.job_search_java25.exceptions.NotFoundException;
import kg.attractor.job_search_java25.model.Category;
import kg.attractor.job_search_java25.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    @Override
    public Optional<Category> getCategoryById(int id) {
        return categoryDao.getById(id);
    }

    @Override
    public Category createCategory(CategoryCreateDto dto) {
        if (dto.getParentId() != null && !categoryDao.existsById(dto.getParentId())) {
            throw new NotFoundException("Родительская категория с ID " + dto.getParentId() + " не найдена.");
        }
        return categoryDao.save(dto);
    }
}