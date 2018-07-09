package cybercafe;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseHandler {
    private static Connection con;
    private static final String ALPHA_NUMERIC_STRING = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
    private static final String url = "jdbc:sqlserver://localhost:1433;databaseName=cyber;integratedSecurity=true";
    public DatabaseHandler(){ 
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
            con = DriverManager.getConnection(url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
   
    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
    
    public static String addNewUser(String email, String name, String phone, String address) {
        try {
            PreparedStatement stat = con.prepareStatement("insert into users(uName, email, phone, address, password) values (?, ?, ?, ?, ?)");
            stat.setString(1, name);
            stat.setString(2, email);
            stat.setString(3, phone);
            stat.setString(4, address);
            String pass = randomAlphaNumeric(8);
            stat.setString(5, pass);
            stat.execute();
            return pass;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void closeConnection() {
        try {
            con.close();
            Runtime.getRuntime().halt(0);
        } catch(Exception r){
            r.printStackTrace();
        }
    }
    
    public static boolean loginAdmin(String email, String password){
        try {
            PreparedStatement stat = con.prepareStatement("select * from cybermin where email = ? and uPassword = ?");
            stat.setString(1, email);
            stat.setString(2, password);
            ResultSet rt = stat.executeQuery();
            return rt.next();
        } catch (Exception t){
            t.printStackTrace();
            return false;
        }
    }
}
