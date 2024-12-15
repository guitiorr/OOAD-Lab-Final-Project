package connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OfferDAO {
	private Connect connect = Connect.getInstance();
	
	public boolean addOffer(String itemId, String userId, double offerPrice) {
        String query = "INSERT INTO Offers (itemId, userId, offerPrice) VALUES (?, ?, ?)";
        try (
             PreparedStatement stmt = connect.preparedStatement(query)) {

            stmt.setString(1, itemId);
            stmt.setString(2, userId);
            stmt.setDouble(3, offerPrice);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	// Check if the user has already made an offer for the item
    public boolean hasExistingOffer(String itemId, String userId) {
        String query = "SELECT COUNT(*) FROM Offers WHERE itemId = ? AND userId = ?";
        try (
             PreparedStatement stmt = connect.preparedStatement(query)) {

            stmt.setString(1, itemId);
            stmt.setString(2, userId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Return true if there is at least one offer
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
