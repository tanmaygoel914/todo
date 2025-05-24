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
        todoMap.getOrDefault(userId, new ArrayList<>()).removeIf(todo -> todo.getTodoId() == todoId);
    }

    public Todo getById(int userId, int todoId) {
        return todoMap.getOrDefault(userId, new ArrayList<>())
                .stream()
                .filter(todo -> todo.getTodoId() == todoId)
                .findFirst()
                .orElse(null);
    }

    public boolean update(int userId, int todoId, Todo updatedTodo) {
        List<Todo> userTodos = todoMap.getOrDefault(userId, new ArrayList<>());
        for (int i = 0; i < userTodos.size(); i++) {
            if (userTodos.get(i).getTodoId() == todoId) {
                userTodos.set(i, updatedTodo);
                return true;
            }
        }
        return false;
    }
}