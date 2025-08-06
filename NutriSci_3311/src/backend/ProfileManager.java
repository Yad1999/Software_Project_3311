//package backend;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class ProfileManager {
//    private Map<String, UserProfile> profiles;
//
//    public ProfileManager() {
//        profiles = new HashMap<>();
//    }
//
//    public boolean addProfile(@NotNull UserProfile profile) {
//        if (profile == null || profiles.containsKey(profile.getUsername())) {
//            return false;
//        }
//        profiles.put(profile.getUsername(), profile);
//        return true;
//    }
//
//
//    public UserProfile getProfile(String username) {
//        return profiles.get(username);
//    }
//
//    public boolean profileExists(String username) {
//        return profiles.containsKey(username);
//    }
//
//    public Map<String, UserProfile> getAllProfiles() {
//        return profiles;
//    }
//
//    public boolean updateProfile(UserProfile profile) {
//        String username = profile.getUsername();
//        if (profiles.containsKey(username)) {
//            profiles.put(username, profile);
//            return true;
//        }
//        return false;
//    }
//
//    public boolean removeProfile(String username) {
//        return profiles.remove(username) != null;
//    }
//
//}
//
