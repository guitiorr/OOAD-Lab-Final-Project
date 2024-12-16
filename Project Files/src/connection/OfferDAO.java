package connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Offer;

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
    
    public List<Offer> getOffersBySellerId(String sellerId) {
        List<Offer> offers = new ArrayList<>();
        String query = "SELECT o.offerId, o.itemId, o.userId, o.offerPrice, o.status, o.created_at " +
                       "FROM Offers o " +
                       "JOIN SellerItems si ON o.itemId = si.itemId " +
                       "WHERE si.userId = ?";

        try (
             PreparedStatement statement = connect.preparedStatement(query)) {

            statement.setString(1, sellerId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Offer offer = new Offer();
                offer.setOfferId(resultSet.getInt("offerId"));
                offer.setItemId(resultSet.getString("itemId"));
                offer.setUserId(resultSet.getString("userId"));
                offer.setOfferPrice(resultSet.getDouble("offerPrice"));
                offer.setStatus(resultSet.getString("status"));
                offer.setCreated_at(resultSet.getString("created_at"));
                offers.add(offer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offers;
    }
    
    /**
     * Updates the status of an offer.
     * @param offerId The ID of the offer to update.
     * @param newStatus The new status for the offer (e.g., "Accepted", "Rejected").
     * @return True if the update was successful, false otherwise.
     */
    public boolean updateOfferStatus(int offerId, String newStatus) {
        String query = "UPDATE Offers SET status = ? WHERE offerId = ?";

        try (
             PreparedStatement statement = connect.preparedStatement(query)) {

            statement.setString(1, newStatus);
            statement.setInt(2, offerId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
