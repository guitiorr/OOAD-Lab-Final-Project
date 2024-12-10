package connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class TransactionDAO {
	private Connect connect = Connect.getInstance();
		
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
