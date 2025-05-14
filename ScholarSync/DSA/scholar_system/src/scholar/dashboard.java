package scholar;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class dashboard extends JFrame {
    private UserDAO userDAO;
    private User currentUser;

    public dashboard(User loggedInUser) {
        // Initialize userDAO and currentUser
        userDAO = new UserDAO();
        this.currentUser = loggedInUser;

        this.setTitle("ScholarSync User Dashboard");
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

        // Dashboard Panel
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(null);
        dashboardPanel.setBounds((1920 - 700) / 2, (1080 - 800) / 2, 700, 800); // Panel size
        dashboardPanel.setBackground(Color.WHITE);
        dashboardPanel.setBorder(new LineBorder(Color.BLACK, 1, true));
        background.add(dashboardPanel);

        // Title Label
        JLabel titleLabel = new JLabel("<html>Welcome, <font color='#0000FF'>" + currentUser.getFirstName() + "!</font></html>", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setBounds(0, 20, 700, 40);
        dashboardPanel.add(titleLabel);

        // User Information Display
        JLabel nameLabel = new JLabel("Name: " + currentUser.getFirstName() + " " + currentUser.getLastName());
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nameLabel.setBounds(50, 80, 600, 25);
        dashboardPanel.add(nameLabel);

        JLabel emailLabel = new JLabel("Email: " + currentUser.getEmail());
        emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        emailLabel.setBounds(50, 120, 600, 25);
        dashboardPanel.add(emailLabel);

        JLabel schoolLabel = new JLabel("School: " + currentUser.getSchool());
        schoolLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        schoolLabel.setBounds(50, 160, 600, 25);
        dashboardPanel.add(schoolLabel);

        JLabel courseLabel = new JLabel("College Course: " + currentUser.getCollegeCourse());
        courseLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        courseLabel.setBounds(50, 200, 600, 25);
        dashboardPanel.add(courseLabel);

        JLabel gpaLabel = new JLabel("GPA: " + currentUser.getGpa());
        gpaLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gpaLabel.setBounds(50, 240, 600, 25);
        dashboardPanel.add(gpaLabel);

        // Buttons for actions
        JButton editProfileButton = new JButton("Edit Profile");
        editProfileButton.setBounds(50, 300, 200, 35);
        dashboardPanel.add(editProfileButton);

        JButton returnButton = new JButton("Return");
        returnButton.setBounds(50, 350, 200, 35);
        dashboardPanel.add(returnButton);

        // Action listener for edit profile button
        editProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to navigate to the profile editing screen
                JOptionPane.showMessageDialog(dashboard.this, "Profile Editing Screen - Under Construction");
            }
        });

        // Action listener for logout button
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                new MyFrame(currentUser); // Open the login screen again
            }
        });

        this.setVisible(true);
    }
}
