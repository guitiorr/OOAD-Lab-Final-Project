package models;

public class Transaction {
	private String transactionID;
	private String userID;
	private String itemID;
	
	public Transaction(String transactionID, String userID, String itemID) {
		super();
		this.transactionID = transactionID;
		this.userID = userID;
		this.itemID = itemID;
	}
	
	public Transaction() {
		
	}
	
	public void PurchaseItems(String userID, String itemID) {
		
	}
	
	public void ViewHistory(String userID) {
		
	}
	
	public void CreateTransaction(String transactionID) {
		
	}

	public String getTransactionID() {
		return transactionID;
	}

	public String getUserID() {
		return userID;
	}

	public String getItemID() {
		return itemID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	
	
}
