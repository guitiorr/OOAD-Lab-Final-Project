package controller;

import java.util.List;

import connection.TransactionDAO;
import models.Item;
import models.Transaction;

public class TransactionController {
	private TransactionDAO transactionDAO;
	
	public TransactionController() {
		this.transactionDAO = new TransactionDAO();
	}
	
	public void insertTransaction(String userId, String itemId) {
		transactionDAO.insertTransaction(userId, itemId);
	}
	
	public List<Transaction> getTransactionsWithItemsByUserId(String userId){
		return transactionDAO.getTransactionsWithItemsByUserId(userId);
	}
	
	public List<Item> getItemsByTransactionId(String transactionId){
		return transactionDAO.getItemsByTransactionId(transactionId);
	}

}
