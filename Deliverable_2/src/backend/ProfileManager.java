package backend;
import java.util.HashMap;
import java.util.Map;

public class ProfileManager {
    private Map<String, UserProfile> profiles;

    public ProfileManager() {
        profiles = new HashMap<>();
    }

    public void addProfile(UserProfile profile) {
        String username = profile.getUsername();
        if (profiles.containsKey(username)) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }
        profiles.put(username, profile);
    }

    public UserProfile getProfile(String username) {
        return profiles.get(username);
    }

    public boolean profileExists(String username) {
        return profiles.containsKey(username);
    }

    public Map<String, UserProfile> getAllProfiles() {
        return profiles;
    }
}

