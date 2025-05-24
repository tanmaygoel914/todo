package entity;

import java.time.LocalDateTime;

public class Todo {
    private static int todoCounter = 0;
    private int todoId;
    private int userId;
    private String title;
    private String description;
    private String status; // PENDING, IN_PROGRESS, COMPLETED
    private String priority; // HIGH, MEDIUM, LOW
    private LocalDateTime dueDateTime;
    private LocalDateTime creationDateTime;

    public Todo( int userId, String title, String description,
                String status, String priority, LocalDateTime dueDate, LocalDateTime creationDate) {
        setTodoId(todoCounter++);
        setUserId(userId);
        setTitle(title);
        setDescription(description);
        setStatus(status);
        setPriority(priority);
        setDueDate(dueDate);
        setCreationDate(creationDate);
    }

    // Getters and Setters
    public int getTodoId() {
        return todoId;
    }
    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public LocalDateTime getDueDate() {
        return dueDateTime;
    }
    public void setDueDate(LocalDateTime dueDateTime) {
        this.dueDateTime = dueDateTime;
    }
    public LocalDateTime getCreationDate() {
        return creationDateTime;
    }
    public void setCreationDate(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }
    @Override
    public String toString() {
        return "Todo{" +
                "todoId='" + todoId + '\'' +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", dueDateTime=" + dueDateTime +
                ", creationDateTime=" + creationDateTime +
                '}';
    }
}
