package scholar;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicComboPopup;

import java.awt.*;
import java.awt.event.*;

public class register extends JFrame {
    private JTextField[] fields;
    private UserDAO userDAO;
    private Color primaryColor = new Color(25, 118, 210);
    private Color secondaryColor = new Color(66, 165, 245);
    private Color backgroundColor = new Color(248, 250, 253);

    public register() {
        userDAO = new UserDAO();
    
        setTitle("ScholarSync Registration");
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

        // Right Panel (Registration Form)
        JPanel rightPanel = createRightPanel();
        mainContainer.add(rightPanel, BorderLayout.CENTER);

        add(mainContainer);
        setVisible(true);
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(400, 768));
        leftPanel.setBackground(primaryColor);
        leftPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Logo
        JLabel logoLabel = new JLabel("ScholarSync");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        logoLabel.setForeground(Color.WHITE);
        leftPanel.add(logoLabel, gbc);

        // Welcome Message
        JLabel welcomeMsg = new JLabel("Create your account");
        welcomeMsg.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        welcomeMsg.setForeground(Color.WHITE);
        leftPanel.add(welcomeMsg, gbc);

        return leftPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new BorderLayout());

        // Form Container with Scroll
        JPanel formContainer = new JPanel();
        formContainer.setBackground(Color.WHITE);
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
        formContainer.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        // Form Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setMaximumSize(new Dimension(1000, 60));
        
        JLabel titleLabel = new JLabel("Registration Form");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(33, 33, 33));
        titlePanel.add(titleLabel);
        
        formContainer.add(titlePanel);
        formContainer.add(Box.createRigidArea(new Dimension(0, 30)));

        // Form Fields
        String[] labels = {
            "First Name", "Middle Name", "Last Name",
            "Email", "Password", "Confirm Password",
            "School", "Track and Strand", "GWA",
            "Monthly Family Income", "College Course", "Student ID"
        };

        fields = new JTextField[labels.length];
        JComboBox<String> incomeBox = null;

        // Create two columns for form fields with better spacing
        JPanel fieldsPanel = new JPanel(new GridLayout(6, 2, 20, 20));
        fieldsPanel.setBackground(Color.WHITE);
        fieldsPanel.setMaximumSize(new Dimension(1000, 600));
        fieldsPanel.setPreferredSize(new Dimension(1000, 500));
        fieldsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (int i = 0; i < labels.length; i++) {
            JPanel fieldContainer = new JPanel();
            fieldContainer.setLayout(new BoxLayout(fieldContainer, BoxLayout.Y_AXIS));
            fieldContainer.setBackground(Color.WHITE);
            fieldContainer.setMaximumSize(new Dimension(450, 80));
            fieldContainer.setPreferredSize(new Dimension(450, 80));

            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            label.setForeground(new Color(66, 66, 66));
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            fieldContainer.add(label);
            fieldContainer.add(Box.createRigidArea(new Dimension(0, 8)));

            if (labels[i].toLowerCase().contains("password")) {
                JPasswordField passField = new JPasswordField();
                styleField(passField);
                fieldContainer.add(passField);
                fields[i] = passField;
            } else if (labels[i].equals("Monthly Family Income")) {
                incomeBox = new JComboBox<>(new String[]{
                    "< ₱10,000", "< ₱30,000", "< ₱70,000", "< ₱100,000", "₱100,000 above"
                });
                styleComboBox(incomeBox);
                fieldContainer.add(incomeBox);
                
                JTextField dummy = new JTextField();
                dummy.setVisible(false);
                dummy.setName("incomeBox");
                dummy.putClientProperty("combo", incomeBox);
                fields[i] = dummy;
            } else {
                JTextField textField = new JTextField();
                styleField(textField);
                fieldContainer.add(textField);
                fields[i] = textField;
            }

            fieldsPanel.add(fieldContainer);
        }

        formContainer.add(fieldsPanel);
        formContainer.add(Box.createRigidArea(new Dimension(0, 30)));

        // Buttons Panel with better alignment
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setMaximumSize(new Dimension(1000, 60));

        JButton registerButton = new JButton("Register");
        styleButton(registerButton, true);
        
        JButton backButton = new JButton("Back to Login");
        styleButton(backButton, false);

        buttonsPanel.add(registerButton);
        buttonsPanel.add(backButton);

        formContainer.add(buttonsPanel);

        // Error Label
        JLabel errorLabel = new JLabel("");
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formContainer.add(Box.createRigidArea(new Dimension(0, 20)));
        formContainer.add(errorLabel);

        // Add form to scroll pane
        JScrollPane scrollPane = new JScrollPane(formContainer);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Register Button Action
        registerButton.addActionListener(e -> {
            if (validateAndRegister()) {
                JOptionPane.showMessageDialog(this, "Registration successful! You can now login.");
                dispose();
                new login();
            }
        });

        // Back Button Action
        backButton.addActionListener(e -> {
            dispose();
            new login();
        });

        return rightPanel;
    }

    private void styleField(JTextField field) {
        field.setMaximumSize(new Dimension(450, 40));
        field.setPreferredSize(new Dimension(450, 40));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setBackground(Color.WHITE);

        // Add focus listener for better visual feedback
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(primaryColor),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setMaximumSize(new Dimension(450, 40));
        comboBox.setPreferredSize(new Dimension(450, 40));
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        comboBox.setBackground(Color.WHITE);
        
        // Style the combo box popup
        Object child = comboBox.getAccessibleContext().getAccessibleChild(0);
        BasicComboPopup popup = (BasicComboPopup)child;
        JList list = popup.getList();
        list.setSelectionBackground(primaryColor);
        list.setSelectionForeground(Color.WHITE);
        
        // Add focus listener for better visual feedback
        comboBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                comboBox.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(primaryColor),
                    BorderFactory.createEmptyBorder(4, 8, 4, 8)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                comboBox.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(4, 8, 4, 8)
                ));
            }
        });
    }

    private void styleButton(JButton button, boolean isPrimary) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setPreferredSize(new Dimension(200, 48));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);

        if (isPrimary) {
            button.setBackground(primaryColor);
            button.setForeground(Color.WHITE);
            button.setBorderPainted(false);
        } else {
            button.setBackground(Color.WHITE);
            button.setForeground(primaryColor);
            button.setBorder(new LineBorder(primaryColor, 2));
        }

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (isPrimary) {
                    button.setBackground(secondaryColor);
                } else {
                    button.setBackground(new Color(245, 245, 255));
                }
            }
            public void mouseExited(MouseEvent e) {
                if (isPrimary) {
                    button.setBackground(primaryColor);
                } else {
                    button.setBackground(Color.WHITE);
                }
            }
        });
    }

    private boolean validateAndRegister() {
        // Get values from all fields
        String[] fieldValues = new String[fields.length];
        boolean hasEmptyField = false;
        
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] instanceof JPasswordField) {
                fieldValues[i] = new String(((JPasswordField) fields[i]).getPassword()).trim();
            } else if (fields[i].getName() != null && fields[i].getName().equals("incomeBox")) {
                JComboBox<String> combo = (JComboBox<String>) fields[i].getClientProperty("combo");
                fieldValues[i] = (String) combo.getSelectedItem();
                if (combo.getSelectedIndex() == -1) {
                    hasEmptyField = true;
                    break;
                }
            } else {
                fieldValues[i] = fields[i].getText().trim();
                if (fieldValues[i].isEmpty() && i != 1) { // Middle name can be empty
                    hasEmptyField = true;
                    break;
                }
            }
        }
        
        if (hasEmptyField) {
            JOptionPane.showMessageDialog(this, "Please fill out all required fields.", 
                                        "Missing Information", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validate email format
        String email = fieldValues[3];
        String emailPattern = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailPattern)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.", 
                                        "Invalid Email", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Check if email already exists
        if (userDAO.emailExists(email)) {
            JOptionPane.showMessageDialog(this, "This email is already registered.", 
                                        "Email Exists", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validate password match
        if (!fieldValues[4].equals(fieldValues[5])) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", 
                                        "Password Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validate GPA
        double gpa;
        try {
            gpa = Double.parseDouble(fieldValues[8]);
            if (gpa < 0.0 || gpa > 100.0) {
                JOptionPane.showMessageDialog(this, "GWA must be between 0.0 and 100.0", 
                                            "Invalid GPA", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "GWA must be a number", 
                                        "Invalid GWA", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Create User object
        User user = new User(
            fieldValues[0],  // First Name
            fieldValues[1],  // Middle Name
            fieldValues[2],  // Last Name
            fieldValues[3],  // Email
            fieldValues[4],  // Password
            fieldValues[6],  // School
            fieldValues[7],  // Track and Strand
            gpa,            // GPA
            fieldValues[9], // Monthly Income
            fieldValues[10], // College Course
            fieldValues[11]  // Student ID
        );
        
        // Register the user
        return userDAO.registerUser(user);
    }
}