package dao;

import entity.Todo;
import java.util.List;

public interface TodoDAO {
    void addTodo(int userId, Todo todo);
    List<Todo> getTodosByUser(int userId);
    void deleteTodo(int userId, int todoId);
    Todo getTodoById(int userId, int todoId);
    boolean updateTodo(int userId, int todoId, Todo updatedTodo);
}