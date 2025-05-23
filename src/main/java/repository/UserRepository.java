package repository;

import entity.User;
import java.util.*;

public class UserRepository {
    private final Map<Integer, User> users = new HashMap<>();

    public void add(User user) {
        users.put(user.getUserId(), user);
    }

    public Optional<User> findByEmail(String email) {
        return users.values().stream().filter(u -> u.getEmail().equals(email)).findFirst();
    }

    public Optional<User> findById(int id) {
        return Optional.ofNullable(users.get(id));
    }
}
