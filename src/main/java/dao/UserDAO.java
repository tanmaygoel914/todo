package dao;

import entity.User;
import repository.UserRepository;
import java.util.Optional;

public class UserDAO {
    private final UserRepository userRepo = new UserRepository();

    public void save(User user) {
        userRepo.add(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public Optional<User> findById(int id) {
        return userRepo.findById(id);
    }
}
