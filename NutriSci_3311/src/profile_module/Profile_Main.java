package profile_module;

import java.time.LocalDate;

/**
 * The {@code Profile_Main} is only temporary to test the Profile module
 * <p>Test cases:
 * <ul>
 *   <li>Creating profiles with unique usernames</li>
 *   <li>Attempting to create a profile with a duplicate username (expected to fail)</li>
 *   <li>Changing a profile's username to an already existing one (expected to fail)</li>
 * </ul>
 * </p>
 *
 * @author AJ
 * @version 0.1
 * @since 2025-06-20
 */
public class Profile_Main {

    public static void main(String[] args) {
        try {
            Profile p1 = new Profile("AJDCruz", "ajiscool123", "M", LocalDate.of(1995, 6, 20), 170, 60);
            System.out.println("A Profile has been Created! \n" + p1 + "\n");

            Profile p2 = new Profile("WinreyFits", "WinsFitness@456", "F", LocalDate.of(2008, 7, 2), 160, 50);
            System.out.println("A Profile has been Created! \n" + p2 + "\n");

            // Attempt to create a profile with a duplicate username
            Profile p3 = new Profile("AJDCruz", "whoistakingthisuser", "M", LocalDate.of(2001, 2, 19), 170, 60);
            System.out.println("A Profile has been Created! \n" + p3 + "\n");  // This line won't be reached
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }

        try {
            Profile p4 = new Profile("SomeDude", "ACoolPassword", "M", LocalDate.of(2005, 12, 20), 180, 90);
            System.out.println("A Profile has been Created! \n" + p4 + "\n");

            // Changing p4's username to one that's already taken
            p4.setUsername("WinreyFits");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }
}
