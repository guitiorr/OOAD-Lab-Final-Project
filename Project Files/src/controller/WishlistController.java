package controller;

import connection.WishlistDAO;

public class WishlistController {
	WishlistDAO wishlistDAO = new WishlistDAO();
	
	public boolean addToWishlist(String wishlistID, String itemID, String userID) {
        return wishlistDAO.insertWishlist(wishlistID, itemID, userID);
    }
	
	public boolean isItemInWishlist(String itemId, String userId) {
		return wishlistDAO.isItemInWishlist(itemId, userId);
	}
}
