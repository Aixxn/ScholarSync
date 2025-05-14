package scholar;

import java.awt.*;
import java.awt.event.*;
import javax.swing.border.LineBorder;
import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

public class MyFrame extends JFrame implements ActionListener {

    JButton loginButton;
    JButton registerButton;
    User currentUser;

    public MyFrame(User user) {
        this.currentUser = user;

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

        // Drop Shadow Panel
        JPanel shadow = new JPanel();
        shadow.setBackground(new Color(0, 0, 0, 30));
        shadow.setBounds(300, 0, 1, 1080);
        background.add(shadow);

        // Navigator Panel
        JPanel navigator = new JPanel();
        navigator.setBackground(Color.WHITE);
        navigator.setBounds(300, 0, 1620, 150);
        navigator.setLayout(null);
        background.add(navigator);

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Color.WHITE);
        sidebar.setBounds(0, 0, 300, 1080);
        sidebar.setLayout(null);
        background.add(sidebar);

        // Sidebar Title
        JLabel titlecard = new JLabel("<html><font color='#0000FF'>Scholar</font>Sync</html>");
        titlecard.setFont(new Font("Konkhmer Sleokchher", Font.PLAIN, 25));
        titlecard.setBounds(15, 30, 260, 40);
        sidebar.add(titlecard);

        if (currentUser != null) {
            JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getFirstName() + "!");
            welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
            welcomeLabel.setBounds(15, 90, 250, 30);
            sidebar.add(welcomeLabel);

            JButton dashboardButton = new JButton("Go to Dashboard");
            dashboardButton.setBounds(15, 130, 250, 30);
            dashboardButton.setFocusable(false);
            dashboardButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    new dashboard(currentUser);
                }
            });
            sidebar.add(dashboardButton);

            JButton logoutButton = new JButton("Logout");
            logoutButton.setBounds(15, 400, 250, 30);
            logoutButton.setFocusable(false);
            logoutButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    new login();
                }
            });
            sidebar.add(logoutButton);

        } else {
            loginButton = new JButton("Login");
            loginButton.setBounds(15, 90, 100, 30);
            loginButton.setFocusable(false);
            loginButton.addActionListener(this);
            sidebar.add(loginButton);

            registerButton = new JButton("Register");
            registerButton.setBounds(150, 90, 100, 30);
            registerButton.setFocusable(false);
            registerButton.addActionListener(this);
            sidebar.add(registerButton);
        }

        JLabel scholarNav = new JLabel("Scholarships");
        scholarNav.setFont(new Font("Konkhmer Sleokchher", Font.PLAIN, 35));
        scholarNav.setBounds(30, 25, 260, 40);
        navigator.add(scholarNav);

        JPanel searchContainer = new JPanel();
        searchContainer.setBounds(500, 30, 600, 40);
        searchContainer.setBackground(Color.WHITE);
        searchContainer.setLayout(null);
        searchContainer.setBorder(new LineBorder(Color.BLACK, 1, true));

        JTextField searchField = new JTextField("Search...");
        searchField.setBounds(15, 5, 530, 30);
        searchField.setForeground(Color.GRAY);
        searchField.setBorder(null);
        searchField.setBackground(Color.WHITE);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        ImageIcon searchIcon = new ImageIcon("images/search.png");
        Image img = searchIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        searchIcon = new ImageIcon(img);

        JLabel searchIconLabel = new JLabel(searchIcon);
        searchIconLabel.setBounds(565, 10, 20, 20);

        searchContainer.add(searchField);
        searchContainer.add(searchIconLabel);
        navigator.add(searchContainer);

        Scholarship gsisScholarship = new Scholarship("GSIS Scholarship Program 2025", "April 13, 2025", "August 30, 2025");

        // Scholarship Card
        JPanel scholarshipCard = new JPanel();
        scholarshipCard.setLayout(null);
        scholarshipCard.setBounds(350, 180, 1200, 120);
        scholarshipCard.setBackground(Color.WHITE);
        scholarshipCard.setBorder(new LineBorder(new Color(220, 220, 220), 1, true));
        background.add(scholarshipCard);

        JLabel title = new JLabel("<html><i>" + gsisScholarship.getTitle() + "</i></html>");
        title.setFont(new Font("SansSerif", Font.ITALIC, 18));
        title.setBounds(20, 10, 400, 25);
        scholarshipCard.add(title);

        JLabel startDateLabel = new JLabel("Start: " + gsisScholarship.getStartDate());
        startDateLabel.setOpaque(true);
        startDateLabel.setBackground(new Color(46, 92, 138));
        startDateLabel.setForeground(new Color(242, 247, 252));
        startDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        startDateLabel.setBounds(20, 45, 120, 30);
        scholarshipCard.add(startDateLabel);

        JLabel dueDateLabel = new JLabel("Due: " + gsisScholarship.getEndDate());
        dueDateLabel.setOpaque(true);
        dueDateLabel.setBackground(new Color(91, 143, 189));
        dueDateLabel.setForeground(new Color(242, 247, 252));
        dueDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dueDateLabel.setBounds(150, 45, 120, 30);
        scholarshipCard.add(dueDateLabel);

        JLabel applicantsLabel = new JLabel("Applicants");
        applicantsLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        applicantsLabel.setBounds(450, 20, 100, 20);
        scholarshipCard.add(applicantsLabel);

        JLabel applicantsCount = new JLabel("0");
        applicantsCount.setFont(new Font("SansSerif", Font.PLAIN, 22));
        applicantsCount.setBounds(470, 45, 100, 30);
        scholarshipCard.add(applicantsCount);

        JLabel acceptedLabel = new JLabel("Accepted");
        acceptedLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        acceptedLabel.setBounds(580, 20, 100, 20);
        scholarshipCard.add(acceptedLabel);

        JLabel acceptedCount = new JLabel(gsisScholarship.getAcceptedCount() + "/5");
        acceptedCount.setFont(new Font("SansSerif", Font.PLAIN, 22));
        acceptedCount.setBounds(600, 45, 100, 30);
        scholarshipCard.add(acceptedCount);

        JLabel statusDot = new JLabel("\u25CF");
        statusDot.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusDot.setForeground(new Color(0, 0, 255));
        statusDot.setBounds(20, 90, 20, 20);
        scholarshipCard.add(statusDot);

        JLabel statusLabel = new JLabel("Ongoing");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setBounds(35, 90, 100, 20);
        scholarshipCard.add(statusLabel);

        JButton applyButton = new JButton("Apply Now");
        applyButton.setBounds(1050, 40, 120, 25);
        applyButton.setFocusable(false);
        applyButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        scholarshipCard.add(applyButton);

        ImageIcon notInterestedIcon = new ImageIcon("images/flag.png");
        Image flagImg = notInterestedIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        notInterestedIcon = new ImageIcon(flagImg);
        JLabel notInterestedLabel = new JLabel("Not Interested", notInterestedIcon, JLabel.RIGHT);
        notInterestedLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        notInterestedLabel.setForeground(Color.GRAY);
        notInterestedLabel.setBounds(1050, 90, 120, 20);
        scholarshipCard.add(notInterestedLabel);

        // Apply Logic
        applyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentUser == null) {
                    JOptionPane.showMessageDialog(null, "Please log in to apply.");
                    return;
                }

                if (currentUser.hasApplied(gsisScholarship.getTitle())) {
                    JOptionPane.showMessageDialog(null, "You have already applied for this scholarship.");
                } else if (!gsisScholarship.isFull()) {
                    currentUser.apply(gsisScholarship.getTitle());
                    gsisScholarship.incrementApplicants(); // <-- Add this line
                    applicantsCount.setText(String.valueOf(gsisScholarship.getApplicantCount())); // <-- Update UI
                    JOptionPane.showMessageDialog(null, "Application successful!");
                } else {
                    JOptionPane.showMessageDialog(null, "This scholarship has reached its limit.");
                }
            }
        });

        // Search Placeholder Logic
        searchField.addFocusListener(new FocusAdapter() {
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

        MouseAdapter clickOutsideListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!searchField.getBounds().contains(
                        SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), searchField.getParent()))) {
                    if (searchField.getText().isEmpty()) {
                        searchField.setText("Search...");
                        searchField.setForeground(Color.GRAY);
                    }
                    background.requestFocusInWindow();
                }
            }
        };

        background.addMouseListener(clickOutsideListener);
        navigator.addMouseListener(clickOutsideListener);
        sidebar.addMouseListener(clickOutsideListener);

        this.setVisible(true);
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
