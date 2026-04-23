package model;

import util.EmailUtils;
import util.PhoneUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Student {
    private int id;
    private String fullName;
    private Gender gender;
    private String phoneNumber;
    private String address;
    private String email;
    private String guardianName;
    private LocalDate dateOfBirth;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // for reconstructing a student fetched from DB
    public Student(int id, String fullName, Gender gender, String phoneNumber,
                   String address, String email, String guardianName,
                   LocalDate dateOfBirth, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        setPhoneNumber(phoneNumber);
        this.address = address;
        this.email = email;
        this.guardianName = guardianName;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // for creating new student
    public Student(String fullName, Gender gender, String phoneNumber,
                   String address, String email, String guardianName,
                   LocalDate dateOfBirth) {
        this.fullName = fullName;
        this.gender = gender;
        setPhoneNumber(phoneNumber);
        this.address = address;
        setEmail(email);
        this.guardianName = guardianName;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public int getId() { return id; }
    public String getFullName() { return fullName; }
    public Gender getGender() { return gender; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    public String getGuardianName() { return guardianName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = PhoneUtils.validateAndNormalize(phoneNumber);
        this.updatedAt = LocalDateTime.now();
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.fullName = fullName.trim();
        this.updatedAt = LocalDateTime.now();
    }

    public void setAddress(String address) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address can't be empty");
        }
        this.address = address.trim();
        this.updatedAt = LocalDateTime.now();
    }

    public void setGender(String input) {
        this.gender = Gender.fromString(input);
        this.updatedAt = LocalDateTime.now();
    }
    public void setEmail(String email){
        EmailUtils.validate(email);
        this.email = email.trim();
        this.updatedAt = LocalDateTime.now();
    }
    public void setGuardianName(String guardianName){
        if(guardianName == null || guardianName.isBlank()) throw new IllegalArgumentException("Parents name cannot be empty");
        this.guardianName = guardianName;
        this.updatedAt = LocalDateTime.now();
    }
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", gender=" + gender +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", guardianName='" + guardianName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}