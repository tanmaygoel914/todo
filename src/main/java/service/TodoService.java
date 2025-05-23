package service;

import dao.TodoDAO;
import dto.TodoDTO;
import entity.Todo;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TodoService {
    private final TodoDAO dao = new TodoDAO();

    public void createTodo(int userId, TodoDTO dto) {
        Todo todo = new Todo( userId,
                dto.getTitle(), dto.getDescription(),
                "PENDING", dto.getPriority(),
                dto.getDueDate(), LocalDate.now());

        dao.addTodo(userId, todo);
    }

    public List<Todo> getTodos(int userId) {
        return dao.getTodosByUser(userId);
    }

    public void markAsComplete(int userId, int todoId) {
        dao.getTodosByUser(userId).forEach(todo -> {
            if (todo.getTodoId()==todoId) {
                todo.setStatus("COMPLETED");
            }
        });
    }

    public List<Todo> filterByStatus(int userId, String status) {
        return dao.getTodosByUser(userId).stream()
                .filter(todo -> todo.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    public List<Todo> filterByPriority(int userId, String priority) {
        return dao.getTodosByUser(userId).stream()
                .filter(todo -> todo.getPriority().equalsIgnoreCase(priority))
                .collect(Collectors.toList());
    }

    public List<Todo> search(int userId, String keyword) {
        return dao.getTodosByUser(userId).stream()
                .filter(todo -> todo.getTitle().contains(keyword) || todo.getDescription().contains(keyword))
                .collect(Collectors.toList());
    }

    public List<Todo> sortTodos(int userId, String by) {
        Comparator<Todo> comparator;
        switch (by.toLowerCase()) {
            case "priority":
                comparator = Comparator.comparing(Todo::getPriority);
                break;
            case "title":
                comparator = Comparator.comparing(Todo::getTitle);
                break;
            case "date":
                comparator = Comparator.comparing(Todo::getDueDate);
                break;
            default:
                comparator = Comparator.comparing(Todo::getCreationDate);
                break;
        }

        return dao.getTodosByUser(userId).stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<Todo> getOverdueTodos(int userId) {
        return dao.getTodosByUser(userId).stream()
                .filter(todo -> todo.getDueDate().isBefore(LocalDate.now()) && !todo.getStatus().equals("COMPLETED"))
                .collect(Collectors.toList());
    }
}
