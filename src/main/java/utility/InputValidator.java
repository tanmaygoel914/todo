package utility;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class InputValidator {

    public static boolean validateEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    public static boolean validatePassword(String password) {
        if (password == null) return false;
        if (password.length() < 6) return false;
        if (!password.matches(".*[a-zA-Z].*")) return false; // At least one letter
        if (!password.matches(".*[0-9].*")) return false; // At least one digit
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) return false; // At least one special character
        if (password.matches(".*\\s.*")) return false; // No spaces
        return true;
    }
    public static boolean validName(String name) {
        return name != null && !name.trim().isEmpty() && !name.matches(".*\\d.*");
    }
 public static boolean validateDateTime(String dateTimeStr) {
    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime.parse(dateTimeStr, formatter);
        return true;
    } catch (Exception e) {
        return false;
    }
}

    public static boolean validateDescription(String description) {
        if (description == null) return false;
        String trimmed = description.trim();
        if (trimmed.isEmpty()) return false;
        // Check word count
        String[] words = trimmed.split("\\s+");
        if (words.length > 200) return false;
        return true;
    }
    public static boolean isDueDateAfterCreation(LocalDateTime created, LocalDateTime due) {
        return due.isAfter(created);
    }
      public static boolean isValidTitle(String title) {
        return title != null && title.length()>=3;
    }
}
