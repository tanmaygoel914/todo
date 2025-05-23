package repository;

import entity.Todo;
import java.util.*;

public class TodoRepository {
    private final Map<Integer, List<Todo>> todoMap = new HashMap<>();

    public void add(int userId, Todo todo) {
        todoMap.computeIfAbsent(userId, k -> new ArrayList<>()).add(todo);
    }

    public List<Todo> getByUserId(int userId) {
        return todoMap.getOrDefault(userId, new ArrayList<>());
    }

    public void delete(int userId, int todoId) {
        todoMap.getOrDefault(userId, new ArrayList<>()).removeIf(todo -> todo.getTodoId()==todoId);
    }
}
