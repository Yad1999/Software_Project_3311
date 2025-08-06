package com.nutrisci.database.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object representing a user in the NutriSci application.
 * Uses Builder pattern to handle complex object construction with many parameters.
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
    
    // Private constructor for Builder pattern
    private User(Builder builder) {
        this.userId = builder.userId;
        this.username = builder.username;
        this.email = builder.email;
        this.passwordHash = builder.passwordHash;
        this.sex = builder.sex;
        this.dateOfBirth = builder.dateOfBirth;
        this.heightCm = builder.heightCm;
        this.weightKg = builder.weightKg;
        this.unitPreference = builder.unitPreference;
        this.createdAt = builder.createdAt;
    }
    
    // Default constructor for frameworks/serialization
    public User() {}
    

    
    /**
     * Creates a new Builder instance for constructing User objects.
     * 
     * @return new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }
    
    /**
     * Builder class for constructing User objects with fluent interface.
     * Follows the Builder pattern to handle complex object construction.
     */
    public static class Builder {
        private int userId;
        private String username;
        private String email;
        private String passwordHash;
        private char sex;
        private LocalDate dateOfBirth;
        private int heightCm;
        private double weightKg;
        private String unitPreference = "metric"; // Default value
        private LocalDateTime createdAt;
        
        private Builder() {}
        
        /**
         * Sets the user ID (typically for existing users from database).
         * 
         * @param userId the user ID
         * @return this builder for method chaining
         */
        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }
        
        /**
         * Sets the username (required field).
         * 
         * @param username the unique username
         * @return this builder for method chaining
         */
        public Builder username(String username) {
            this.username = username;
            return this;
        }
        
        /**
         * Sets the email address (required field).
         * 
         * @param email the user's email address
         * @return this builder for method chaining
         */
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        
        /**
         * Sets the password hash (required field).
         * 
         * @param passwordHash the hashed password
         * @return this builder for method chaining
         */
        public Builder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }
        
        /**
         * Sets the user's sex (required field).
         * 
         * @param sex the user's sex ('M', 'F', or 'O')
         * @return this builder for method chaining
         */
        public Builder sex(char sex) {
            this.sex = sex;
            return this;
        }
        
        /**
         * Sets the date of birth (required field).
         * 
         * @param dateOfBirth the user's date of birth
         * @return this builder for method chaining
         */
        public Builder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }
        
        /**
         * Sets the height in centimeters (required field).
         * 
         * @param heightCm the user's height in centimeters
         * @return this builder for method chaining
         */
        public Builder heightCm(int heightCm) {
            this.heightCm = heightCm;
            return this;
        }
        
        /**
         * Sets the weight in kilograms (required field).
         * 
         * @param weightKg the user's weight in kilograms
         * @return this builder for method chaining
         */
        public Builder weightKg(double weightKg) {
            this.weightKg = weightKg;
            return this;
        }
        
        /**
         * Sets the unit preference (defaults to "metric").
         * 
         * @param unitPreference the preferred unit system ("metric" or "imperial")
         * @return this builder for method chaining
         */
        public Builder unitPreference(String unitPreference) {
            this.unitPreference = unitPreference;
            return this;
        }
        
        /**
         * Sets the account creation timestamp (typically set by database).
         * 
         * @param createdAt when the user account was created
         * @return this builder for method chaining
         */
        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        
        /**
         * Builds and validates the User object.
         * 
         * @return new User instance
         * @throws IllegalStateException if required fields are missing
         */
        public User build() {
            validateRequiredFields();
            return new User(this);
        }
        
        /**
         * Validates that all required fields are set.
         * 
         * @throws IllegalStateException if validation fails
         */
        private void validateRequiredFields() {
            validateStringFields();
            validateSexField();
            validateDateOfBirth();
            validatePhysicalMeasurements();
        }
        
        /**
         * Validates required string fields (username, email, passwordHash, unitPreference).
         * 
         * @throws IllegalStateException if any string field validation fails
         */
        private void validateStringFields() {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalStateException("Username is required");
            }
            if (email == null || email.trim().isEmpty()) {
                throw new IllegalStateException("Email is required");
            }
            if (passwordHash == null || passwordHash.trim().isEmpty()) {
                throw new IllegalStateException("Password hash is required");
            }
            if (unitPreference == null || unitPreference.trim().isEmpty()) {
                throw new IllegalStateException("Unit preference is required");
            }
        }
        
        /**
         * Validates the sex field.
         * 
         * @throws IllegalStateException if sex field validation fails
         */
        private void validateSexField() {
            if (sex != 'M' && sex != 'F' && sex != 'O') {
                throw new IllegalStateException("Valid sex (M, F, or O) is required");
            }
        }
        
        /**
         * Validates the date of birth field.
         * 
         * @throws IllegalStateException if date of birth validation fails
         */
        private void validateDateOfBirth() {
            if (dateOfBirth == null) {
                throw new IllegalStateException("Date of birth is required");
            }
        }
        
        /**
         * Validates physical measurement fields (height and weight).
         * 
         * @throws IllegalStateException if physical measurement validation fails
         */
        private void validatePhysicalMeasurements() {
            if (heightCm <= 0) {
                throw new IllegalStateException("Valid height (greater than 0) is required");
            }
            if (weightKg <= 0) {
                throw new IllegalStateException("Valid weight (greater than 0) is required");
            }
        }
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