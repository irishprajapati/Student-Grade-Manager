package model;

import util.PasswordUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {
    private int id;
    private String fullname;
    private String password;
    private Role role;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(int id, String fullname, String password, Role role, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.id = id;
        this.fullname = validateUsername(fullname);
        this.password = validatePassword(password);
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public User(String fullname, String password, Role role){
        this.fullname = validateUsername(fullname);
        this.password = validatePassword(password);
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }
    public String getfullname() {
        return fullname;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    private String validateUsername(String fullname){
        if (fullname == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        fullname = fullname.trim();
        if (fullname.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        // normalize multiple spaces → single space
        fullname = fullname.replaceAll("\\s+", " ");
        if (fullname.length() < 2 || fullname.length() > 50) {
            throw new IllegalArgumentException("Name must be between 2 and 50 characters");
        }
        // Unicode-safe (important)
        if (!fullname.matches("^[\\p{L} .'-]+$")) {
            throw new IllegalArgumentException("Name contains invalid characters");
        }
        return fullname;
    }
    private String validatePassword(String password){
        if(password == null){
            throw new IllegalArgumentException("Password cannot be null");
        }
        password = password.trim();
        if(password.isEmpty()){
            throw new IllegalArgumentException("Password cannot be empty");
        }
        password = password.replaceAll("\\s+", " ");
        if(password.length() < 8 || password.length() > 50){
            throw new IllegalArgumentException("password must be between 8 to 50 words");
        }
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and include uppercase, lowercase, number, and special character");
        }
        return password;
    }

    public void setUsername(String fullname) {
        this.fullname= validateUsername(fullname);
        this.updatedAt = LocalDateTime.now();
    }
    public void setPassword(String password){
        this.password = PasswordUtil.hashPassword(password);
        this.updatedAt = LocalDateTime.now();
    }

    public void setRole(String input) {
        this.role = Role.fromString(input);
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
