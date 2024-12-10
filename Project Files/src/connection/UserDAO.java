package connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.User;
import java.sql.PreparedStatement;

public class UserDAO {
	private Connect connect = Connect.getInstance();
	
	public String getUserIdByUsername(String username) {
	    // Updated query to use LOWER for case-insensitivity
	    String query = "SELECT userId FROM Users WHERE LOWER(username) = LOWER(?)";
	    try (
	         PreparedStatement ps = connect.preparedStatement(query)) {
	        ps.setString(1, username); // Bind the parameter
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            return rs.getString("userId"); // Return the ID if found
	        } else {
	            System.out.println("No user found with username: " + username);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Print the stack trace for debugging
	    }
	    return null; // Return null if no user is found or an exception occurs
	}

	
    public void insertUser(User user) {
        String query = "INSERT INTO Users (id, username, phoneNumber, address, role, password) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connect.preparedStatement(query)) {
            ps.setString(1, user.getID());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPhoneNumber());
            ps.setString(4, user.getAddress());
            ps.setString(5, user.getRole());
            ps.setString(6, user.getPassword());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately in production code
        }
    }

 // Update user
    public void updateUser(User user) {
        String query = "UPDATE Users SET username = ?, phoneNumber = ?, address = ?, role = ?, password = ? WHERE ID = ?";

        try (PreparedStatement ps = connect.preparedStatement(query)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPhoneNumber());
            ps.setString(3, user.getAddress());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
 // Delete user
    public void deleteUser(String userID) {
        String query = "DELETE FROM Users WHERE ID = ?";

        try (PreparedStatement ps = connect.preparedStatement(query)) {
            ps.setString(1, userID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public ArrayList<User> getAllUsers(){
		ArrayList<User> userList = new ArrayList<>();
		String query = "SELECT * FROM Users";
		ResultSet rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				String ID = rs.getString("ID");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String phoneNumber = rs.getString("phoneNumber");
				String address = rs.getString("address");
				String role = rs.getString("role");

				userList.add(new User(ID, username, password, phoneNumber, address, role));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}
	
	public boolean validateCredentials(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (
             PreparedStatement stmt = connect.preparedStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
