package controller;

import java.util.List;

import connection.WishlistDAO;
import models.Item;

public class WishlistController {
	WishlistDAO wishlistDAO = new WishlistDAO();
	
	public boolean addToWishlist(String wishlistID, String itemID, String userID) {
        return wishlistDAO.insertWishlist(wishlistID, itemID, userID);
    }
	
	public boolean isItemInWishlist(String itemId, String userId) {
		return wishlistDAO.isItemInWishlist(itemId, userId);
	}
	
	public boolean removeFromWishlist(String itemId, String userId) {
		return wishlistDAO.removeFromWishlist(itemId, userId);
	}
	
	public List<Item> getWishlistItemsByUserId(String userId){
		return wishlistDAO.getWishlistItemsByUserId(userId);
	}
}
