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
	
	// Function to get items by status
    public List<Item> getItemsByStatus(String status) {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM Item WHERE itemStatus = ?";

        try (PreparedStatement preparedStatement = connect.preparedStatement(query)) {
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Item item = new Item();
                item.setItemId(resultSet.getString("itemId"));
                item.setItemName(resultSet.getString("itemName"));
                item.setItemSize(resultSet.getString("itemSize"));
                item.setItemPrice(resultSet.getString("itemPrice"));
                item.setItemCategory(resultSet.getString("itemCategory"));
                item.setItemStatus(resultSet.getString("itemStatus"));
                item.setItemWishlist(resultSet.getString("itemWishlist"));
                item.setItemOfferStatus(resultSet.getString("itemOfferStatus"));

                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
    
 // Function to update item status
    public boolean updateItemStatus(String itemId, String newStatus) {
        String query = "UPDATE Item SET itemStatus = ? WHERE itemId = ?";

        try (PreparedStatement preparedStatement = connect.preparedStatement(query)) {
            preparedStatement.setString(1, newStatus);
            preparedStatement.setString(2, itemId);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	
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
	
	public List<Item> getAvailableItems() {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM item WHERE itemStatus = 'Available'"; // Adjust your query based on your table structure

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
        List<Item> items = new ArrayList<>();

        String query = "SELECT i.itemId, i.itemName, i.itemPrice, i.itemStatus " +
                       "FROM Item i " +
                       "JOIN SellerItems si ON i.itemId = si.itemId " +
                       "JOIN Users u ON si.userId = u.userId " +
                       "WHERE u.userId = ?";

        try (
             PreparedStatement preparedStatement = connect.preparedStatement(query)) {

            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Item item = new Item();
                item.setItemId(resultSet.getString("itemId"));
                item.setItemName(resultSet.getString("itemName"));
                item.setItemPrice(resultSet.getString("itemPrice"));
                item.setItemStatus(resultSet.getString("itemStatus"));

                items.add(item);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching items for seller: " + e.getMessage());
        }

        return items;
    }
	
	public boolean updateItemPrice(String itemId, double newPrice) {
        String query = "UPDATE Item SET itemPrice = ? WHERE itemId = ?";

        try (
             PreparedStatement statement = connect.preparedStatement(query)) {

            statement.setDouble(1, newPrice);
            statement.setString(2, itemId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


}
