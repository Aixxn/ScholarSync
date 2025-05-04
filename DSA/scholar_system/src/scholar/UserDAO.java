package scholar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    
    // Register a new user
    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (first_name, middle_name, last_name, email, password, " +
                     "school, track_strand, gpa, monthly_income, college_course, student_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Set values for prepared statement
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getMiddleName());
            pstmt.setString(3, user.getLastName());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getPassword()); // In a real app, use password hashing
            pstmt.setString(6, user.getSchool());
            pstmt.setString(7, user.getTrackStrand());
            pstmt.setDouble(8, user.getGpa());
            pstmt.setString(9, user.getMonthlyIncome());
            pstmt.setString(10, user.getCollegeCourse());
            pstmt.setString(11, user.getStudentId());
            
            // Execute the query
            int rowsAffected = pstmt.executeUpdate();
            return (rowsAffected > 0);
            
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Check if email already exists
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking email existence: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Verify login credentials
    public User login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            pstmt.setString(2, password); // In a real app, use password hashing
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setMiddleName(rs.getString("middle_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setSchool(rs.getString("school"));
                user.setTrackStrand(rs.getString("track_strand"));
                user.setGpa(rs.getDouble("gpa"));
                user.setMonthlyIncome(rs.getString("monthly_income"));
                user.setCollegeCourse(rs.getString("college_course"));
                user.setStudentId(rs.getString("student_id"));
                return user;
            }
            
        } catch (SQLException e) {
            System.err.println("Error during login: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null; // Return null if login fails
    }
    
    // Get user by email
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setMiddleName(rs.getString("middle_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setSchool(rs.getString("school"));
                user.setTrackStrand(rs.getString("track_strand"));
                user.setGpa(rs.getDouble("gpa"));
                user.setMonthlyIncome(rs.getString("monthly_income"));
                user.setCollegeCourse(rs.getString("college_course"));
                user.setStudentId(rs.getString("student_id"));
                return user;
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting user by email: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
}