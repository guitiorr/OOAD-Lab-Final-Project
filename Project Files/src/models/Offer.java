package models;

public class Offer {
	private int offerId;
	private String itemId;
	private String userId;
	private double offerPrice;
	private String status;
	private String created_at;
	
	
	
	
	public Offer(int offerId, String itemId, String userId, double offerPrice, String status, String created_at) {
		super();
		this.offerId = offerId;
		this.itemId = itemId;
		this.userId = userId;
		this.offerPrice = offerPrice;
		this.status = status;
		this.created_at = created_at;
	}
	
	public Offer() {
		
	}
	
	
	public int getOfferId() {
		return offerId;
	}
	public String getItemId() {
		return itemId;
	}
	public String getUserId() {
		return userId;
	}
	public double getOfferPrice() {
		return offerPrice;
	}
	public String getStatus() {
		return status;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setOfferPrice(double offerPrice) {
		this.offerPrice = offerPrice;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	
	
}
