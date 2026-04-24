package model;

import util.EmailUtils;
import util.PhoneUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Student {
    private int id;
    private int userId;
    private String fullName;
    private Gender gender;
    private String phoneNumber;
    private String email;
    private String location;
    private String guardianName;
    private LocalDate dateOfBirth;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // for reconstructing from DB
    public Student(int id, int userId, String fullName, Gender gender, String phoneNumber,
                   String email, String location, String guardianName,
                   LocalDate dateOfBirth, LocalDateTime createdAt, LocalDateTime updatedAt) {

        this.id = id;
        this.userId = userId;
        this.fullName = validateFullName(fullName);
        this.gender = gender;
        this.phoneNumber = validatePhone(phoneNumber);
        this.email = validateEmail(email);
        this.location = validateLocation(location);
        this.guardianName = validateGuardianName(guardianName);
        this.dateOfBirth = validateDateOfBirth(dateOfBirth);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // for creating new student
    public Student(int userId, String fullName, Gender gender, String phoneNumber,
                   String email,String location, String guardianName,
                   LocalDate dateOfBirth) {
        this.fullName = validateFullName(fullName);
        this.gender = gender;
        this.phoneNumber = validatePhone(phoneNumber);
        this.email = validateEmail(email);
        this.location = validateLocation(location);
        this.guardianName = validateGuardianName(guardianName);
        this.dateOfBirth = validateDateOfBirth(dateOfBirth);
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    // getters
    public int getId() { return id; }
    public int getuserId() {
        return userId;
    }
    public String getFullName() { return fullName; }
    public Gender getGender() { return gender; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public String getLocation() { return location; }
    public String getGuardianName() { return guardianName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // setters (ONLY for updates)
    public void setFullName(String fullName) {
        this.fullName = validateFullName(fullName);
        touch();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = validatePhone(phoneNumber);
        touch();
    }

    public void setEmail(String email) {
        this.email = validateEmail(email);
        touch();
    }

    public void setLocation(String location) {
        this.location = validateLocation(location);
        touch();
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = validateGuardianName(guardianName);
        touch();
    }

    public void setGender(String input) {
        this.gender = Gender.fromString(input);
        touch();
    }

    private void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    // validation methods (single source of truth)

    private String validateFullName(String fullName) {
        if (fullName == null) throw new IllegalArgumentException("Name cannot be null");

        fullName = fullName.trim().replaceAll("\\s+", " ");

        if (fullName.isEmpty())
            throw new IllegalArgumentException("Name cannot be empty");

        if (fullName.length() < 2 || fullName.length() > 50)
            throw new IllegalArgumentException("Name must be between 2 and 50 characters");

        if (!fullName.matches("^[\\p{L} .'-]+$"))
            throw new IllegalArgumentException("Name contains invalid characters");

        return fullName;
    }

    private String validateLocation(String location) {
        if (location == null)
            throw new IllegalArgumentException("Location cannot be null");

        location = location.trim().replaceAll("\\s+", " ");

        if (location.isEmpty())
            throw new IllegalArgumentException("Location cannot be empty");

        if (location.length() < 2 || location.length() > 100)
            throw new IllegalArgumentException("Invalid location length");

        return location;
    }

    private String validateGuardianName(String guardianName) {
        if (guardianName == null)
            throw new IllegalArgumentException("Guardian name cannot be null");

        guardianName = guardianName.trim();

        if (guardianName.isEmpty())
            throw new IllegalArgumentException("Guardian name cannot be empty");

        return guardianName;
    }

    private String validateEmail(String email) {
        EmailUtils.validate(email);
        return email.trim();
    }

    private String validatePhone(String phoneNumber) {
        return PhoneUtils.validateAndNormalize(phoneNumber);
    }
    private LocalDate validateDateOfBirth(LocalDate dateOfBirth){
        if(dateOfBirth == null) throw new IllegalArgumentException("Date of Birth cannot be null");
        LocalDate today = LocalDate.now();
        if(dateOfBirth.isAfter(today)) throw new IllegalArgumentException("Date of birth cannot be in future");
        int age = java.time.Period.between(dateOfBirth, today).getYears();
        if (dateOfBirth.isBefore(LocalDate.of(1900, 1, 1)))
            throw new IllegalArgumentException("Date of birth too old");
        if(age < 3) throw new IllegalArgumentException("Student must be 3 years old");
        if(age> 100) throw new IllegalArgumentException("Student age cannot be more than 100 years old");
        return dateOfBirth;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", gender=" + gender +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                ", guardianName='" + guardianName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}