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
	
	public void switchStatusToSold(String itemId) {
		itemDAO.switchStatusToSold(itemId);
	}

}
