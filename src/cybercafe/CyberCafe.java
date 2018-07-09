package cybercafe;
import cybercafe.AdminLogin;

public class CyberCafe {
    public static boolean isAdminLoggedIn = false;
    public static void main(String[] args) {
        new DatabaseHandler();
        new AdminLogin();
    }
}
