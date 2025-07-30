package kg.attractor.job_search_java25.service;

import kg.attractor.job_search_java25.dto.CategoryCreateDto;
import kg.attractor.job_search_java25.model.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getAllCategories();
    Optional<Category> getCategoryById(int id);
    Category createCategory(CategoryCreateDto dto);
}