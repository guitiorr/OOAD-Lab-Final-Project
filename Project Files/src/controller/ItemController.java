package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.ItemDAO;
import models.Item;

public class ItemController {
	
	private ItemDAO itemDAO;
	
	
	public ItemController() {
		this.itemDAO = new ItemDAO();
	}
	
	public List<Item> getItems() {
       return itemDAO.getItems();
    }
	
	public List<Item> getAvailableItems(){
		return itemDAO.getAvailableItems();
	}
	
	public void switchStatusToSold(String itemId) {
		itemDAO.switchStatusToSold(itemId);
	}

	public boolean addItem(String itemName, String itemCategory, String itemSize, String itemPrice, String userId) {
	    // Create a new Item object with the provided values
	    Item newItem = new Item();
	    newItem.setItemName(itemName);
	    newItem.setItemCategory(itemCategory);
	    newItem.setItemSize(itemSize);
	    newItem.setItemPrice(itemPrice);
	    newItem.setItemStatus("Pending"); // Default value
	    newItem.setItemWishlist("0"); // Default value
	    newItem.setItemOfferStatus(null); // Optional or default can be handled by the database

	    // Add item to the database, passing userId as part of the method call
	    return itemDAO.addItem(newItem, userId);
	}
	
	public List<Item> getItemsBySeller(String userId){
		return itemDAO.getItemsBySeller(userId);
	}
	
	public boolean updateItemStatus(String itemId, String newStatus) {
		return itemDAO.updateItemStatus(itemId, newStatus);
	}
	
	public List<Item> getItemsByStatus(String status){
		return itemDAO.getItemsByStatus(status);
	}
	
	public boolean updateItemPrice(String itemId, double newPrice) {
		return itemDAO.updateItemPrice(itemId, newPrice);
	}
	
	public void updateItem(Item item) {
		itemDAO.updateItem(item);
	}

	public void deleteItem(String itemId) {
		itemDAO.deleteItem(itemId);
	}
	
}
