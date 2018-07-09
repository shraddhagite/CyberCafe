package cybercafe;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import cybercafe.CyberCafe;
import javafx.embed.swing.JFXPanel;

public class AdminLogin extends Frame {
    public AdminLogin() {
        Label email = new Label("Email: ");
        Label pass = new Label("Password: ");
        TextField em = new TextField(20);
        TextField pw = new TextField(20);
        pw.setEchoChar('*');
        Button login = new Button("Login");
        login.addActionListener((ActionEvent e) -> {
            if(DatabaseHandler.loginAdmin(em.getText().trim(), pw.getText().trim())){
                int input = JOptionPane.showOptionDialog(null, "Login Successful!", "Success!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if(input == JOptionPane.OK_OPTION) {
                    setVisible(false);
                    dispose();
                    CyberCafe.isAdminLoggedIn = true;
                    new AdminDashboard(em.getText().trim());
                }
            } else {
                JOptionPane.showMessageDialog(new JFrame(),"Login Unsuccessful! Check credentials.", "Alert", JOptionPane.WARNING_MESSAGE);    
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                dispose();
                DatabaseHandler.closeConnection();
            }
        });
        setLayout(new FlowLayout());
        setSize(300, 300);
        setVisible(true);
        add(new Label("ADMIN LOGIN"));
        add(email);
        add(em);
        add(pass);
        add(pw);
        add(login);
    }
}