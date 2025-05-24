package dao.impl;

import dao.UserDAO;
import entity.User;
import repository.UserRepository;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    private final UserRepository userRepo = new UserRepository();

    @Override
    public void save(User user) {
        userRepo.add(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepo.findById(id);
    }
}