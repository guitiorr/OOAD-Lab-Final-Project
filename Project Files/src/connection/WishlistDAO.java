package connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WishlistDAO {
	private Connect connect = Connect.getInstance();
	
	public boolean insertWishlist(String wishlistID, String itemID, String userID) {
        String query = "INSERT INTO Wishlist (wishlistID, itemId, userId) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connect.preparedStatement(query)) {
            stmt.setString(1, wishlistID);
            stmt.setString(2, itemID);
            stmt.setString(3, userID);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	public boolean isItemInWishlist(String itemId, String userId) {
	    // Query the database or check the data structure to see if the item is already wishlisted
	    String query = "SELECT COUNT(*) FROM wishlist WHERE itemId = ? AND userId = ?";
	    try (PreparedStatement stmt = connect.preparedStatement(query)) {
	        stmt.setString(1, itemId);
	        stmt.setString(2, userId);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1) > 0; // Returns true if at least one match is found
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}


}