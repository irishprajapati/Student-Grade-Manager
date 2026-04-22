package model;

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
    private LocalDateTime createdAt;

    public Student(int id, Gender gender, String fullName, String phoneNumber, String address, String email, String guardianName, LocalDate dateOfBirth, LocalDateTime createdAt) {
        this.id = id;
        this.gender = gender;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.guardianName = guardianName;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = createdAt;
    }

    public Student(String fullName, Gender gender, String phoneNumber, String address,String guardianName, LocalDate dateOfBirth, String email, LocalDateTime createdAt) {
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.guardianName = guardianName;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public Gender getGender() {
        return gender;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public static boolean isValidPhoneNumber(String phoneNumber){
        if(phoneNumber == null) return false;
        return phoneNumber.matches("^(98|97)\\d{8}$");
    }
    public void setPhoneNumber(String phoneNumber){
        if(!isValidPhoneNumber(phoneNumber))
        {
            throw new IllegalArgumentException("Invalid Nepali phone number: " + phoneNumber);
        }
        this.phoneNumber = phoneNumber;
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
                '}';
    }
}
