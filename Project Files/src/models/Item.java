package models;

public class Item {

    private String itemId;
    private String itemName;
    private String itemSize;
    private String itemPrice;
    private String itemCategory;
    private String itemStatus;
    private String itemWishlist;
    private String itemOfferStatus;

    public Item(String itemId, String itemName, String itemSize, String itemPrice, String itemCategory,
                String itemStatus, String itemWishlist, String itemOfferStatus) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemSize = itemSize;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
        this.itemStatus = itemStatus;
        this.itemWishlist = itemWishlist;
        this.itemOfferStatus = itemOfferStatus;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemSize() {
        return itemSize;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public String getItemWishlist() {
        return itemWishlist;
    }

    public String getItemOfferStatus() {
        return itemOfferStatus;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public void setItemWishlist(String itemWishlist) {
        this.itemWishlist = itemWishlist;
    }

    public void setItemOfferStatus(String itemOfferStatus) {
        this.itemOfferStatus = itemOfferStatus;
    }

    // Methods (these can be expanded with actual implementation)
    public void uploadItem(String itemName, String itemCategory, String itemSize, String itemPrice) {
        // Implementation here
    }

    public void editItem(String itemId, String itemName, String itemCategory, String itemSize, String itemPrice) {
        // Implementation here
    }

    public void deleteItem(String itemId) {
        // Implementation here
    }

    public void browseItem(String itemName) {
        // Implementation here
    }

    public void viewItem() {
        // Implementation here
    }

    public void checkItemValidation(String itemName, String itemCategory, String itemSize, String itemPrice) {
        // Implementation here
    }

    public void viewRequestedItem(String itemId, String itemStatus) {
        // Implementation here
    }

    public void offerPrice(String itemId, String itemPrice) {
        // Implementation here
    }

    public void acceptOffer(String itemId) {
        // Implementation here
    }

    public void declineOffer(String itemId) {
        // Implementation here
    }

    public void approveItem(String itemId) {
        // Implementation here
    }

    public void declineItem(String itemId) {
        // Implementation here
    }

    public void viewAcceptedItem(String itemId) {
        // Implementation here
    }

    public void viewOfferItem(String userId) {
        // Implementation here
    }
}
