package controller;

import dto.TodoDTO;
import service.TodoService;
import service.UserService;
import utility.InputValidator;
import entity.Todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TodoController {
    private final UserService userService = new UserService();
    private final TodoService todoService = new TodoService();
    private final Scanner sc = new Scanner(System.in);

    public void startApp() {
        System.out.println("Welcome to Console TODO App");

        while (true) {
            if (userService.getLoggedInUser() == null) {
                System.out.println("1. Signup\n2. Login\n3. Exit");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        signup();
                        break;
                    case 2:
                        login();
                        break;
                    case 3:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            } else {
                mainMenu();
            }
        }
    }

    private void signup() {
        String name;
        while (true) {
            System.out.print("Name: ");
            name = sc.nextLine();
            if (InputValidator.validName(name)) {
                break;
            } else {
                System.out.println("Invalid name format. Please try again.");
            }
        }

        String email;
        while (true) {
            System.out.print("Email: ");
            email = sc.nextLine();
            if (InputValidator.validateEmail(email)) {
                break;
            } else {
                System.out.println("Invalid email format. Please try again.");
            }
        }

        String password;
        while (true) {
            System.out.print("Password: ");
            password = sc.nextLine();
            if (InputValidator.validatePassword(password)) {
                break;
            } else {
                System.out.println("Invalid password format. Please try again.");
            }
        }

        if (userService.signup(name, email, password)) {
            System.out.println("Signup successful!");
        } else {
            System.out.println("Signup failed. Email may already exist or invalid input.");
        }
    }

    private void login() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        if (userService.signin(email, password)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private void mainMenu() {
        System.out.println("\nTODO MENU");
        System.out.println("1. Add TODO");
        System.out.println("2. View All TODOs");
        System.out.println("3. Edit TODO");
        System.out.println("4. Search TODOs");
        System.out.println("5. Mark TODO Complete");
        System.out.println("6. Mark TODO In Progress");
        System.out.println("7. View Overdue TODOs");
        System.out.println("8. Remove TODO");
        System.out.println("9. Logout");

        int choice;
        while (true) {
            try {
                System.out.print("Choose an option: ");
                choice = sc.nextInt();
                sc.nextLine(); // consume newline
             break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
                sc.nextLine(); // clear the invalid input
            }
           
        }

        switch (choice) {
            case 1:
                addTodo();
                break;
            case 2:
                viewTodos();
                break;
            case 3:
                editTodo();
                break;
            case 4:
                searchTodos();
                break;
            case 5:
                markComplete();
                break;
            case 6:
                markInProgress();
                break;
            case 7:
                viewOverdue();
                break;
            case 8:
                removeTodo();
                break;
            case 9:
                userService.logout();
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    private void addTodo() {
        String title;
        while (true) {
            System.out.print("Title: ");
            title = sc.nextLine();
            if (InputValidator.isValidTitle(title)) {
            break;
            } else {
            System.out.println("Invalid title format. Please try again.");
            }
        }

        String desc;
        while (true) {
            System.out.print("Description: ");
            desc = sc.nextLine();
            if (InputValidator.validateDescription(desc)) {
            break;
            } else {
            System.out.println("Invalid description format. Please try again.");
            }
        }

        String priority;
        while (true) {
            System.out.print("Priority (HIGH/MEDIUM/LOW): ");
            priority = sc.nextLine();
            if (priority.equalsIgnoreCase("HIGH") || priority.equalsIgnoreCase("MEDIUM") || priority.equalsIgnoreCase("LOW")) {
            break;
            } else {
            System.out.println("Invalid priority. Please enter HIGH, MEDIUM, or LOW.");
            }
        }

        LocalDateTime dueDate;
        while (true) {
            System.out.print("Due Date and Time (YYYY-MM-DD HH:mm): ");
            String dueDateStr = sc.nextLine();
            if (!InputValidator.validateDateTime(dueDateStr)) {
            System.out.println("Invalid date format. Please try again.");
            continue;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            dueDate = LocalDateTime.parse(dueDateStr, formatter);
            if (!InputValidator.isDueDateAfterCreation(LocalDateTime.now(), dueDate)) {
            System.out.println("Due date/time cannot be before creation date/time. Please try again.");
            continue;
            }
            break;
        }

        TodoDTO dto = new TodoDTO(title, desc, priority, dueDate);
        todoService.createTodo(userService.getLoggedInUser().getUserId(), dto);
        System.out.println("TODO added!");
    }

   private void viewTodos() {
        String keyword = null;
        String status = null;
        String priority = null;
        String sortBy = null;
        while (true) {
            try {
            System.out.println("Enter search keyword (optional):");
            keyword = sc.nextLine().trim();
            if (keyword.isEmpty()) keyword = null;

            // Status validation loop
            while (true) {
                System.out.println("Filter by status (Pending/Completed/In_Progress) or leave blank:");
                status = sc.nextLine().trim();
                if (status.isEmpty()) {
                status = null;
                break;
                }
                if (status.equalsIgnoreCase("Pending") ||
                status.equalsIgnoreCase("Completed") ||
                status.equalsIgnoreCase("In_Progress")) {
                break;
                } else {
                System.out.println("Invalid status. Please enter Pending, Completed, or In_Progress.");
                }
            }

            // Priority validation loop
            while (true) {
                System.out.println("Filter by priority (High/Medium/Low) or leave blank:");
                priority = sc.nextLine().trim();
                if (priority.isEmpty()) {
                priority = null;
                break;
                }
                if (priority.equalsIgnoreCase("HIGH") ||
                priority.equalsIgnoreCase("MEDIUM") ||
                priority.equalsIgnoreCase("LOW")) {
                break;
                } else {
                System.out.println("Invalid priority. Please enter High, Medium, or Low.");
                }
            }

            // SortBy validation loop
            while (true) {
                System.out.println("Sort by (creationDate/dueDate/priority) or leave blank:");
                sortBy = sc.nextLine().trim();
                if (sortBy.isEmpty()) {
                sortBy = null;
                break;
                }
                if (sortBy.equalsIgnoreCase("creationDate") ||
                sortBy.equalsIgnoreCase("dueDate") ||
                sortBy.equalsIgnoreCase("priority")) {
                break;
                } else {
                System.out.println("Invalid sort option. Please enter creationDate, dueDate, or priority.");
                }
            }

            break;
            } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
            sc.nextLine(); // clear the invalid input
            }
        }

        List<Todo> todos = todoService.getTodos(userService.getLoggedInUser().getUserId(),
                      keyword, status, priority, sortBy);
        if (todos.isEmpty()) {
            System.out.println("No TODOs found.");
        } else {
            System.out.println("Found " + todos.size() + " TODO(s):");
            System.out.printf("%-6s %-20s %-25s %-10s %-12s %-20s %-20s%n",
            "ID", "Title", "Description", "Status", "Priority", "Due Date", "Created At");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
            for (Todo todo : todos) {
            System.out.printf("%-6s %-20s %-25s %-10s %-12s %-20s %-20s%n",
                todo.getTodoId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getStatus(),
                todo.getPriority(),
                todo.getDueDate(),
                todo.getCreationDate()
            );
            }
        }
    }
    private void editTodo() {
        System.out.print("Enter TODO ID to edit: ");
        int id;
        while (true) {
            try {
                id = sc.nextInt();
                sc.nextLine(); // consume newline
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid TODO ID (number):");
                sc.nextLine(); // clear invalid input
            }
        }
        
        Todo existingTodo = todoService.getTodoById(userService.getLoggedInUser().getUserId(), id);
        if (existingTodo == null) {
            System.out.println("TODO not found.");
            return;
        }
        
        System.out.println("Current TODO: " + existingTodo);
        System.out.println("Enter new values (press Enter to keep current value):");
        
        System.out.print("Title [" + existingTodo.getTitle() + "]: ");
        String title = sc.nextLine();
        if (title.trim().isEmpty()) {
            title = existingTodo.getTitle();
        }
        
        System.out.print("Description [" + existingTodo.getDescription() + "]: ");
        String desc = sc.nextLine();
        if (desc.trim().isEmpty()) {
            desc = existingTodo.getDescription();
        }
        
        System.out.print("Priority [" + existingTodo.getPriority() + "] (HIGH/MEDIUM/LOW): ");
        String priority = sc.nextLine();
        if (priority.trim().isEmpty()) {
            priority = existingTodo.getPriority();
        }
        
        System.out.print("Due Date and Time [" + existingTodo.getDueDate() + "] (YYYY-MM-DD HH:mm): ");
        String dueDateStr = sc.nextLine();
        LocalDateTime dueDate;
        if (dueDateStr.trim().isEmpty()) {
            dueDate = existingTodo.getDueDate();
        } else {
            if (!InputValidator.validateDateTime(dueDateStr)) {
                System.out.println("Invalid date format.");
                return;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            dueDate = LocalDateTime.parse(dueDateStr, formatter);
        }
        
        // Validate inputs
        if (!InputValidator.isValidTitle(title)) {
            System.out.println("Invalid title format.");
            return;
        }
        if (!InputValidator.validateDescription(desc)) {
            System.out.println("Invalid description format.");
            return;
        }
        
        TodoDTO dto = new TodoDTO(title, desc, priority, dueDate);
        if (todoService.editTodo(userService.getLoggedInUser().getUserId(), id, dto)) {
            System.out.println("TODO updated successfully!");
        } else {
            System.out.println("Failed to update TODO.");
        }
    }

    private void searchTodos() {
        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine();
        todoService.search(userService.getLoggedInUser().getUserId(), keyword)
                .forEach(System.out::println);
    }

    private void markComplete() {
        System.out.print("Enter TODO ID to mark as complete: ");
        int id = sc.nextInt();
        todoService.markAsComplete(userService.getLoggedInUser().getUserId(), id);
        System.out.println("Marked complete (if ID matched).");
    }

    private void markInProgress() {
        System.out.print("Enter TODO ID to mark as in progress: ");
        int id = sc.nextInt();
        todoService.markAsInProgress(userService.getLoggedInUser().getUserId(), id);
        System.out.println("Marked as in progress (if ID matched).");
    }

    private void viewOverdue() {
        todoService.getOverdueTodos(userService.getLoggedInUser().getUserId())
                .forEach(System.out::println);
    }

    private void removeTodo() {
        System.out.print("Enter TODO ID to remove: ");
        int id = sc.nextInt();
        if (todoService.removeTodo(userService.getLoggedInUser().getUserId(), id)) {
            System.out.println("TODO removed successfully!");
        } else {
            System.out.println("TODO not found or failed to remove.");
        }
    }
}