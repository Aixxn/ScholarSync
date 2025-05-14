package scholar;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class login extends JFrame {
    private JTextField userField;
    private JPasswordField passwordField;
    private UserDAO userDAO;

    public login() {
        userDAO = new UserDAO();

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
        loginPanel.setBounds((1920 - 600) / 2, (1080 - 400) / 2, 600, 400); // Made taller for error message
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(new LineBorder(Color.BLACK, 1, true));
        background.add(loginPanel);

        // Login Label
        JLabel loginLabel = new JLabel("<html>Welcome to <font color='#0000FF'>Scholar</font>Sync!</html>", SwingConstants.CENTER);
        loginLabel.setFont(new Font("Konkhmer Sleokchher", Font.PLAIN, 24));
        loginLabel.setBounds(0, 20, 600, 40); // full width of the panel
        loginPanel.add(loginLabel);

        // Username Container
        JPanel userContainer = new JPanel();
        userContainer.setBounds(100, 80, 400, 40);
        userContainer.setBackground(Color.WHITE);
        userContainer.setLayout(null);
        userContainer.setBorder(new LineBorder(Color.BLACK, 1, true));
        loginPanel.add(userContainer);

        userField = new JTextField("Email");
        userField.setBounds(15, 5, 370, 30);
        userField.setForeground(Color.GRAY);
        userField.setBorder(null);
        userField.setBackground(Color.WHITE);
        userField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        userContainer.add(userField);

        // Password Container
        JPanel passwordContainer = new JPanel();
        passwordContainer.setBounds(100, 140, 400, 40);
        passwordContainer.setBackground(Color.WHITE);
        passwordContainer.setLayout(null);
        passwordContainer.setBorder(new LineBorder(Color.BLACK, 1, true));
        loginPanel.add(passwordContainer);

        passwordField = new JPasswordField("Password");
        passwordField.setBounds(15, 5, 370, 30);
        passwordField.setForeground(Color.GRAY);
        passwordField.setBorder(null);
        passwordField.setBackground(Color.WHITE);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setEchoChar((char) 0); // No masking when placeholder
        passwordContainer.add(passwordField);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(200, 230, 200, 35);
        loginPanel.add(loginButton);

        // Register Link
        JLabel registerLabel = new JLabel("Don't have an account? Register here", SwingConstants.CENTER);
        registerLabel.setForeground(Color.BLUE);
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLabel.setBounds(150, 280, 300, 30);
        loginPanel.add(registerLabel);

        // Error Message Label (initially hidden)
        JLabel errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(100, 320, 400, 30);
        errorLabel.setVisible(false);
        loginPanel.add(errorLabel);

        // Placeholder Logic - Username
        userField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (userField.getText().equals("Email")) {
                    userField.setText("");
                    userField.setForeground(Color.BLACK);
                }
                errorLabel.setVisible(false);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (userField.getText().isEmpty()) {
                    userField.setText("Email");
                    userField.setForeground(Color.GRAY);
                }
            }
        });

        // Placeholder Logic - Password
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("Password")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('\u2022'); // Bullet char
                }
                errorLabel.setVisible(false);
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

        // Click outside text fields resets placeholders
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

        // Login Button Action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = userField.getText();
                String password = new String(passwordField.getPassword());

                // Check for placeholders
                if (email.equals("Email") || password.equals("Password")) {
                    errorLabel.setText("Please enter your email and password");
                    errorLabel.setVisible(true);
                    return;
                }

                // Attempt login
                User user = userDAO.login(email, password);

                if (user != null) {
                    // Login successful - close login form and open main application
                    JOptionPane.showMessageDialog(login.this,
                            "Welcome, " + user.getFirstName() + "!",
                            "Login Successful",
                            JOptionPane.INFORMATION_MESSAGE);
                    login.this.dispose();

                    // Pass the authenticated user to MyFrame
                    new MyFrame(user); // Open main application window with user data
                } else {
                    // Login failed
                    errorLabel.setText("Invalid email or password");
                    errorLabel.setVisible(true);
                }
            }
        });

        // Register Label Click Action
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                login.this.dispose();
                new register();
            }
        });

        this.setVisible(true);
    }
}
