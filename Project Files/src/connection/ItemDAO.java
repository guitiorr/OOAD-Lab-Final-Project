package connection;

import models.Item;
import java.util.List;
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

}
