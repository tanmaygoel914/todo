package service;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import entity.User;
import utility.InputValidator;

import java.util.Optional;

public class UserService {
    private final UserDAO dao = new UserDAOImpl();
    private User loggedInUser;

    public boolean signup(String name, String email, String password) {
        if (!InputValidator.validateEmail(email) || !InputValidator.validatePassword(password) || !InputValidator.validName(name)) return false;
        if (dao.findByEmail(email).isPresent()) return false;

        User user = new User(name, email, password);
        dao.save(user);
        return true;
    }

    public boolean signin(String email, String password) {
        Optional<User> userOpt = dao.findByEmail(email);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            loggedInUser = userOpt.get();
            return true;
        }
        return false;
    }

    public void logout() {
        loggedInUser = null;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}