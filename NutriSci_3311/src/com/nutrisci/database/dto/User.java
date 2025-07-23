package com.nutrisci.database.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object representing a user in the NutriSci application.
 * Contains all user profile information including updated fields for sex, dob, height, weight, and unit preference.
 */
public class User {
    
    private int userId;
    private String username;
    private String email;
    private String passwordHash;
    private char sex; // 'M', 'F', 'O'
    private LocalDate dateOfBirth;
    private int heightCm;
    private double weightKg;
    private String unitPreference; // 'metric' or 'imperial'
    private LocalDateTime createdAt;
    
    // Default constructor
    public User() {}
    
    // Constructor for creating new user (without userId and createdAt)
    public User(String username, String email, String passwordHash, char sex, 
                LocalDate dateOfBirth, int heightCm, double weightKg, String unitPreference) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.heightCm = heightCm;
        this.weightKg = weightKg;
        this.unitPreference = unitPreference;
    }
    
    // Full constructor
    public User(int userId, String username, String email, String passwordHash, char sex,
                LocalDate dateOfBirth, int heightCm, double weightKg, String unitPreference, 
                LocalDateTime createdAt) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.heightCm = heightCm;
        this.weightKg = weightKg;
        this.unitPreference = unitPreference;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    
    public char getSex() { return sex; }
    public void setSex(char sex) { this.sex = sex; }
    
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public int getHeightCm() { return heightCm; }
    public void setHeightCm(int heightCm) { this.heightCm = heightCm; }
    
    public double getWeightKg() { return weightKg; }
    public void setWeightKg(double weightKg) { this.weightKg = weightKg; }
    
    public String getUnitPreference() { return unitPreference; }
    public void setUnitPreference(String unitPreference) { this.unitPreference = unitPreference; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", sex=" + sex +
                ", dateOfBirth=" + dateOfBirth +
                ", heightCm=" + heightCm +
                ", weightKg=" + weightKg +
                ", unitPreference='" + unitPreference + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}