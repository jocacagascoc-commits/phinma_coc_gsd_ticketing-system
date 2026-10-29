package phinma_coc_gsd_ticketing_system.connections;

public class UserSession {
    private static int userId = 0;
    private static String email;
    private static String fullName;
    private static String role;

    // Handles 2 parameters: UserSession.setUser(userId, email)
    public static void setUser(int id, String userEmail) {
        userId = id;
        email = userEmail;
    }

    // Handles 4 parameters if you also want to save name and role:
    public static void setUser(int id, String userEmail, String name, String userRole) {
        userId = id;
        email = userEmail;
        fullName = name;
        role = userRole;
    }

    // Checks if a valid user is currently logged in
    public static boolean isLoggedIn() {
        return userId > 0;
    }

    // Getters to retrieve the logged-in user details anywhere in your app
    public static int getUserId() { return userId; }
    public static String getEmail() { return email; }
    public static String getFullName() { return fullName; }
    public static String getRole() { return role; }
    
    public static void setUserId(int id) { userId = id; }
    public static void setEmail(String userEmail) { email = userEmail; }
    public static void setFullName(String name) { fullName = name; }
    public static void setRole(String userRole) { role = userRole; }

    // Resets session variables on logout
    public static void clearSession() {
        userId = 0;
        email = null;
        fullName = null;
        role = null;
    }

    // Alias for clearSession() to support homepage logout call
    public static void clear() {
        clearSession();
    }
}