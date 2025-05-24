package dao;

import entity.User;
import java.util.Optional;

public interface UserDAO {
    void save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(int id);
}