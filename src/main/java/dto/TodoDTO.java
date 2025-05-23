package dto;

import java.time.LocalDate;

public class TodoDTO {
    private String title;
    private String description;
    private String priority;
    private LocalDate dueDate;

    public TodoDTO(String title, String description, String priority, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    // Getters
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getPriority() {
        return priority;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    
}
