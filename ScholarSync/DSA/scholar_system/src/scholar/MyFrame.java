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
    // Color scheme matching dashboard
    private Color primaryColor = new Color(25, 118, 210);    // Primary blue
    private Color secondaryColor = new Color(66, 165, 245);  // Secondary blue
    private Color backgroundColor = new Color(248, 250, 253); // Light background
    private Color textColor = new Color(30, 41, 59);         // Dark text
    private Color subtitleColor = new Color(100, 116, 139);  // Subtitle gray
    private Color cardBackground = new Color(255, 255, 255); // White for cards
    private Color borderColor = new Color(226, 232, 240);    // Border color
    private Color greenStatus = new Color(34, 197, 94);      // Green for "Open" status

    public MyFrame(User user) {
        this.currentUser = user;
        this.userDAO = new UserDAO();

        this.setTitle("ScholarSync");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1366, 768);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setBackground(backgroundColor);

        // Main Container with no spacing
        JPanel mainContainer = new JPanel(new BorderLayout(0, 0));
        mainContainer.setBackground(backgroundColor);
        mainContainer.setBorder(null);

        // Sidebar with shadow - using BorderLayout.WEST to ensure full height
        JPanel sidebar = createSidebar();
        sidebar.setBorder(new ShadowBorder());
        mainContainer.add(sidebar, BorderLayout.WEST);

        // Content Area with shadow
        JPanel contentArea = createContentArea();
        contentArea.setBorder(new ShadowBorder());
        mainContainer.add(contentArea, BorderLayout.CENTER);

        this.add(mainContainer);
        this.setVisible(true);
    }

    private class ShadowBorder extends AbstractBorder {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0, 0, 0, 20));
            g2.fillRoundRect(x + 2, y + 2, width - 4, height - 4, 10, 10);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 4, 4, 4);
        }
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBackground(primaryColor);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(15, 15, 25, 15));

        // Logo Panel with vertical centering
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for perfect centering
        logoPanel.setOpaque(false);
        logoPanel.setMaximumSize(new Dimension(200, 45));
        logoPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));
        
        JLabel logoLabel = new JLabel("ScholarSync");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logoLabel.setForeground(Color.WHITE);
        logoPanel.add(logoLabel);

        if (currentUser != null) {
            // User Info Panel
            JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            userPanel.setOpaque(false);
            userPanel.setMaximumSize(new Dimension(200, 60));
            userPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 30, 0));

            JLabel welcomeLabel = new JLabel("Welcome back,");
            welcomeLabel.setForeground(Color.WHITE);
            welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            
            JPanel welcomeWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            welcomeWrapper.setOpaque(false);
            welcomeWrapper.add(welcomeLabel);
            
            JLabel nameLabel = new JLabel(currentUser.getFirstName());
            nameLabel.setForeground(Color.WHITE);
            nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            
            JPanel nameWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            nameWrapper.setOpaque(false);
            nameWrapper.add(nameLabel);
            
            userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
            userPanel.add(welcomeWrapper);
            userPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            userPanel.add(nameWrapper);

            // Navigation Menu
            String[] menuItems = {"Dashboard", "My Applications", "Profile", "Settings", "Logout"};
            JPanel navPanel = new JPanel();
            navPanel.setOpaque(false);
            navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
            navPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            navPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            for (String item : menuItems) {
                JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
                buttonWrapper.setOpaque(false);
                buttonWrapper.setMaximumSize(new Dimension(200, 35));
                
                JButton navButton = createNavButton(item);
                buttonWrapper.add(navButton);
                navPanel.add(buttonWrapper);
                navPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            }

            sidebar.add(logoPanel);
            sidebar.add(userPanel);
            sidebar.add(navPanel);
        } else {
            // Auth Buttons Panel
            JPanel authPanel = new JPanel();
            authPanel.setLayout(new BoxLayout(authPanel, BoxLayout.Y_AXIS));
            authPanel.setOpaque(false);
            authPanel.setMaximumSize(new Dimension(200, 100));
            authPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
            authPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            loginButton = createAuthButton("Login");
            registerButton = createAuthButton("Create Account");

            JPanel loginWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            loginWrapper.setOpaque(false);
            loginWrapper.add(loginButton);

            JPanel registerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            registerWrapper.setOpaque(false);
            registerWrapper.add(registerButton);

            authPanel.add(loginWrapper);
            authPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            authPanel.add(registerWrapper);

            sidebar.add(logoPanel);
            sidebar.add(authPanel);
        }

        return sidebar;
    }

    private JButton createAuthButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(170, 35));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(primaryColor);
        button.setBorderPainted(text.equals("Create Account"));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (text.equals("Create Account")) {
            button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            button.setContentAreaFilled(false);
        }

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (text.equals("Create Account")) {
                    button.setBackground(new Color(255, 255, 255, 30));
                    button.setContentAreaFilled(true);
                } else {
                    button.setBackground(secondaryColor);
                }
            }
            public void mouseExited(MouseEvent e) {
                if (text.equals("Create Account")) {
                    button.setContentAreaFilled(false);
                } else {
                    button.setBackground(primaryColor);
                }
            }
        });

        button.addActionListener(this);
        return button;
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(160, 35));
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Create panel for content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setOpaque(false);
        
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(button.getFont());
        textLabel.setForeground(Color.WHITE);
        
        if (!text.equals("Logout")) {
            JLabel arrowLabel = new JLabel("›");
            arrowLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            arrowLabel.setForeground(Color.WHITE);
            contentPanel.add(arrowLabel, BorderLayout.EAST);
        }
        
        contentPanel.add(textLabel, BorderLayout.CENTER);
        button.add(contentPanel);

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
                // Add other navigation handlers as needed
            }
        });

        return button;
    }

    private JPanel createContentArea() {
        JPanel contentArea = new JPanel(new BorderLayout(0, 25));
        contentArea.setBackground(backgroundColor);
        contentArea.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Header
        JPanel header = new JPanel(new BorderLayout(20, 0));
        header.setBackground(backgroundColor);
        
        // Title and subtitle
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(backgroundColor);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));
        
        JLabel titleLabel = new JLabel("Available Scholarships");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(textColor);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Find and apply for scholarships that match your profile");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitleLabel.setForeground(subtitleColor);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 8)));
        titlePanel.add(subtitleLabel);

        // Filter button
        JButton filterButton = new JButton("Filter");
        filterButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        filterButton.setForeground(Color.WHITE);
        filterButton.setBackground(primaryColor);
        filterButton.setBorderPainted(false);
        filterButton.setFocusPainted(false);
        filterButton.setPreferredSize(new Dimension(100, 35));
        filterButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        filterButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                filterButton.setBackground(secondaryColor);
            }
            public void mouseExited(MouseEvent e) {
                filterButton.setBackground(primaryColor);
            }
        });

        header.add(titlePanel, BorderLayout.WEST);
        header.add(filterButton, BorderLayout.EAST);

        // Add separator
        JSeparator separator = new JSeparator();
        separator.setForeground(borderColor);
        separator.setBackground(borderColor);
        
        JPanel headerContainer = new JPanel(new BorderLayout());
        headerContainer.setBackground(backgroundColor);
        headerContainer.add(header, BorderLayout.CENTER);
        headerContainer.add(separator, BorderLayout.SOUTH);
        
        contentArea.add(headerContainer, BorderLayout.NORTH);

        // Scholarships Grid
        JPanel scholarshipsGrid = new JPanel(new GridLayout(0, 2, 25, 25));
        scholarshipsGrid.setBackground(backgroundColor);
        
        // Add scholarship cards
        addScholarshipCards(scholarshipsGrid);
        
        // Wrap in scroll pane
        JScrollPane scrollPane = new JScrollPane(scholarshipsGrid);
        scrollPane.setBorder(null);
        scrollPane.setBackground(backgroundColor);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        contentArea.add(scrollPane, BorderLayout.CENTER);

        return contentArea;
    }

    private void addScholarshipCards(JPanel container) {
        // Get scholarship data from database
        String sql = "SELECT title, start_date, end_date, applicant_count, max_slots FROM scholarships";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                container.add(createScholarshipCard(
                    rs.getString("title"),
                    rs.getDate("start_date").toString(),
                    rs.getDate("end_date").toString(),
                    rs.getInt("applicant_count"),
                    rs.getInt("max_slots")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Add sample data if database connection fails
            for (int i = 0; i < 4; i++) {
                container.add(createScholarshipCard(
                    "GSI's Scholarship Program 2025",
                    "June 13, 2025",
                    "July 30, 2025",
                    125,
                    5
                ));
            }
        }
    }

    private JPanel createScholarshipCard(String title, String startDate, String endDate, 
                                       int applicantCount, int maxSlots) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(cardBackground);
        card.setBorder(BorderFactory.createCompoundBorder(
            new ShadowBorder(),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(textColor);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        // Application Period
        JLabel periodLabel = new JLabel("Application Period: " + startDate + " - " + endDate);
        periodLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        periodLabel.setForeground(subtitleColor);
        periodLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(periodLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        // Status
        JLabel statusLabel = new JLabel("Status: Open");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        statusLabel.setForeground(greenStatus);
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(statusLabel);

        // Stats Panel
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        statsPanel.setBackground(cardBackground);
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Get the count of accepted applicants from the database
        int acceptedCount = 0;
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT COUNT(*) as accepted FROM applications WHERE scholarship_title = ? AND status = 'ACCEPTED'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                acceptedCount = rs.getInt("accepted");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JLabel applicantsLabel = new JLabel(applicantCount + " applicants");
        JLabel slotsLabel = new JLabel(acceptedCount + "/" + maxSlots + " Accepted Applicants");
        applicantsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        slotsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        applicantsLabel.setForeground(textColor);
        slotsLabel.setForeground(textColor);

        statsPanel.add(applicantsLabel);
        statsPanel.add(slotsLabel);

        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(statsPanel);

        // Tags Panel
        JPanel tagsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        tagsPanel.setBackground(cardBackground);
        tagsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] tags = {"Merit-based", "Full Tuition", "International"};
        for (String tag : tags) {
            JLabel tagLabel = new JLabel(tag);
            tagLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            tagLabel.setForeground(subtitleColor);
            tagLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
            ));
            tagsPanel.add(tagLabel);
        }

        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(tagsPanel);

        // Action Button
        JButton actionButton = new JButton("Apply Now");
        actionButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        actionButton.setForeground(Color.WHITE);
        actionButton.setBackground(primaryColor);
        actionButton.setBorderPainted(false);
        actionButton.setFocusPainted(false);
        actionButton.setMaximumSize(new Dimension(150, 35));
        actionButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        actionButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                actionButton.setBackground(secondaryColor);
            }
            public void mouseExited(MouseEvent e) {
                actionButton.setBackground(primaryColor);
            }
        });

        actionButton.addActionListener(e -> handleScholarshipApplication(title, applicantCount, maxSlots));

        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(actionButton);

        return card;
    }

    private void handleScholarshipApplication(String scholarshipTitle, int currentApplicants, int maxSlots) {
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
                JOptionPane.showMessageDialog(this,
                    "Successfully applied for " + scholarshipTitle + "!\nGood luck!",
                    "Application Submitted",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh the cards
                Container parent = this.getContentPane();
                for (Component comp : parent.getComponents()) {
                    if (comp instanceof JPanel) {
                        JPanel mainContainer = (JPanel) comp;
                        for (Component innerComp : mainContainer.getComponents()) {
                            if (innerComp instanceof JPanel) {
                                JPanel contentArea = (JPanel) innerComp;
                                if (contentArea.getLayout() instanceof BorderLayout) {
                                    for (Component content : contentArea.getComponents()) {
                                        if (content instanceof JScrollPane) {
                                            JScrollPane scrollPane = (JScrollPane) content;
                                            JViewport viewport = scrollPane.getViewport();
                                            if (viewport.getView() instanceof JPanel) {
                                                JPanel grid = (JPanel) viewport.getView();
                                                grid.removeAll();
                                                addScholarshipCards(grid);
                                                grid.revalidate();
                                                grid.repaint();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
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
	