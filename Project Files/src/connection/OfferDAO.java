package connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
//    public List<Offer> getOffersBySellerId(String sellerId) {
//        List<Offer> offers = new ArrayList<>();
//        String query = "SELECT o.offerId, o.itemId, i.itemName, o.userId, o.offerPrice, o.status, o.created_at " +
//                "FROM Offers o " +
//                "JOIN SellerItems si ON o.itemId = si.itemId " +
//                "JOIN Item i ON o.itemId = i.itemId " + 
//                "WHERE si.userId = ?";
//
//        try (
//             PreparedStatement statement = connect.preparedStatement(query)) {
//
//            statement.setString(1, sellerId);
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                Offer offer = new Offer();
//                offer.setOfferId(resultSet.getInt("offerId"));
//                offer.setItemId(resultSet.getString("itemId"));
//                offer.setUserId(resultSet.getString("userId"));
//                offer.setOfferPrice(resultSet.getDouble("offerPrice"));
//                offer.setStatus(resultSet.getString("status"));
//                offer.setCreated_at(resultSet.getString("created_at"));
//                offers.add(offer);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return offers;
//    }
    public List<Map<String, Object>> getOffersBySellerId(String sellerId) {
        List<Map<String, Object>> offersWithItemName = new ArrayList<>();
        String query = "SELECT o.offerId, o.itemId, i.itemName, o.userId, o.offerPrice, o.status, o.created_at " +
                       "FROM Offers o " +
                       "JOIN SellerItems si ON o.itemId = si.itemId " +
                       "JOIN Item i ON o.itemId = i.itemId " + 
                       "WHERE si.userId = ?";

        try (PreparedStatement statement = connect.preparedStatement(query)) {
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

                // Map untuk menyimpan itemName bersama Offer
                Map<String, Object> offerWithItemName = new HashMap<>();
                offerWithItemName.put("offer", offer);
                offerWithItemName.put("itemName", resultSet.getString("itemName"));

                offersWithItemName.add(offerWithItemName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offersWithItemName;
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
    public boolean updateOfferPriceAndStatus(int offerId, double newOfferPrice, String newStatus) {
        String query = "UPDATE Offers SET offerPrice = ?, status = ? WHERE offerId = ?";

        try (PreparedStatement statement = connect.preparedStatement(query)) {

            statement.setDouble(1, newOfferPrice);
            statement.setString(2, newStatus);
            statement.setInt(3, offerId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Return true if at least one row was updated

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Return false if the operation fails
    }
    public String getOfferStatus(String itemId, String userId) {
        String query = "SELECT status FROM Offers WHERE itemId = ? AND userId = ?";
        String status = null;

        try (PreparedStatement stmt = connect.preparedStatement(query)) {
            stmt.setString(1, itemId);
            stmt.setString(2, userId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                status = rs.getString("status"); // Ambil nilai kolom 'status'
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status; // Return status atau null jika tidak ditemukan
    }
    public boolean updateOfferPriceAndStatus(String itemId, String userId, double newOfferPrice, String newStatus) {
        String query = "UPDATE Offers SET offerPrice = ?, status = ? WHERE itemId = ? AND userId = ?";

        try (PreparedStatement stmt = connect.preparedStatement(query)) {
            stmt.setDouble(1, newOfferPrice); // Set offerPrice baru
            stmt.setString(2, newStatus);    // Set status baru
            stmt.setString(3, itemId);       // Set itemId
            stmt.setString(4, userId);       // Set userId

            int rowsAffected = stmt.executeUpdate(); // Eksekusi query
            return rowsAffected > 0; // Return true jika ada baris yang ter-update

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Return false jika terjadi error atau tidak ada baris yang di-update
    }


}
