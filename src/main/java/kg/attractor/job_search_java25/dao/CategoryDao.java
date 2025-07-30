package kg.attractor.job_search_java25.dao;

import kg.attractor.job_search_java25.model.Category;
import kg.attractor.job_search_java25.dto.CategoryCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryDao {
    private final JdbcTemplate jdbcTemplate;

    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM categories WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    public Optional<Category> getById(int id) {
        String sql = "SELECT id, name, parent_id FROM categories WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, getCategoryRowMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Category> getAllCategories() {
        String sql = "SELECT id, name, parent_id FROM categories";
        return jdbcTemplate.query(sql, getCategoryRowMapper());
    }

    public Category save(CategoryCreateDto dto) {
        String sql = "INSERT INTO categories (name, parent_id) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.getName());
            if (dto.getParentId() != null) {
                ps.setInt(2, dto.getParentId());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }
            return ps;
        }, keyHolder);

        Category category = new Category();
        category.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        category.setName(dto.getName());
        category.setParentId(dto.getParentId());
        return category;
    }

    private RowMapper<Category> getCategoryRowMapper() {
        return (rs, rowNum) -> {
            Category category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            if (rs.getObject("parent_id") != null) {
                category.setParentId(rs.getInt("parent_id"));
            } else {
                category.setParentId(null);
            }
            return category;
        };
    }
}