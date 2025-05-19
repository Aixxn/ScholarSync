package test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;

public class MyFrame extends JFrame implements ActionListener {

    JButton loginButton, registerButton;
    JPanel mainContent;

    MyFrame() {
        this.setTitle("ScholarSync");
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

        // Sidebar
        JPanel sidebar = createSidebar();
        background.add(sidebar);

        // Navigator Panel
        JPanel navigator = createNavigator();
        background.add(navigator);

        // Main Content Area
        mainContent = new JPanel();
        mainContent.setBackground(new Color(0xF8FAFD));
        mainContent.setBounds(300, 150, 1620, 930);
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add sample scholarships
        addSampleScholarships();
        
        JScrollPane scrollPane = new JScrollPane(mainContent);
        scrollPane.setBounds(300, 150, 1620, 930);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        background.add(scrollPane);

        this.setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Color.WHITE);
        sidebar.setBounds(0, 0, 300, 1080);
        sidebar.setLayout(null);

        // Sidebar Title
        JLabel titlecard = new JLabel("<html><font color='#0000FF'>Scholar</font>Sync</html>");
        titlecard.setFont(new Font("Konkhmer Sleokchher", Font.PLAIN, 25));
        titlecard.setBounds(15, 30, 260, 40);
        sidebar.add(titlecard);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setBounds(15, 90, 100, 30); 
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);
        sidebar.add(loginButton);

        // Register Button
        registerButton = new JButton("Register");
        registerButton.setBounds(150, 90, 100, 30); 
        registerButton.setFocusable(false);
        registerButton.addActionListener(this);
        sidebar.add(registerButton);

        return sidebar;
    }

    private JPanel createNavigator() {
        JPanel navigator = new JPanel();
        navigator.setBackground(Color.WHITE);
        navigator.setBounds(300, 0, 1620, 150);
        navigator.setLayout(null);

        // Navigator Title
        JLabel scholarNav = new JLabel("Scholarships");
        scholarNav.setFont(new Font("Konkhmer Sleokchher", Font.PLAIN, 35)); 
        scholarNav.setBounds(30, 25, 260, 40); 
        navigator.add(scholarNav);

        // Search Container
        JPanel searchContainer = new JPanel();
        searchContainer.setBounds(500, 30, 600, 40);
        searchContainer.setBackground(Color.WHITE);
        searchContainer.setLayout(null);
        searchContainer.setBorder(new LineBorder(Color.BLACK, 1, true));

        // Search Field
        JTextField searchField = new JTextField("Search...");
        searchField.setBounds(15, 5, 530, 30);
        searchField.setForeground(Color.GRAY);
        searchField.setBorder(null);
        searchField.setBackground(Color.WHITE);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Placeholder logic
        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search...");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        // Search Icon
        JLabel searchIconLabel = new JLabel(new ImageIcon("images/search.png"));
        searchIconLabel.setBounds(565, 10, 20, 20);

        searchContainer.add(searchField);
        searchContainer.add(searchIconLabel);
        navigator.add(searchContainer);

        return navigator;
    }

    private void addSampleScholarships() {
        // Scholarship count label
        JLabel countLabel = new JLabel("Scholarships (11)");
        countLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        countLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        countLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainContent.add(countLabel);

        // Add sample scholarship cards
        mainContent.add(createScholarshipCard(
            "GSI's Scholarship Program 2025",
            new String[]{"June 13, 2025", "July 30, 2025"},
            "Scheduled",
            "0/5",
            "0/5",
            "Early Apply",
            new String[]{"Not interested", "Not accepted", "Not accepted"}
        ));

        mainContent.add(Box.createRigidArea(new Dimension(0, 20)));

        mainContent.add(createScholarshipCard(
            "CHED Merit Scholarship Program 2024",
            new String[]{"June 24th", "Jan 20, 2024", "July 30, 2024"},
            "Scheduled",
            "360",
            "0/5",
            "Apply",
            new String[]{"Not interested", "Not accepted"}
        ));

        // Add more scholarships as needed...
    }

    private JPanel createScholarshipCard(String title, String[] dates, String status, 
                                       String applicants, String accepted, String eligibility, 
                                       String[] tags) {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0xE0E0E0)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        // Dates
        for (String date : dates) {
            JLabel dateLabel = new JLabel("• " + date);
            dateLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            card.add(dateLabel);
        }
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        // Status
        JLabel statusLabel = new JLabel(status);
        statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(statusLabel);
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        // Bottom section
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottomPanel.setBackground(Color.WHITE);

        // Applicants/Accepted
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBackground(Color.WHITE);

        JLabel applicantsLabel = new JLabel("Applicants: " + applicants);
        applicantsLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        statsPanel.add(applicantsLabel);

        JLabel acceptedLabel = new JLabel("Accepted: " + accepted);
        acceptedLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        statsPanel.add(acceptedLabel);

        bottomPanel.add(statsPanel);
        bottomPanel.add(Box.createHorizontalGlue());

        // Eligibility button
        JButton eligibilityButton = new JButton(eligibility);
        eligibilityButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        eligibilityButton.setBackground(new Color(0x0000FF));
        eligibilityButton.setForeground(Color.WHITE);
        eligibilityButton.setFocusable(false);
        bottomPanel.add(eligibilityButton);
        bottomPanel.add(Box.createRigidArea(new Dimension(20, 0)));

        card.add(bottomPanel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        // Tags
        JPanel tagsPanel = new JPanel();
        tagsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        tagsPanel.setBackground(Color.WHITE);
        tagsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (String tag : tags) {
            JLabel tagLabel = new JLabel(tag);
            tagLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
            tagLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            tagLabel.setBorder(BorderFactory.createCompoundBorder(
                tagLabel.getBorder(),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
            ));
            tagsPanel.add(tagLabel);
        }

        card.add(tagsPanel);

        return card;
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