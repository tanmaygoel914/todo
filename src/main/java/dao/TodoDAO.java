package dao;

import entity.Todo;
import repository.TodoRepository;

import java.util.List;

public class TodoDAO {
    private final TodoRepository todoRepo = new TodoRepository();

    public void addTodo(int userId, Todo todo) {
        todoRepo.add(userId, todo);
    }

    public List<Todo> getTodosByUser(int userId) {
        return todoRepo.getByUserId(userId);
    }

    public void deleteTodo(int userId, int todoId) {
        todoRepo.delete(userId, todoId);
    }
}
