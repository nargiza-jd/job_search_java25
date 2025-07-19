package kg.attractor.dao;

import kg.attractor.job_search_java25.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> findByName(String name);
    List<User> findByPhoneNumber(String phone);
    Optional<User> findByEmail(String email);
}
