package scholar;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class login extends JFrame {

    public login() {
        this.setTitle("ScholarSync Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1920, 1080);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        // Background Panel
        JPanel background = new JPanel();
        Color customColor = new Color(0xF8FAFD);
        background.setBackground(customColor);
        background.setBounds(0, 0, 1920, 1080);
        background.setLayout(null);
        this.add(background);

        // Login Panel (Centered)
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds((1920 - 600) / 2, (1080 - 300) / 2, 600, 300); // Centered
        loginPanel.setBackground(customColor);
        background.add(loginPanel);

        // Login Label
        JLabel loginLabel = new JLabel("<html>Welcome to <font color='#0000FF'>Scholar</font>Sync!</html>", SwingConstants.CENTER); 
        loginLabel.setFont(new Font("Konkhmer Sleokchher", Font.PLAIN, 24));
        loginLabel.setBounds(0, 20, 600, 40); // full width of the panel
        loginPanel.add(loginLabel);

        // Username Container
        JPanel userContainer = new JPanel();
        userContainer.setBounds(0, 80, 600, 40);
        userContainer.setBackground(Color.WHITE);
        userContainer.setLayout(null);
        userContainer.setBorder(new LineBorder(Color.BLACK, 1, true));
        loginPanel.add(userContainer);

        JTextField userField = new JTextField("Email");
        userField.setBounds(15, 5, 530, 30);
        userField.setForeground(Color.GRAY);
        userField.setBorder(null);
        userField.setBackground(Color.WHITE);
        userField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        userContainer.add(userField);

        // Password Container
        JPanel passwordContainer = new JPanel();
        passwordContainer.setBounds(0, 140, 600, 40);
        passwordContainer.setBackground(Color.WHITE);
        passwordContainer.setLayout(null);
        passwordContainer.setBorder(new LineBorder(Color.BLACK, 1, true));
        loginPanel.add(passwordContainer);

        JPasswordField passwordField = new JPasswordField("Password");
        passwordField.setBounds(15, 5, 530, 30);
        passwordField.setForeground(Color.GRAY);
        passwordField.setBorder(null);
        passwordField.setBackground(Color.WHITE);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setEchoChar((char) 0); // No masking when placeholder
        passwordContainer.add(passwordField);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(240, 200, 120, 35);
        loginPanel.add(loginButton);

        // Placeholder Logic - Username
        userField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (userField.getText().equals("Email")) {
                    userField.setText("");
                    userField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (userField.getText().isEmpty()) {
                    userField.setText("Email");
                    userField.setForeground(Color.GRAY);
                }
            }
        });

        // Placeholder Logic - Password (Completed)
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("Password")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('\u2022'); // Bullet char
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("Password");
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0); // Show placeholder text
                }
            }
        });
        background.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Username reset
                if (!userField.hasFocus() && userField.getText().isEmpty()) {
                    userField.setText("Email");
                    userField.setForeground(Color.GRAY);
                }

                // Password reset
                if (!passwordField.hasFocus() && String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("Password");
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0);
                }
                
                background.requestFocusInWindow(); // remove focus from textfields
            }
        });
        this.setVisible(true);
    }
}
