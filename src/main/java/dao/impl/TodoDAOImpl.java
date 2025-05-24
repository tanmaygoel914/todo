package dao.impl;

import dao.TodoDAO;
import entity.Todo;
import repository.TodoRepository;
import java.util.List;

public class TodoDAOImpl implements TodoDAO {
    private final TodoRepository todoRepo = new TodoRepository();

    @Override
    public void addTodo(int userId, Todo todo) {
        todoRepo.add(userId, todo);
    }

    @Override
    public List<Todo> getTodosByUser(int userId) {
        return todoRepo.getByUserId(userId);
    }

    @Override
    public void deleteTodo(int userId, int todoId) {
        todoRepo.delete(userId, todoId);
    }

    @Override
    public Todo getTodoById(int userId, int todoId) {
        return todoRepo.getById(userId, todoId);
    }

    @Override
    public boolean updateTodo(int userId, int todoId, Todo updatedTodo) {
        return todoRepo.update(userId, todoId, updatedTodo);
    }
}