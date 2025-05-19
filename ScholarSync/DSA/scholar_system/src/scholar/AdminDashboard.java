package scholar;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminDashboard extends JFrame {
    private JTable applicantsTable;
    private DefaultTableModel tableModel;
    private UserDAO userDAO;
    private Color primaryColor = new Color(52, 152, 219);
    private Color secondaryColor = new Color(41, 128, 185);
    private Color backgroundColor = new Color(245, 247, 250);
    private JPanel applicationsPanel;
    private Timer refreshTimer;

    public AdminDashboard() {
        userDAO = new UserDAO();
        
        setTitle("ScholarSync Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        
        // Main container
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(backgroundColor);
        
        // Create and add components
        mainContainer.add(createHeader(), BorderLayout.NORTH);
        mainContainer.add(createMainContent(), BorderLayout.CENTER);
        
        add(mainContainer);
        
        // Start refresh timer (refresh every 30 seconds)
        refreshTimer = new Timer(30000, e -> refreshApplications());
        refreshTimer.start();
        
        setVisible(true);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(primaryColor);
        header.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        JButton logoutButton = new JButton("Logout");
        styleButton(logoutButton);
        logoutButton.addActionListener(e -> {
            dispose();
            new login();
        });

        header.add(titleLabel, BorderLayout.WEST);
        header.add(logoutButton, BorderLayout.EAST);

        return header;
    }
    
    private JPanel createMainContent() {
        JPanel mainContent = new JPanel(new BorderLayout(20, 20));
        mainContent.setBackground(backgroundColor);
        mainContent.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Statistics Panel
        JPanel statsPanel = createStatsPanel();
        mainContent.add(statsPanel, BorderLayout.NORTH);

        // Applications Panel
        applicationsPanel = new JPanel();
        applicationsPanel.setLayout(new BoxLayout(applicationsPanel, BoxLayout.Y_AXIS));
        applicationsPanel.setBackground(backgroundColor);

        // Wrap in scroll pane
        JScrollPane scrollPane = new JScrollPane(applicationsPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        mainContent.add(scrollPane, BorderLayout.CENTER);

        // Initial load of applications
        refreshApplications();

        return mainContent;
    }
    
    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        statsPanel.setBackground(backgroundColor);

        // Total Applications
        addStatCard(statsPanel, "Total Applications", String.valueOf(getTotalApplications()));
        
        // Pending Applications
        addStatCard(statsPanel, "Pending Applications", String.valueOf(getPendingApplications()));
        
        // Accepted Applications
        addStatCard(statsPanel, "Accepted Applications", String.valueOf(getAcceptedApplications()));
        
        // Rejected Applications
        addStatCard(statsPanel, "Rejected Applications", String.valueOf(getRejectedApplications()));

        return statsPanel;
    }
    
    private void addStatCard(JPanel container, String title, String value) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(100, 100, 100));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(new Color(33, 33, 33));
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(valueLabel);

        container.add(card);
    }
    
    private void refreshApplications() {
        applicationsPanel.removeAll();
        
        // Add header
        JPanel headerPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        headerPanel.setBackground(backgroundColor);
        headerPanel.setMaximumSize(new Dimension(1300, 40));
        
        String[] headers = {"Scholarship", "Applicant", "GPA", "Status", "Actions"};
        for (String header : headers) {
            JLabel headerLabel = new JLabel(header);
            headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            headerLabel.setForeground(Color.WHITE);
            headerPanel.add(headerLabel);
        }
        
        applicationsPanel.add(headerPanel);
        applicationsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Get all scholarships and their applications
        List<String> scholarships = getScholarshipTitles();
        for (String scholarship : scholarships) {
            List<Map<String, Object>> applications = userDAO.getApplicationsForScholarship(scholarship);
            
            for (Map<String, Object> application : applications) {
                JPanel applicationRow = createApplicationRow(scholarship, application);
                applicationsPanel.add(applicationRow);
                applicationsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        applicationsPanel.revalidate();
        applicationsPanel.repaint();
    }
    
    private JPanel createApplicationRow(String scholarship, Map<String, Object> application) {
        JPanel row = new JPanel(new GridLayout(1, 5, 10, 0));
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        row.setMaximumSize(new Dimension(1300, 60));

        // Scholarship Title
        JLabel scholarshipLabel = new JLabel(scholarship);
        scholarshipLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Applicant Name
        String applicantName = application.get("firstName") + " " + application.get("lastName");
        JLabel applicantLabel = new JLabel(applicantName);
        applicantLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // GPA
        JLabel gpaLabel = new JLabel(String.format("%.2f", application.get("gpa")));
        gpaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Status
        JLabel statusLabel = new JLabel((String) application.get("status"));
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        styleStatusLabel(statusLabel);

        // Action Buttons
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actionsPanel.setBackground(Color.WHITE);

        if ("PENDING".equals(application.get("status"))) {
            JButton acceptButton = new JButton("Accept");
            JButton rejectButton = new JButton("Reject");
            
            styleActionButton(acceptButton, true);
            styleActionButton(rejectButton, false);

            acceptButton.addActionListener(e -> updateApplicationStatus(scholarship, 
                (String)application.get("email"), "ACCEPTED", statusLabel));
            rejectButton.addActionListener(e -> updateApplicationStatus(scholarship, 
                (String)application.get("email"), "REJECTED", statusLabel));

            actionsPanel.add(acceptButton);
            actionsPanel.add(rejectButton);
        }

        row.add(scholarshipLabel);
        row.add(applicantLabel);
        row.add(gpaLabel);
        row.add(statusLabel);
        row.add(actionsPanel);

        return row;
    }
    
    private void styleStatusLabel(JLabel label) {
        label.setOpaque(true);
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        switch (label.getText()) {
            case "PENDING":
                label.setBackground(new Color(255, 244, 230));
                label.setForeground(new Color(255, 159, 67));
                break;
            case "ACCEPTED":
                label.setBackground(new Color(231, 255, 236));
                label.setForeground(new Color(46, 204, 113));
                break;
            case "REJECTED":
                label.setBackground(new Color(255, 230, 230));
                label.setForeground(new Color(231, 76, 60));
                break;
        }
    }
    
    private void styleActionButton(JButton button, boolean isAccept) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(80, 30));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (isAccept) {
            button.setBackground(new Color(46, 204, 113));
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(new Color(231, 76, 60));
            button.setForeground(Color.WHITE);
        }

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(isAccept ? 
                    new Color(39, 174, 96) : 
                    new Color(192, 57, 43));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(isAccept ? 
                    new Color(46, 204, 113) : 
                    new Color(231, 76, 60));
            }
        });
    }
    
    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(secondaryColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 35));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(36, 113, 163));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(secondaryColor);
            }
        });
    }
    
    private void updateApplicationStatus(String scholarship, String email, String status, JLabel statusLabel) {
        if (userDAO.updateApplicationStatus(scholarship, email, status)) {
            statusLabel.setText(status);
            styleStatusLabel(statusLabel);
            refreshApplications(); // Refresh to update statistics
        }
    }
    
    private int getTotalApplications() {
        return userDAO.getTotalApplicationCount();
    }
    
    private int getPendingApplications() {
        return userDAO.getApplicationCountByStatus("PENDING");
    }
    
    private int getAcceptedApplications() {
        return userDAO.getApplicationCountByStatus("ACCEPTED");
    }
    
    private int getRejectedApplications() {
        return userDAO.getApplicationCountByStatus("REJECTED");
    }
    
    private List<String> getScholarshipTitles() {
        return userDAO.getAllScholarshipTitles();
    }
    
    @Override
    public void dispose() {
        refreshTimer.stop();
        super.dispose();
    }
} 