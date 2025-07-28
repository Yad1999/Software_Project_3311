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
    
    /**
     * Gets the unique user identifier.
     * 
     * @return the user ID, or 0 if not yet assigned
     */
    public int getUserId() { return userId; }
    
    /**
     * Sets the unique user identifier.
     * This is typically set automatically by the database when creating a new user.
     * 
     * @param userId the user ID to set
     */
    public void setUserId(int userId) { this.userId = userId; }
    
    /**
     * Gets the user's unique username.
     * 
     * @return the username used for login
     */
    public String getUsername() { return username; }
    
    /**
     * Sets the user's username.
     * Username must be unique across all users and is used for authentication.
     * 
     * @param username the username to set
     */
    public void setUsername(String username) { this.username = username; }
    
    /**
     * Gets the user's email address.
     * 
     * @return the email address
     */
    public String getEmail() { return email; }
    
    /**
     * Sets the user's email address.
     * Email must be unique across all users and is used for account recovery.
     * 
     * @param email the email address to set
     */
    public void setEmail(String email) { this.email = email; }
    
    /**
     * Gets the user's hashed password.
     * 
     * @return the password hash (never the plain password)
     */
    public String getPasswordHash() { return passwordHash; }
    
    /**
     * Sets the user's hashed password.
     * Should only be set with properly hashed passwords, never plain text.
     * 
     * @param passwordHash the hashed password to set
     */
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    
    /**
     * Gets the user's biological sex.
     * 
     * @return 'M' for Male, 'F' for Female, or 'O' for Other
     */
    public char getSex() { return sex; }
    
    /**
     * Sets the user's biological sex.
     * Used for calculating nutritional requirements and BMR.
     * 
     * @param sex 'M' for Male, 'F' for Female, or 'O' for Other
     */
    public void setSex(char sex) { this.sex = sex; }
    
    /**
     * Gets the user's date of birth.
     * 
     * @return the date of birth
     */
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    
    /**
     * Sets the user's date of birth.
     * Used for age calculation and nutritional requirement adjustments.
     * 
     * @param dateOfBirth the date of birth to set
     */
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    /**
     * Gets the user's height in centimeters.
     * 
     * @return height in centimeters
     */
    public int getHeightCm() { return heightCm; }
    
    /**
     * Sets the user's height in centimeters.
     * Used for BMI calculation and nutritional requirement estimation.
     * 
     * @param heightCm height in centimeters to set
     */
    public void setHeightCm(int heightCm) { this.heightCm = heightCm; }
    
    /**
     * Gets the user's weight in kilograms.
     * 
     * @return weight in kilograms
     */
    public double getWeightKg() { return weightKg; }
    
    /**
     * Sets the user's weight in kilograms.
     * Used for BMI calculation and nutritional requirement estimation.
     * 
     * @param weightKg weight in kilograms to set
     */
    public void setWeightKg(double weightKg) { this.weightKg = weightKg; }
    
    /**
     * Gets the user's preferred unit system.
     * 
     * @return "metric" or "imperial"
     */
    public String getUnitPreference() { return unitPreference; }
    
    /**
     * Sets the user's preferred unit system.
     * Affects how measurements are displayed in the UI.
     * 
     * @param unitPreference "metric" or "imperial"
     */
    public void setUnitPreference(String unitPreference) { this.unitPreference = unitPreference; }
    
    /**
     * Gets the timestamp when the user account was created.
     * 
     * @return the account creation timestamp
     */
    public LocalDateTime getCreatedAt() { return createdAt; }
    
    /**
     * Sets the timestamp when the user account was created.
     * This is typically set automatically by the database.
     * 
     * @param createdAt the account creation timestamp to set
     */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    /**
     * Returns a string representation of the User object.
     * Note: Password hash is excluded for security reasons.
     * 
     * @return string representation of the user
     */
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