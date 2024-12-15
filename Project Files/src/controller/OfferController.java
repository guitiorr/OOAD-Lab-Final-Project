package controller;

import connection.OfferDAO;

public class OfferController {
	OfferDAO offerDAO = new OfferDAO();
	
	public boolean addOffer(String itemId, String userId, double offerPrice) {
		return offerDAO.addOffer(itemId, userId, offerPrice);
	}
	
	 public boolean hasExistingOffer(String itemId, String userId) {
		 return offerDAO.hasExistingOffer(itemId, userId);
	 }
	 
	 
	
	
}
