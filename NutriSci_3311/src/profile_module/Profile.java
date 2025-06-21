package profile_module;

import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;

/**
 * Represents the profile of a user with attributes such as username, password, sex, 
 * date of birth, height, and weight.
 *
 * <p>Each profile must have a unique username, this is
 * enforced with the implementation of
 * a {@code Set} Data Structure. Assigning a username that already exists will result in
 * an {@code IllegalArgumentException}.</p>
 *
 * <p>This class is intended for in-memory representation and can be easily extended
 * for file or database storage.</p>
 *
 * @author AJ
 * @version 0.1
 * @since 2025-06-20
 */
public class Profile {

    /** Set of all existing usernames. */
    private static final Set<String> existingUsernames = new HashSet<>();

    private String username;
    private String password;
    private String sex;
    private LocalDate dob;
    private int height;
    private int weight;

    /**
     * Constructs a new {@code Profile} with the provided information.
     *
     * @param username the unique username for the profile
     * @param password the user's password
     * @param sex the user's gender
     * @param dob the user's date of birth
     * @param height the user's height in centimeters
     * @param weight the user's weight in kilograms
     * @throws IllegalArgumentException if the username is already taken
     */
    public Profile(String username, String password, String sex, LocalDate dob, int height, int weight) {
        this.setUsername(username);
        this.setPassword(password);
        this.setSex(sex);
        this.setDob(dob);
        this.setHeight(height);
        this.setWeight(weight);

        existingUsernames.add(username);
    }

    /**
     * Returns the username of this profile.
     *
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Returns the password of this profile.
     *
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Returns the user's sex.
     *
     * @return the sex
     */
    public String getSex() {
        return this.sex;
    }

    /**
     * Returns the user's date of birth.
     *
     * @return the date of birth as a {@code LocalDate}
     */
    public LocalDate getDob() {
        return this.dob;
    }

    /**
     * Returns the user's height in centimeters.
     *
     * @return the height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Returns the user's weight in kilograms.
     *
     * @return the weight
     */
    public int getWeight() {
        return this.weight;
    }

    /**
     * Sets a new username, ensuring uniqueness.
     *
     * @param username the new username
     * @throws IllegalArgumentException if the username is already taken
     */
    public void setUsername(String username) {
        userExists(username);
        existingUsernames.remove(this.username); // Remove old username
        this.username = username;
        existingUsernames.add(username);
    }

    /**
     * Sets a new password for this profile.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the user's sex.
     *
     * @param sex the new sex value
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * Sets the user's date of birth.
     *
     * @param dob the new date of birth as a {@code LocalDate}
     */
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    /**
     * Sets the user's height.
     *
     * @param height the new height in centimeters
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Sets the user's weight.
     *
     * @param weight the new weight in kilograms
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Checks whether a username already exists and throws an exception if it does.
     *
     * @param username the username to validate
     * @throws IllegalArgumentException if the username is already taken
     */
    private void userExists(String username) {
        if (existingUsernames.contains(username)) {
            throw new IllegalArgumentException("Username " + username + " is already taken!");
        }
    }

    /**
     * Returns a string representation of the profile, excluding the password.
     *
     * @return a human-readable summary of the profile
     */
    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append("Profile: ")
             .append("username = ").append(username)
             .append(", sex = ").append(sex)
             .append(", date of birth = ").append(dob.toString())
             .append(", height = ").append(height)
             .append(", weight = ").append(weight);
        return build.toString();
    }
}
