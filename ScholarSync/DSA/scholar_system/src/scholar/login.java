package scholar;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class login extends JFrame {
    private JTextField userField;
    private JPasswordField passwordField;
    private UserDAO userDAO;
    private Color primaryColor = new Color(25, 118, 210);
    private Color secondaryColor = new Color(66, 165, 245);
    private Color backgroundColor = new Color(248, 250, 253);

    public login() {
        userDAO = new UserDAO();

        setTitle("ScholarSync Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Main Container
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(backgroundColor);

        // Left Panel (Logo and Welcome Message)
        JPanel leftPanel = createLeftPanel();
        mainContainer.add(leftPanel, BorderLayout.WEST);

        // Right Panel (Login Form)
        JPanel rightPanel = createRightPanel();
        mainContainer.add(rightPanel, BorderLayout.CENTER);

        add(mainContainer);
        setVisible(true);
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(683, 768));
        leftPanel.setBackground(primaryColor);
        leftPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Logo
        JLabel logoLabel = new JLabel("ScholarSync");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        logoLabel.setForeground(Color.WHITE);
        leftPanel.add(logoLabel, gbc);

        // Welcome Message
        JLabel welcomeMsg = new JLabel("Welcome back!");
        welcomeMsg.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        welcomeMsg.setForeground(Color.WHITE);
        leftPanel.add(welcomeMsg, gbc);

        return leftPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Login Form Container
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        formPanel.setPreferredSize(new Dimension(400, 400));

        // Login Header
        JLabel loginHeader = new JLabel("Log in");
        loginHeader.setFont(new Font("Segoe UI", Font.BOLD, 24));
        loginHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(loginHeader);
        formPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Email Field
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(emailLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        userField = new JTextField();
        userField.setMaximumSize(new Dimension(400, 40));
        userField.setPreferredSize(new Dimension(400, 40));
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(userField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Password Field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(passwordLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(400, 40));
        passwordField.setPreferredSize(new Dimension(400, 40));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(passwordField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setMaximumSize(new Dimension(400, 40));
        loginButton.setPreferredSize(new Dimension(400, 40));
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(primaryColor);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(loginButton);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Register Link
        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerPanel.setBackground(Color.WHITE);
        registerPanel.setMaximumSize(new Dimension(400, 30));
        
        JLabel registerText = new JLabel("Don't have an account? ");
        registerText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JLabel registerLink = new JLabel("Register here");
        registerLink.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerLink.setForeground(primaryColor);
        registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        registerPanel.add(registerText);
        registerPanel.add(registerLink);
        formPanel.add(registerPanel);

        // Error Label
        JLabel errorLabel = new JLabel("");
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(errorLabel);

        rightPanel.add(formPanel, gbc);

        // Login Button Action
        loginButton.addActionListener(e -> {
            String email = userField.getText();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Please enter your email and password");
                return;
            }

            // Check for admin login
            if (Main.isAdmin(email, password)) {
                dispose();
                new AdminDashboard();
                return;
            }

            // Regular user login
            User user = userDAO.login(email, password);

            if (user != null) {
                dispose();
                new MyFrame(user);
            } else {
                errorLabel.setText("Invalid email or password");
            }
        });

        // Register Link Action
        registerLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new register();
            }
        });

        return rightPanel;
    }
}
