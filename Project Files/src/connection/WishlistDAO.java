package connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Item;

public class WishlistDAO {
	private Connect connect = Connect.getInstance();
	
	public List<Item> getWishlistItemsByUserId(String userId) {
	    List<Item> wishlistItems = new ArrayList<>();

	    // Modify query to exclude 'itemWishlist' and 'itemOfferStatus'
	    String query = "SELECT i.itemId, i.itemName, i.itemSize, i.itemPrice, i.itemCategory, " +
	                   "i.itemStatus " +  // Removed 'itemWishlist' and 'itemOfferStatus' from select
	                   "FROM wishlist w " +
	                   "JOIN item i ON w.itemId = i.itemId " +
	                   "WHERE w.userId = ?";

	    try (
	         PreparedStatement statement = connect.preparedStatement(query)) {

	        statement.setString(1, userId);

	        try (ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	                Item item = new Item(
	                    resultSet.getString("itemId"),
	                    resultSet.getString("itemName"),
	                    resultSet.getString("itemSize"),
	                    resultSet.getString("itemPrice"),
	                    resultSet.getString("itemCategory"),
	                    resultSet.getString("itemStatus")
	                    // Removed 'itemWishlist' and 'itemOfferStatus' from constructor
	                );
	                wishlistItems.add(item);
	            }
	        }

	    } catch (SQLException e) {
	        System.err.println("Error retrieving wishlist items for user ID: " + userId);
	        e.printStackTrace();
	    }

	    return wishlistItems;
	}

	
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
	
	public boolean removeFromWishlist(String itemId, String userId) {
	    String query = "DELETE FROM wishlist WHERE itemId = ? AND userId = ?";
	    try (PreparedStatement stmt = connect.preparedStatement(query)) {
	        stmt.setString(1, itemId);
	        stmt.setString(2, userId);
	        return stmt.executeUpdate() > 0; // Return true if a row was deleted
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}



}
