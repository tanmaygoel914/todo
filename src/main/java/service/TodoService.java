package service;

import dao.TodoDAO;
import dao.impl.TodoDAOImpl;
import dto.TodoDTO;
import entity.Todo;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TodoService {
    private final TodoDAO dao = new TodoDAOImpl();

    public void createTodo(int userId, TodoDTO dto) {
        Todo todo = new Todo(userId,
                dto.getTitle(), dto.getDescription(),
                "PENDING", dto.getPriority(),
                dto.getDueDate(), LocalDateTime.now());

        dao.addTodo(userId, todo);
    }
   public List<Todo> getTodos(int userId, String searchKeyword, String statusFilter, String priorityFilter, String sortBy) {
        return dao.getTodosByUser(userId).stream()
            .filter(todo -> (searchKeyword == null || searchKeyword.trim().isEmpty() ||
                    todo.getTitle().toLowerCase().contains(searchKeyword.toLowerCase()) ||
                    todo.getDescription().toLowerCase().contains(searchKeyword.toLowerCase())))
            .filter(todo -> (statusFilter == null || statusFilter.trim().isEmpty() || 
                    todo.getStatus().equalsIgnoreCase(statusFilter)))
            .filter(todo -> (priorityFilter == null || priorityFilter.trim().isEmpty() || 
                    todo.getPriority().equalsIgnoreCase(priorityFilter)))
            .sorted((t1, t2) -> {
                if (sortBy == null || sortBy.trim().isEmpty()) {
                    return t1.getCreationDate().compareTo(t2.getCreationDate());
                }
                switch (sortBy.toLowerCase()) {
                    case "priority":
                        return t1.getPriority().compareToIgnoreCase(t2.getPriority());
                    case "duedate":
                        return t1.getDueDate().compareTo(t2.getDueDate());
                    default:
                        return t1.getCreationDate().compareTo(t2.getCreationDate());
                }
            })
            .collect(Collectors.toList());
    }
    public List<Todo> getAllTodos(int userId) {
        return dao.getTodosByUser(userId);
    }

    public Todo getTodoById(int userId, int todoId) {
        return dao.getTodoById(userId, todoId);
    }

    public boolean editTodo(int userId, int todoId, TodoDTO dto) {
        Todo existingTodo = dao.getTodoById(userId, todoId);
        if (existingTodo == null) {
            return false;
        }

        Todo updatedTodo = new Todo(userId,
                dto.getTitle(), dto.getDescription(),
                existingTodo.getStatus(), // Keep the same status
                dto.getPriority(),
                dto.getDueDate(),
                existingTodo.getCreationDate()); // Keep the same creation date
        
        // Set the same todoId
        updatedTodo.setTodoId(existingTodo.getTodoId());

        return dao.updateTodo(userId, todoId, updatedTodo);
    }

    public boolean removeTodo(int userId, int todoId) {
        Todo existingTodo = dao.getTodoById(userId, todoId);
        if (existingTodo != null) {
            dao.deleteTodo(userId, todoId);
            return true;
        }
        return false;
    }

    public void markAsComplete(int userId, int todoId) {
        dao.getTodosByUser(userId).forEach(todo -> {
            if (todo.getTodoId() == todoId) {
                todo.setStatus("COMPLETED");
                return;
            }
        });
        System.out.println("todo did not found");
    }

    public void markAsInProgress(int userId, int todoId) {
        dao.getTodosByUser(userId).forEach(todo -> {
            if (todo.getTodoId() == todoId) {
                todo.setStatus("IN_PROGRESS");
            }
        });
    }

    public List<Todo> search(int userId, String keyword) {
        return dao.getTodosByUser(userId).stream()
                .filter(todo -> todo.getTitle().toLowerCase().contains(keyword.toLowerCase()) || 
                               todo.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Todo> getOverdueTodos(int userId) {
        return dao.getTodosByUser(userId).stream()
                .filter(todo -> todo.getDueDate().isBefore(LocalDateTime.now()) && 
                               !todo.getStatus().equals("COMPLETED"))
                .collect(Collectors.toList());
    }
}