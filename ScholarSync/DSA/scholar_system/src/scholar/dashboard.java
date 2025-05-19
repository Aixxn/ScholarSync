package scholar;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class dashboard extends JFrame {
    private UserDAO userDAO;
    private User currentUser;
    private Color primaryColor = new Color(25, 118, 210);
    private Color secondaryColor = new Color(66, 165, 245);
    private Color backgroundColor = new Color(248, 250, 253);

    public dashboard(User loggedInUser) {
        userDAO = new UserDAO();
        this.currentUser = loggedInUser;

        setTitle("ScholarSync Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Main Container
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(backgroundColor);

        // Sidebar
        JPanel sidebar = createSidebar();
        mainContainer.add(sidebar, BorderLayout.WEST);

        // Content Area
        JPanel contentArea = createContentArea();
        mainContainer.add(contentArea, BorderLayout.CENTER);

        add(mainContainer);
        setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(250, 768));
        sidebar.setBackground(primaryColor);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        // Logo Panel
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setMaximumSize(new Dimension(250, 100));
        JLabel logoLabel = new JLabel("ScholarSync");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logoLabel.setForeground(Color.WHITE);
        logoPanel.add(logoLabel);

        // User Info Panel
        JPanel userPanel = new JPanel();
        userPanel.setOpaque(false);
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setMaximumSize(new Dimension(250, 120));
        userPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel welcomeLabel = new JLabel("Welcome,");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JLabel nameLabel = new JLabel(currentUser.getFirstName());
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        userPanel.add(welcomeLabel);
        userPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        userPanel.add(nameLabel);

        // Navigation
        String[] navItems = {"Dashboard", "My Applications", "Profile", "Settings", "Return to Home"};
        JPanel navPanel = new JPanel();
        navPanel.setOpaque(false);
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        for (String item : navItems) {
            JButton navButton = createNavButton(item);
            navPanel.add(navButton);
            navPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        sidebar.add(logoPanel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(userPanel);
        sidebar.add(navPanel);

        return sidebar;
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(220, 40));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMargin(new Insets(0, 20, 0, 0));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setContentAreaFilled(true);
                button.setBackground(secondaryColor);
            }

            public void mouseExited(MouseEvent e) {
                button.setContentAreaFilled(false);
            }
        });

        button.addActionListener(e -> {
            if (text.equals("Return to Home")) {
                dispose();
                new MyFrame(currentUser);
            }
        });

        return button;
    }

    private JPanel createContentArea() {
        JPanel contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(backgroundColor);

        // Header
        JPanel header = new JPanel();
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(1116, 60));
        header.setLayout(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("My Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.add(titleLabel, BorderLayout.WEST);

        contentArea.add(header, BorderLayout.NORTH);

        // Main Content
        JPanel mainContent = new JPanel();
        mainContent.setBackground(backgroundColor);
        mainContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));

        // Profile Section
        JPanel profileSection = createProfileSection();
        mainContent.add(profileSection);
        mainContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // Stats Section
        JPanel statsSection = createStatsSection();
        mainContent.add(statsSection);

        JScrollPane scrollPane = new JScrollPane(mainContent);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentArea.add(scrollPane, BorderLayout.CENTER);

        return contentArea;
    }

    private JPanel createProfileSection() {
        JPanel section = new JPanel();
        section.setLayout(new BorderLayout());
        section.setMaximumSize(new Dimension(1076, 200));
        section.setBackground(Color.WHITE);
        section.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Profile Info
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 20, 10));
        infoPanel.setOpaque(false);

        String[][] info = {
            {"Full Name", currentUser.getFirstName() + " " + currentUser.getLastName()},
            {"Email", currentUser.getEmail()},
            {"School", currentUser.getSchool()},
            {"Course", currentUser.getCollegeCourse()},
            {"GPA", String.valueOf(currentUser.getGpa())},
            {"Student ID", currentUser.getStudentId()}
        };

        for (String[] item : info) {
            JLabel label = new JLabel(item[0]);
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            
            JLabel value = new JLabel(item[1]);
            value.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            
            infoPanel.add(label);
            infoPanel.add(value);
        }

        section.add(infoPanel, BorderLayout.CENTER);

        return section;
    }

    private JPanel createStatsSection() {
        JPanel section = new JPanel(new GridLayout(1, 3, 20, 0));
        section.setMaximumSize(new Dimension(1076, 100));
        section.setOpaque(false);

        String[][] stats = {
            {"Applied Scholarships", "5", "description"},
            {"Pending Applications", "3", "pending"},
            {"Approved Applications", "2", "check_circle"}
        };

        for (String[] stat : stats) {
            JPanel card = createStatCard(stat[0], stat[1], stat[2]);
            section.add(card);
        }

        return section;
    }

    private JPanel createStatCard(String title, String value, String icon) {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(Color.GRAY);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }
}
