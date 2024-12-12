package connection;

import models.Item;
import java.util.List;
import java.util.UUID;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ItemDAO {
	private Connect connect = Connect.getInstance();
	
	public void switchStatusToSold(String itemId) {
        String query = "UPDATE item SET itemStatus = 'Sold' WHERE itemId = ?";

        try (
             PreparedStatement stmt = connect.preparedStatement(query)) {
            stmt.setString(1, itemId);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Item status updated to 'Sold' for itemId: " + itemId);
            } else {
                System.out.println("No item found with itemId: " + itemId);
            }
        } catch (SQLException e) {
            System.out.println("Error updating item status: " + e.getMessage());
        }
    }
	
	public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM item"; // Adjust your query based on your table structure

        try (
             ResultSet rs = connect.execQuery(query)) {
            
            while (rs.next()) {
                // Assuming your Item class has a constructor that matches the columns
                Item item = new Item(
                    rs.getString("itemId"),
                    rs.getString("itemName"),
                    rs.getString("itemSize"),
                    rs.getString("itemPrice"),
                    rs.getString("itemCategory"),
                    rs.getString("itemStatus"),
                    rs.getString("itemWishlist"),
                    rs.getString("itemOfferStatus")
                );
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
	
	public boolean addItem(Item item, String userId) {
	    // Generate unique itemId starting with "IT"
	    String itemId = "IT" + UUID.randomUUID().toString().substring(0, 8);

	    String itemQuery = "INSERT INTO Item (itemId, itemName, itemSize, itemPrice, itemCategory, itemStatus, itemWishlist, itemOfferStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	    String sellerItemQuery = "INSERT INTO SellerItems (userId, itemId) VALUES (?, ?)";


	    try {
	        connect.setAutoCommit(false); // Disable auto-commit for transaction

	        // First insert into the Item table
	        try (PreparedStatement preparedStatement = connect.preparedStatement(itemQuery)) {
	            preparedStatement.setString(1, itemId); // Set the generated itemId
	            preparedStatement.setString(2, item.getItemName());
	            preparedStatement.setString(3, item.getItemSize());
	            preparedStatement.setString(4, item.getItemPrice());
	            preparedStatement.setString(5, item.getItemCategory());

	            // Default values
	            preparedStatement.setString(6, "Pending"); // Default status
	            preparedStatement.setBoolean(7, false); // Default wishlist value
	            preparedStatement.setString(8, "No Offer"); // Default offer status

	            int rowsAffected = preparedStatement.executeUpdate();
	            if (rowsAffected <= 0) {
	            	connect.rollback(); // Rollback if item insert fails
	                return false;
	            }
	        }

	        // Insert into the SellerItems table
	        try (PreparedStatement preparedStatement = connect.preparedStatement(sellerItemQuery)) {
	            preparedStatement.setString(1, userId); // Set the seller's userId
	            preparedStatement.setString(2, itemId); // Set the newly generated itemId

	            int rowsAffected = preparedStatement.executeUpdate();
	            if (rowsAffected <= 0) {
	            	connect.rollback(); // Rollback if SellerItems insert fails
	                return false;
	            }
	        }

	        connect.commit(); // Commit the transaction if both inserts succeed
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            if (connect != null) {
	            	connect.rollback(); // Rollback if an exception occurs
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return false;
	    } finally {
	        try {
	            if (connect != null) {
	            	connect.setAutoCommit(true); // Restore auto-commit mode
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	// Method to get items owned by a specific seller
    public List<Item> getItemsBySeller(String userId) {
        List<Item> itemList = new ArrayList<>();
        String query = "SELECT i.itemId, i.itemName, i.itemSize, i.itemPrice, i.itemCategory, i.itemStatus, i.itemWishlist, i.itemOfferStatus " +
                       "FROM Item i " +
                       "JOIN SellerItems si ON i.itemId = si.itemId " +
                       "WHERE si.userId = ?";

        try (PreparedStatement ps = connect.preparedStatement(query)) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Item item = new Item();
                item.setItemId(rs.getString("itemId"));
                item.setItemName(rs.getString("itemName"));
                item.setItemSize(rs.getString("itemSize"));
                item.setItemPrice(rs.getString("itemPrice"));
                item.setItemCategory(rs.getString("itemCategory"));
                item.setItemStatus(rs.getString("itemStatus"));
                item.setItemWishlist(rs.getString("itemWishlist"));
                item.setItemOfferStatus(rs.getString("itemOfferStatus"));
                itemList.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemList;
    }

}
