package models;

public class Wishlist {
	private String wishlistID;
	private String itemID;
	private String userID;
	
	public Wishlist(String wishlistID, String itemID, String userID) {
		super();
		this.wishlistID = wishlistID;
		this.itemID = itemID;
		this.userID = userID;
	}

	public void ViewWishlist(String wishlistID, String userID) {
		
	}
	
	public void AddWishList(String itemID, String userID) {
		
	}
	
	public void RemoveWishList(String wishlistID) {
		
	}

	public String getWishlistID() {
		return wishlistID;
	}

	public String getItemID() {
		return itemID;
	}

	public String getUserID() {
		return userID;
	}

	public void setWishlistID(String wishlistID) {
		this.wishlistID = wishlistID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	
	
}
