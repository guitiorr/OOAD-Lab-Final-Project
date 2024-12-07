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
	
	public void PurchaseItems(String userID, String itemID) {
		
	}
	
	public void ViewHistory(String userID) {
		
	}
	
	public void CreateTransaction(String transactionID) {
		
	}
}
