package controller;

import connection.TransactionDAO;

public class TransactionController {
	private TransactionDAO transactionDAO;
	
	public TransactionController() {
		this.transactionDAO = new TransactionDAO();
	}
	
	public void insertTransaction(String userId, String itemId) {
		transactionDAO.insertTransaction(userId, itemId);
	}

}
