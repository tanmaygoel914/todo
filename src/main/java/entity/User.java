package entity;

public class User {
    private static int serialVersionUID = 1;
    private int userId;
    private String name;
    private String email;
    private String password;

    public User( String name, String email, String password) {
        this.userId = serialVersionUID++;
        setName(name);
        setEmail(email);
        setPassword(password);
        
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
