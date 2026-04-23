package model;

import util.PhoneUtils;
import java.time.LocalDateTime;
public class Teacher {
    private int id;
    private String fullName;
    private String location;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public Teacher(int id, String fullName, String location, String phoneNumber, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.id = id;
        this.fullName = fullName;
        this.location = location;
        setPhoneNumber(phoneNumber);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public Teacher(String fullName, String location, String phoneNumber){
        this.fullName = fullName;
        this.location = location;
        setPhoneNumber(phoneNumber);
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getLocation() {
        return location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setFullName(String fullName){
        if(fullName == null) throw new IllegalArgumentException("Name cannot be null");
        fullName = fullName.trim();
        if(fullName.isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        if(fullName.length() < 5 || fullName.length() > 50) throw new IllegalArgumentException("Name cannot be shorter than 5 and longer than 50");
        fullName = fullName.replaceAll("\\s+", " ");
        if (!fullName.matches("^[\\p{L} .'-]+$")) {
            throw new IllegalArgumentException("Name contains invalid characters");
        }
        this.fullName = fullName;
        this.updatedAt = LocalDateTime.now();
    }

    public void setLocation(String location) {
        if(location == null) throw new IllegalArgumentException("Location cannot be null");
        location = location.trim();
        if(location.isEmpty()) throw new IllegalArgumentException("Location cannot be empty");
        if(location.length()<4 || location.length() > 100) throw new IllegalArgumentException("Input valid location name");
        location = location.replaceAll("\\s+", " ");
        if (!location.matches("^[\\p{L} .'-]+$")) {
            throw new IllegalArgumentException("Location contains invalid characters");
        }
        this.location = location;
        this.updatedAt = LocalDateTime.now();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = PhoneUtils.validateAndNormalize(phoneNumber);
        this.updatedAt = LocalDateTime.now();
    }
}
