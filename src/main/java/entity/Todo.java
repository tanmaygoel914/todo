package entity;

import java.time.LocalDate;

public class Todo {
    private static int todoCounter = 0;
    private int todoId;
    private int userId;
    private String title;
    private String description;
    private String status; // PENDING, IN_PROGRESS, COMPLETED
    private String priority; // HIGH, MEDIUM, LOW
    private LocalDate dueDate;
    private LocalDate creationDate;

    public Todo( int userId, String title, String description,
                String status, String priority, LocalDate dueDate, LocalDate creationDate) {
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
    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public LocalDate getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
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
                ", dueDate=" + dueDate +
                ", creationDate=" + creationDate +
                '}';
    }
}
