// package service;

// import dao.UserDAO;
// import entity.User;
// import utility.InputValidator;

// import java.util.Optional;

// public class UserService {
//     private final UserDAO dao = new UserDAO();
//     private User loggedInUser;

//     public boolean signup(String name, String email, String password) {
//         if (!InputValidator.validateEmail(email) || !InputValidator.validatePassword(password)|| !InputValidator.validName(name)) return false;
//         if (dao.findByEmail(email).isPresent()) return false;

//         User user = new User( name, email, password);
//         dao.save(user);
//         return true;
//     }

//     public boolean signin(String email, String password) {
//         Optional<User> userOpt = dao.findByEmail(email);
//         if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
//             loggedInUser = userOpt.get();
//             return true;
//         }
//         return false;
//     }

//     public void logout() {
//         loggedInUser = null;
//     }

//     public User getLoggedInUser() {
//         return loggedInUser;
//     }
// }



package service;

import dao.UserDAO;
import entity.User;
import utility.InputValidator;

import java.util.Optional;

public class UserService {
    private final UserDAO dao = new UserDAO();
    private User loggedInUser;
    private String lastErrorMessage; // To store the specific error message

    public boolean signup(String name, String email, String password) {
        // Validate name
        if (!InputValidator.validName(name)) {
            lastErrorMessage = "Invalid name. Name cannot be empty or contain numbers.";
            return false;
        }
        
        // Validate email
        if (!InputValidator.validateEmail(email)) {
            lastErrorMessage = "Invalid email format.";
            return false;
        }
        
        // Validate password
        if (!InputValidator.validatePassword(password)) {
            lastErrorMessage = "Invalid password. Password must be at least 6 characters long, contain at least 1 letter, 1 digit, 1 special character, and no spaces.";
            return false;
        }
        
        // Check if email already exists
        if (dao.findByEmail(email).isPresent()) {
            lastErrorMessage = "Email already exists.";
            return false;
        }

        User user = new User(name, email, password);
        dao.save(user);
        lastErrorMessage = null; // Clear error message on success
        return true;
    }

    public boolean signin(String email, String password) {
        // Validate email format first
        if (!InputValidator.validateEmail(email)) {
            lastErrorMessage = "Invalid email format.";
            return false;
        }
        
        Optional<User> userOpt = dao.findByEmail(email);
        if (!userOpt.isPresent()) {
            lastErrorMessage = "No account found with this email.";
            return false;
        }
        
        if (!userOpt.get().getPassword().equals(password)) {
            lastErrorMessage = "Wrong password.";
            return false;
        }
        
        loggedInUser = userOpt.get();
        lastErrorMessage = null; // Clear error message on success
        return true;
    }

    public void logout() {
        loggedInUser = null;
        lastErrorMessage = null;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
    
    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
}
