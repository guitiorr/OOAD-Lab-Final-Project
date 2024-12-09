package connection;

import models.Item;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ItemDAO {
	private Connect connect = Connect.getInstance();
	
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
