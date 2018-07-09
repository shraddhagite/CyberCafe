package cybercafe;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.JOptionPane;

public class AdminDashboard extends Frame {
    private static String email;
    public AdminDashboard(String email){
        this.email = email;
        if(!CyberCafe.isAdminLoggedIn){
            setVisible(false);
            dispose();
            new AdminLogin();
        }
        Button checkSystem = new Button("Check System");
        checkSystem.addActionListener((ActionEvent e) -> {
            
        });
        Button addNewUser = new Button("New User");
        addNewUser.addActionListener((ActionEvent e) -> {
            new AddNewUser();
        });
        Button updateUser = new Button("Update User");
        Button logout = new Button("Logout");
        logout.addActionListener((ActionEvent e) -> { 
            CyberCafe.isAdminLoggedIn = false;
            this.email = null;
            setVisible(false);
            dispose();
            JOptionPane.showOptionDialog(null, "Logout Successful!", "Success!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            new AdminLogin();
        });
        setLayout(new FlowLayout());
        setSize(300, 300);
        setVisible(true);
        add(new Label("ADMIN DASHBOARD"));
        add(checkSystem);
        add(addNewUser);
        add(updateUser);
        add(logout);
    }
}
