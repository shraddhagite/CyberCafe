package cybercafe;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AddNewUser extends Frame {
    public AddNewUser(){
        Label email = new Label("Email: ");
        Label name = new Label("Name: ");
        Label phone = new Label("Phone: ");
        Label address = new Label("Address: ");
        TextField emailText = new TextField(20);
        TextField nameText = new TextField(20);
        TextField phoneText = new TextField(10);
        TextField addressText = new TextField(20);
        Button addUser = new Button("Add");
        addUser.addActionListener((ActionEvent e) -> {
            String tp = DatabaseHandler.addNewUser(emailText.getText().trim(), nameText.getText().trim(), phoneText.getText().trim(), addressText.getText().trim());
            if(tp != null) {
                JOptionPane.showMessageDialog(new JFrame(),"New user added successfully! Temporary password: " + tp, "Alert", JOptionPane.WARNING_MESSAGE);    
                emailText.setText("");
                phoneText.setText("");
                nameText.setText("");
                addressText.setText("");
            } else {
                JOptionPane.showMessageDialog(new JFrame(),"Something went wrong!", "Alert", JOptionPane.WARNING_MESSAGE);    
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                dispose();
            }
        });
        setLayout(new FlowLayout());
        setSize(300, 300);
        setVisible(true);
        add(new Label("ADD NEW USER"));
        add(email);
        add(emailText);
        add(name);
        add(nameText);
        add(phone);
        add(phoneText);
        add(address);
        add(addressText);
        add(addUser);
    }
}
