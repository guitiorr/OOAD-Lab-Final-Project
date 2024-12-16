package controller;

import java.util.List;

import connection.OfferDAO;
import models.Offer;

public class OfferController {
	OfferDAO offerDAO = new OfferDAO();
	
	public boolean addOffer(String itemId, String userId, double offerPrice) {
		return offerDAO.addOffer(itemId, userId, offerPrice);
	}
	
	 public boolean hasExistingOffer(String itemId, String userId) {
		 return offerDAO.hasExistingOffer(itemId, userId);
	 }
	 
	 public List<Offer> getOffersBySellerId(String sellerId){
		 return offerDAO.getOffersBySellerId(sellerId);
	 }
	
	 public boolean updateOfferStatus(int offerId, String newStatus) {
		 return offerDAO.updateOfferStatus(offerId, newStatus);
	 }
	
}
