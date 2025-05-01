package scholar;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class register extends JFrame {

    public register() {
        this.setTitle("ScholarSync Registration");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1920, 1080);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        // Background Panel
        JPanel background = new JPanel();
        background.setBackground(new Color(0xF8FAFD));
        background.setBounds(0, 0, 1920, 1080);
        background.setLayout(null);
        this.add(background);

        // Registration Panel
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(null);
        registerPanel.setBounds((1920 - 700) / 2, (1080 - 700) / 2, 700, 700); // Resized to fit added fields
        registerPanel.setBackground(Color.WHITE);
        registerPanel.setBorder(new LineBorder(Color.BLACK, 1, true));
        background.add(registerPanel);

        JLabel titleLabel = new JLabel("<html>Welcome to <font color='#0000FF'>Scholar</font>Sync!</html>", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setBounds(0, 20, 700, 40);
        registerPanel.add(titleLabel);

        String[] labels = {
            "First Name", "Middle Name", "Last Name",
            "Email", "Password", "Confirm Password",
            "School", "Track and Strand", "GPA", "Monthly Family Income"
        };

        JTextField[] fields = new JTextField[labels.length];
        JComboBox<String> incomeBox = null;

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
            lbl.setBounds(50, 80 + (i * 50), 200, 25);
            registerPanel.add(lbl);

            if (labels[i].toLowerCase().contains("password")) {
                JPasswordField passField = new JPasswordField();
                passField.setBounds(250, 80 + (i * 50), 400, 30);
                registerPanel.add(passField);
                fields[i] = passField;
            } else if (labels[i].equals("Monthly Family Income")) {
                incomeBox = new JComboBox<>(new String[]{
                    "< ₱10,000", "< ₱30,000", "< ₱70,000", "< ₱100,000", "₱100,000 above"
                });
                incomeBox.setBounds(250, 80 + (i * 50), 400, 30);
                registerPanel.add(incomeBox);

                JTextField dummy = new JTextField(); // used for tracking
                dummy.setVisible(false);
                dummy.setName("incomeBox");
                dummy.putClientProperty("combo", incomeBox);
                fields[i] = dummy;
            } else {
                JTextField txtField = new JTextField();
                txtField.setBounds(250, 80 + (i * 50), 400, 30);
                registerPanel.add(txtField);
                fields[i] = txtField;
            }
        }

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(290, 650, 120, 35);
        registerPanel.add(registerButton);

        // Action listener
        registerButton.addActionListener(e -> {
            String[] fieldValues = new String[fields.length];
            boolean hasEmptyField = false;

            for (int i = 0; i < fields.length; i++) {
                if (fields[i] instanceof JPasswordField) {
                    fieldValues[i] = new String(((JPasswordField) fields[i]).getPassword()).trim();
                } else if (fields[i].getName() != null && fields[i].getName().equals("incomeBox")) {
                    JComboBox combo = (JComboBox) fields[i].getClientProperty("combo");
                    fieldValues[i] = (String) combo.getSelectedItem();
                    if (combo.getSelectedIndex() == -1) {
                        hasEmptyField = true;
                        break;
                    }
                } else {
                    fieldValues[i] = fields[i].getText().trim();
                    if (fieldValues[i].isEmpty()) {
                        hasEmptyField = true;
                        break;
                    }
                }
            }

            if (hasEmptyField) {
                JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Missing Information", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate email format
            String email = fieldValues[3]; // Email is now at index 3
            String emailPattern = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
            if (!email.matches(emailPattern)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address.", "Invalid Email", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate password match
            if (!fieldValues[4].equals(fieldValues[5])) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!", "Password Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate GPA
            try {
                double gpa = Double.parseDouble(fieldValues[8]); // GPA at index 8
                if (gpa < 0.0 || gpa > 100.0) {
                    JOptionPane.showMessageDialog(this, "GPA must be between 0.0 and 100.0", "Invalid GPA", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "GPA must be a number", "Invalid GPA", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // All validations passed
            JOptionPane.showMessageDialog(this, "Registration successful!");
        });

        this.setVisible(true);
    }
}
