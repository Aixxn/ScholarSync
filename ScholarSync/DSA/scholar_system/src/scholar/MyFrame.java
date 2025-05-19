package scholar;

import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;
import java.util.HashSet;
import java.util.Set;
import java.sql.*;

public class MyFrame extends JFrame implements ActionListener {

    private JButton loginButton;
    private JButton registerButton;
    private User currentUser;
    private UserDAO userDAO;
    // Updated color scheme for a cleaner look
    private Color primaryColor = new Color(52, 152, 219);    // Soft blue
    private Color secondaryColor = new Color(41, 128, 185);  // Darker blue
    private Color backgroundColor = new Color(245, 247, 250); // Light gray-blue
    private Color textColor = new Color(44, 62, 80);         // Dark blue-gray
    private Color cardColor = Color.WHITE;

    public MyFrame(User user) {
        this.currentUser = user;
        this.userDAO = new UserDAO();

        this.setTitle("ScholarSync");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1366, 768);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setBackground(backgroundColor);

        // Main Container
        JPanel mainContainer = new JPanel(new BorderLayout(0, 0));
        mainContainer.setBackground(backgroundColor);

        // Sidebar
        JPanel sidebar = createSidebar();
        mainContainer.add(sidebar, BorderLayout.WEST);

        // Content Area
        JPanel contentArea = createContentArea();
        mainContainer.add(contentArea, BorderLayout.CENTER);

        this.add(mainContainer);
        this.setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(280, 768));
        sidebar.setBackground(primaryColor);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(30, 25, 30, 25));

        // Logo Panel
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setOpaque(false);
        logoPanel.setMaximumSize(new Dimension(280, 50));
        
        JLabel logoLabel = new JLabel("ScholarSync");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        logoLabel.setForeground(Color.WHITE);
        logoPanel.add(logoLabel);

        sidebar.add(logoPanel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));

        if (currentUser != null) {
            // User Info Panel
            JPanel userPanel = new JPanel();
            userPanel.setOpaque(false);
            userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
            userPanel.setMaximumSize(new Dimension(280, 100));
            userPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel welcomeLabel = new JLabel("Welcome back,");
            welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            welcomeLabel.setForeground(new Color(255, 255, 255, 200));
            welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JLabel nameLabel = new JLabel(currentUser.getFirstName() + " " + currentUser.getLastName());
            nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
            nameLabel.setForeground(Color.WHITE);
            nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            userPanel.add(welcomeLabel);
            userPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            userPanel.add(nameLabel);
            sidebar.add(userPanel);
            sidebar.add(Box.createRigidArea(new Dimension(0, 40)));

            // Navigation Menu
            String[] navItems = {"Dashboard", "My Applications", "Profile", "Settings", "Logout"};
            for (String item : navItems) {
                JButton navButton = createNavButton(item);
                sidebar.add(navButton);
                sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        } else {
            // Auth Buttons Panel
            JPanel authPanel = new JPanel();
            authPanel.setOpaque(false);
            authPanel.setLayout(new BoxLayout(authPanel, BoxLayout.Y_AXIS));
            authPanel.setMaximumSize(new Dimension(280, 120));
            authPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            loginButton = createAuthButton("Login");
            registerButton = createAuthButton("Create Account");

            authPanel.add(loginButton);
            authPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            authPanel.add(registerButton);

            sidebar.add(authPanel);
        }

        return sidebar;
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(280, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add an icon indicator
        button.setLayout(new BorderLayout());
        JLabel iconLabel = new JLabel("›");
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        iconLabel.setForeground(Color.WHITE);
        button.add(iconLabel, BorderLayout.EAST);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(secondaryColor);
                button.setContentAreaFilled(true);
            }
            public void mouseExited(MouseEvent e) {
                button.setContentAreaFilled(false);
            }
        });

        button.addActionListener(e -> {
            switch (text) {
                case "Dashboard":
                    dispose();
                    new dashboard(currentUser);
                    break;
                case "Logout":
                    dispose();
                    new login();
                    break;
            }
        });

        return button;
    }

    private JButton createAuthButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(280, 50));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);

        if (text.equals("Login")) {
            button.setForeground(Color.WHITE);
            button.setBackground(secondaryColor);
            button.setBorderPainted(false);
        } else {
            button.setForeground(Color.WHITE);
            button.setBackground(null);
            button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            button.setContentAreaFilled(false);
        }

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (text.equals("Login")) {
                    button.setBackground(new Color(52, 152, 219));
                } else {
                    button.setBackground(new Color(255, 255, 255, 30));
                }
            }
            public void mouseExited(MouseEvent e) {
                if (text.equals("Login")) {
                    button.setBackground(secondaryColor);
                } else {
                    button.setBackground(null);
                }
            }
        });

        return button;
    }

    private JPanel createContentArea() {
        JPanel contentArea = new JPanel(new BorderLayout(0, 0));
        contentArea.setBackground(backgroundColor);
        contentArea.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(backgroundColor);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JLabel titleLabel = new JLabel("Available Scholarships");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(textColor);
        header.add(titleLabel, BorderLayout.WEST);

        // Search Bar
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 40));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        header.add(searchField, BorderLayout.EAST);

        contentArea.add(header, BorderLayout.NORTH);

        // Scholarships Panel
        JPanel scholarshipsPanel = new JPanel();
        scholarshipsPanel.setBackground(backgroundColor);
        scholarshipsPanel.setLayout(new BoxLayout(scholarshipsPanel, BoxLayout.Y_AXIS));

        // Add scholarship cards
        addScholarshipCards(scholarshipsPanel);

        JScrollPane scrollPane = new JScrollPane(scholarshipsPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(backgroundColor);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentArea.add(scrollPane, BorderLayout.CENTER);

        return contentArea;
    }

    private void addScholarshipCards(JPanel container) {
        // Clear existing components
        container.removeAll();
        
        // Get scholarship data from database
        String sql = "SELECT title, start_date, end_date, applicant_count, max_slots FROM scholarships";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String[] scholarship = {
                    rs.getString("title"),
                    rs.getDate("start_date").toString(),
                    rs.getDate("end_date").toString(),
                    String.valueOf(rs.getInt("applicant_count")),
                    String.valueOf(rs.getInt("max_slots"))
                };
                JPanel card = createScholarshipCard(scholarship);
                container.add(card);
                container.add(Box.createRigidArea(new Dimension(0, 20)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Refresh the container
        container.revalidate();
        container.repaint();
    }

    private void refreshScholarshipCards() {
        // Find the container panel
        Component[] components = this.getContentPane().getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                Component[] subComps = ((JPanel) comp).getComponents();
                for (Component subComp : subComps) {
                    if (subComp instanceof JPanel) {
                        Component[] furtherComps = ((JPanel) subComp).getComponents();
                        for (Component furtherComp : furtherComps) {
                            if (furtherComp instanceof JScrollPane) {
                                JScrollPane scrollPane = (JScrollPane) furtherComp;
                                JViewport viewport = scrollPane.getViewport();
                                if (viewport.getView() instanceof JPanel) {
                                    JPanel container = (JPanel) viewport.getView();
                                    addScholarshipCards(container);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private JPanel createScholarshipCard(String[] data) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(20, 0));
        card.setMaximumSize(new Dimension(1100, 140));
        card.setBackground(cardColor);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));

        // Make the entire card clickable
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Left Content
        JPanel leftContent = new JPanel();
        leftContent.setLayout(new BoxLayout(leftContent, BoxLayout.Y_AXIS));
        leftContent.setOpaque(false);

        JLabel titleLabel = new JLabel(data[0]);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(textColor);

        JPanel datesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 0));
        datesPanel.setOpaque(false);

        JLabel startDate = new JLabel("Start: " + data[1]);
        JLabel endDate = new JLabel("End: " + data[2]);
        startDate.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        endDate.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        startDate.setForeground(new Color(100, 100, 100));
        endDate.setForeground(new Color(100, 100, 100));

        datesPanel.add(startDate);
        datesPanel.add(endDate);

        leftContent.add(titleLabel);
        leftContent.add(Box.createRigidArea(new Dimension(0, 15)));
        leftContent.add(datesPanel);

        // Right Content
        JPanel rightContent = new JPanel(new BorderLayout(0, 15));
        rightContent.setOpaque(false);

        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 0));
        statsPanel.setOpaque(false);

        String applicants = data[3];
        String maxSlots = data[4];
        JLabel applicantsLabel = new JLabel(applicants + " Applicants");
        JLabel slotsLabel = new JLabel("0/" + maxSlots + " Slots");  // Always show accepted/max slots
        applicantsLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        slotsLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        applicantsLabel.setForeground(textColor);
        slotsLabel.setForeground(textColor);

        statsPanel.add(applicantsLabel);
        statsPanel.add(slotsLabel);

        JButton applyButton = new JButton("Apply Now");
        applyButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        applyButton.setForeground(Color.WHITE);
        applyButton.setBackground(primaryColor);
        applyButton.setBorderPainted(false);
        applyButton.setFocusPainted(false);
        applyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        applyButton.setPreferredSize(new Dimension(140, 40));

        // Add hover effect to apply button
        applyButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                applyButton.setBackground(secondaryColor);
                card.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(primaryColor),
                    BorderFactory.createEmptyBorder(25, 30, 25, 30)
                ));
            }
            public void mouseExited(MouseEvent e) {
                applyButton.setBackground(primaryColor);
                card.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(230, 230, 230)),
                    BorderFactory.createEmptyBorder(25, 30, 25, 30)
                ));
            }
        });

        // Add click handler for the apply button
        applyButton.addActionListener(e -> handleScholarshipApplication(data[0], applicantsLabel, slotsLabel));

        rightContent.add(statsPanel, BorderLayout.NORTH);
        rightContent.add(applyButton, BorderLayout.SOUTH);

        card.add(leftContent, BorderLayout.WEST);
        card.add(rightContent, BorderLayout.EAST);

        // Add click handler for the entire card
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleScholarshipApplication(data[0], applicantsLabel, slotsLabel);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(primaryColor),
                    BorderFactory.createEmptyBorder(25, 30, 25, 30)
                ));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(230, 230, 230)),
                    BorderFactory.createEmptyBorder(25, 30, 25, 30)
                ));
            }
        });

        return card;
    }

    private void handleScholarshipApplication(String scholarshipTitle, JLabel applicantsLabel, JLabel slotsLabel) {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this,
                "Please login to apply for scholarships.",
                "Login Required",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Check if user has already applied
        if (userDAO.hasApplied(currentUser.getEmail(), scholarshipTitle)) {
            JOptionPane.showMessageDialog(this,
                "You have already applied for this scholarship.",
                "Already Applied",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Show confirmation dialog
        int choice = JOptionPane.showConfirmDialog(this,
            "Would you like to apply for " + scholarshipTitle + "?",
            "Confirm Application",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            // Update the database
            if (userDAO.applyForScholarship(currentUser.getEmail(), scholarshipTitle)) {
                // Update only the applicants count in UI
                String currentApplicants = applicantsLabel.getText().split(" ")[0];
                int newCount = Integer.parseInt(currentApplicants) + 1;
                applicantsLabel.setText(newCount + " Applicants");

                // Also refresh the cards to ensure database sync
                refreshScholarshipCards();

                JOptionPane.showMessageDialog(this,
                    "Successfully applied for " + scholarshipTitle + "!\nGood luck!",
                    "Application Submitted",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to submit application. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            this.dispose();
            new login();
        } else if (e.getSource() == registerButton) {
            this.dispose();
            new register();
        }
    }
}
