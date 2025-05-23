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
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

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
        System.out.println("3. Filter TODOs");
        System.out.println("4. Search TODOs");
        System.out.println("5. Sort TODOs");
        System.out.println("6. Mark TODO Complete");
        System.out.println("7. View Overdue TODOs");
        System.out.println("8. Logout");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                addTodo();
                break;
            case 2:
                viewTodos();
                break;
            case 3:
                filterTodos();
                break;
            case 4:
                searchTodos();
                break;
            case 5:
                sortTodos();
                break;
            case 6:
                markComplete();
                break;
            case 7:
                viewOverdue();
                break;
            case 8:
                userService.logout();
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    private void addTodo() {
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Description: ");
        String desc = sc.nextLine();
        System.out.print("Priority (HIGH/MEDIUM/LOW): ");
        String priority = sc.nextLine();
        System.out.print("Due Date and Time (YYYY-MM-DD HH:mm): ");
        String dueDateStr = sc.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dueDate = LocalDateTime.parse(dueDateStr, formatter);
        if (!InputValidator.validateDateTime(dueDateStr)) {
            System.out.println("Invalid date format.");
            return;
        }
        if (!InputValidator.isValidTitle(title)) {
            System.out.println("Invalid title format.");
            return;

        }
        if (!InputValidator.validateDescription(desc)) {
            System.out.println("Invalid description format.");
            return;
        }

        if (!InputValidator.isDueDateAfterCreation(LocalDateTime.now(), dueDate)) {
            System.out.println("Due date/time cannot be before creation date/time.");
            return;
        }

        TodoDTO dto = new TodoDTO(title, desc, priority, dueDate);
        todoService.createTodo(userService.getLoggedInUser().getUserId(), dto);
        System.out.println("TODO added!");
    }

    private void viewTodos() {
        System.out.println("Enter search keyword (optional):");
        String keyword = sc.nextLine();

        System.out.println("Filter by status (Pending/Completed) or leave blank:");
        String status = sc.nextLine();

        System.out.println("Filter by priority (High/Medium/Low) or leave blank:");
        String priority = sc.nextLine();

        System.out.println("Sort by (creationDate/dueDate/priority):");
        String sortBy = sc.nextLine();
        List<Todo> todos = todoService.getTodos(userService.getLoggedInUser().getUserId(), keyword, status, priority,
                sortBy);
        if (todos.isEmpty())
            System.out.println("No TODOs found.");
        else
            todos.forEach(System.out::println);
    }

    private void filterTodos() {
        System.out.print("Filter by (status/priority): ");
        String filter = sc.nextLine();
        System.out.print("Value: ");
        String value = sc.nextLine();

        List<Todo> result = filter.equalsIgnoreCase("status")
                ? todoService.filterByStatus(userService.getLoggedInUser().getUserId(), value)
                : todoService.filterByPriority(userService.getLoggedInUser().getUserId(), value);

        result.forEach(System.out::println);
    }

    private void searchTodos() {
        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine();
        todoService.search(userService.getLoggedInUser().getUserId(), keyword)
                .forEach(System.out::println);
    }

    private void sortTodos() {
        System.out.print("Sort by (priority/title/date/creationdate): ");
        String by = sc.nextLine();
        todoService.sortTodos(userService.getLoggedInUser().getUserId(), by)
                .forEach(System.out::println);
    }

    private void markComplete() {
        System.out.print("Enter TODO ID to mark as complete: ");
        int id = sc.nextInt();
        todoService.markAsComplete(userService.getLoggedInUser().getUserId(), id);
        System.out.println("Marked complete (if ID matched).");
    }

    private void viewOverdue() {
        todoService.getOverdueTodos(userService.getLoggedInUser().getUserId())
                .forEach(System.out::println);
    }
}
