package connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import models.Item;
import models.Transaction;

public class TransactionDAO {
	private Connect connect = Connect.getInstance();
	
	public List<Item> getItemsByTransactionId(String transactionId) {
        List<Item> items = new ArrayList<>();

        String query = "SELECT i.itemId, i.itemName, i.itemSize, i.itemPrice, i.itemCategory, i.itemStatus " +
                       "FROM Item i " +
                       "INNER JOIN Transaction t ON i.itemId = t.itemId " +
                       "WHERE t.transactionId = ?";

        try (PreparedStatement preparedStatement = connect.preparedStatement(query)) {
            preparedStatement.setString(1, transactionId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Item item = new Item();
                item.setItemId(resultSet.getString("itemId"));
                item.setItemName(resultSet.getString("itemName"));
                item.setItemSize(resultSet.getString("itemSize"));
                item.setItemPrice(resultSet.getString("itemPrice"));
                item.setItemCategory(resultSet.getString("itemCategory"));
                item.setItemStatus(resultSet.getString("itemStatus"));

                items.add(item);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching items for transaction: " + e.getMessage());
        }

        return items;
    }
	
	public List<Transaction> getTransactionsWithItemsByUserId(String userId) {
	    List<Transaction> transactions = new ArrayList<>();
	    Map<String, List<Item>> transactionItemsMap = new HashMap<>();

	    String query = 
	            "SELECT t.transactionId, t.userId, i.itemId, i.itemName, i.itemSize, i.itemPrice, i.itemCategory, i.itemStatus " +
	            "FROM Transaction t " +
	            "INNER JOIN Item i ON t.itemId = i.itemId " +
	            "WHERE t.userId = ?";

	    try (PreparedStatement preparedStatement = connect.preparedStatement(query)) {
	        preparedStatement.setString(1, userId);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        // Process each result set row
	        while (resultSet.next()) {
	            String transactionId = resultSet.getString("transactionId");

	            // Get or create the list of items for the current transactionId
	            List<Item> items = transactionItemsMap.getOrDefault(transactionId, new ArrayList<>());
	            
	            // Create Item and populate its properties
	            Item item = new Item();
	            item.setItemId(resultSet.getString("itemId"));
	            item.setItemName(resultSet.getString("itemName"));
	            item.setItemSize(resultSet.getString("itemSize"));
	            item.setItemPrice(resultSet.getString("itemPrice"));
	            item.setItemCategory(resultSet.getString("itemCategory"));
	            item.setItemStatus(resultSet.getString("itemStatus"));
	            
	            // Add the item to the list associated with the transactionId
	            items.add(item);

	            // Put the updated list of items back into the map
	            transactionItemsMap.put(transactionId, items);
	        }

	        // Now, create Transaction objects and associate the list of items
	        for (Map.Entry<String, List<Item>> entry : transactionItemsMap.entrySet()) {
	            String transactionId = entry.getKey();
	            List<Item> items = entry.getValue();
	            
	            // Create the transaction and set its properties
	            Transaction transaction = new Transaction();
	            transaction.setTransactionID(transactionId);
	            transaction.setUserID(userId);
	            
	            // Optionally, add the list of items to the transaction in some way (depending on your model)
	            // For now, we assume `Transaction` has a setter for items or you can return the items separately.

	            transactions.add(transaction);  // Add the transaction to the final list
	        }

	    } catch (SQLException e) {
	        System.err.println("Error fetching transactions with items: " + e.getMessage());
	    }

	    return transactions;
	}



		
	public void insertTransaction(String userId, String itemId) {
        String query = "INSERT INTO Transaction (transactionId, userId, itemId) VALUES (?, ?, ?)";
        
        // Generate a transaction ID starting with "TR"
        String transactionId = "TR" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        try (
             PreparedStatement stmt = connect.preparedStatement(query)) {

            // Set the parameters for the query
            stmt.setString(1, transactionId);
            stmt.setString(2, userId);
            stmt.setString(3, itemId);

            // Execute the query
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Transaction inserted successfully with ID: " + transactionId);
            } else {
                System.out.println("Failed to insert transaction.");
            }

        } catch (SQLException e) {
            System.err.println("Error inserting transaction: " + e.getMessage());
        }
    }
	
}
