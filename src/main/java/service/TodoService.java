package service;

import dao.TodoDAO;
import dto.TodoDTO;
import entity.Todo;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TodoService {
    private final TodoDAO dao = new TodoDAO();

    public void createTodo(int userId, TodoDTO dto) {
        Todo todo = new Todo( userId,
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
                .filter(todo -> todo.getDueDate().isBefore(LocalDateTime.now()) && !todo.getStatus().equals("COMPLETED"))
                .collect(Collectors.toList());
    }
    
    
}
