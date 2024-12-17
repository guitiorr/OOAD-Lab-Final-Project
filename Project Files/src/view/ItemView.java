package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import main.Main;
import models.Item;
import controller.ItemController;
import controller.OfferController;
import controller.TransactionController;
import controller.UserController;
import controller.WishlistController;

public class ItemView {
    private VBox layout;
    private TableView<Item> tableView;
    private Text validationMessage;

    public ItemView(String username, String role) {
        layout = new VBox(10);
        tableView = new TableView<>();

        validationMessage = new Text();
        
        // Create the "Return" button to go back to the previous view (BuyerView)
        Button returnButton = new Button("Return");
        returnButton.setOnAction(e -> {
            BuyerView buyerView = new BuyerView(username, role);
            Main.updateLayout(buyerView.getView()); // Switch back to BuyerView
        });

        // Define columns
        TableColumn<Item, String> itemIdColumn = new TableColumn<>("Item ID");
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<Item, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Item, String> itemSizeColumn = new TableColumn<>("Item Size");
        itemSizeColumn.setCellValueFactory(new PropertyValueFactory<>("itemSize"));

        TableColumn<Item, String> itemPriceColumn = new TableColumn<>("Item Price");
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        TableColumn<Item, String> itemCategoryColumn = new TableColumn<>("Item Category");
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));

        TableColumn<Item, String> itemStatusColumn = new TableColumn<>("Item Status");
        itemStatusColumn.setCellValueFactory(new PropertyValueFactory<>("itemStatus"));

        // Action column for buttons
        TableColumn<Item, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setPrefWidth(300); // Set the preferred width for the column
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final HBox buttonBox = new HBox(5);
            private final Button purchaseButton = new Button("Purchase");
            private final Button offerButton = new Button("Make Offer");
            private final Button wishlistButton = new Button("Add to Wishlist");

            {
                purchaseButton.setOnAction(e -> handlePurchase(getTableRow().getItem()));
                offerButton.setOnAction(e -> handleMakeOffer(getTableRow().getItem()));

                wishlistButton.setOnAction(e -> {
                    Item item = getTableRow().getItem();
                    handleAddToWishlist(item, wishlistButton);
                });

                buttonBox.getChildren().addAll(purchaseButton, offerButton, wishlistButton);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    Item currentItem = getTableRow().getItem();

                    // Check wishlist status for current user and update button text
                    String username = Main.getCurrentUsername();
                    if (username != null && !username.isEmpty()) {
                        UserController userController = new UserController();
                        String userID = userController.getUserIdByUsername(username);

                        if (userID != null && !userID.isEmpty()) {
                            WishlistController wishlistController = new WishlistController();
                            boolean alreadyWishlisted = wishlistController.isItemInWishlist(currentItem.getItemId(), userID);
                            wishlistButton.setText(alreadyWishlisted ? "Remove from Wishlist" : "Add to Wishlist");
                        }
                    }

                    setGraphic(buttonBox);
                }
            }
        });

        // Add columns to the table
        tableView.getColumns().addAll(
            itemIdColumn,
            itemNameColumn,
            itemSizeColumn,
            itemPriceColumn,
            itemCategoryColumn,
            itemStatusColumn,
            actionColumn
        );

        ItemController itemController = new ItemController();
        ObservableList<Item> items = FXCollections.observableArrayList(itemController.getAvailableItems());
        tableView.setItems(items);

        // Add the return button first, then the table to the layout
        layout.getChildren().addAll(returnButton, tableView, validationMessage);
    }

    // Action handlers for buttons
    private void handlePurchase(Item item) {
        if (item != null) {
            String username = Main.getCurrentUsername();
            UserController uc = new UserController();
            String userID = uc.getUserIdByUsername(username);
            validationMessage.setFill(Color.RED);

            if (username == null || username.isEmpty()) {
                validationMessage.setText("No user is logged in. Cannot proceed with the purchase.");
                return;
            }

            if ("Sold".equalsIgnoreCase(item.getItemStatus())) {
                validationMessage.setText("Item is already sold. Purchase cannot proceed.");
                return;
            }

            TransactionController tc = new TransactionController();
            tc.insertTransaction(userID, item.getItemId());

            validationMessage.setFill(Color.GREEN);
            validationMessage.setText("Successfully purchased item: " + item.getItemName());

            ItemController itc = new ItemController();
            itc.switchStatusToSold(item.getItemId());

            item.setItemStatus("Sold");
            tableView.refresh(); // Refresh the table view to reflect the status change
        }
    }

    private void handleMakeOffer(Item item) {
        if (item != null) {
            // Prompt user for offer price
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Make Offer");
            dialog.setHeaderText("Make an Offer for: " + item.getItemName());
            dialog.setContentText("Enter your offer price:");

            dialog.showAndWait().ifPresent(input -> {
                try {
                    double offerPrice = Double.parseDouble(input);

                    String username = Main.getCurrentUsername();
                    if (username == null || username.isEmpty()) {
                        validationMessage.setFill(Color.RED);
                        validationMessage.setText("No user is logged in. Cannot make an offer.");
                        return;
                    }

                    UserController userController = new UserController();
                    String userId = userController.getUserIdByUsername(username);

                    if (userId == null || userId.isEmpty()) {
                        validationMessage.setFill(Color.RED);
                        validationMessage.setText("Failed to retrieve user ID.");
                        return;
                    }

                    OfferController offerController = new OfferController();
                    if (offerController.hasExistingOffer(item.getItemId(), userId)) {
//                        String currentStatus = offerController.getOfferStatus(item.getItemId(), userId);
//                        validationMessage.setFill(Color.RED);
//                        validationMessage.setText("You already have an offer for this item. Status: " + currentStatus);

//             
                        String currentStatus = offerController.getOfferStatus(item.getItemId(), userId);

                             if ("Rejected".equalsIgnoreCase(currentStatus)) {
                                 // If status is 'Rejected', update the offer price and set status to 'Pending'
                                 boolean updated = offerController.updateOfferPriceAndStatus(item.getItemId(), userId, offerPrice, "Pending");
                             	 validationMessage.setFill(Color.GREEN);
                                 validationMessage.setText("Your rejected offer has been updated! Status is now pending.");
                                 return;

                             }
                         	 validationMessage.setFill(Color.RED);
                         	 validationMessage.setText("You already have an offer for this item. Status: " + currentStatus);
                        return;
                    }

                    boolean success = offerController.addOffer(item.getItemId(), userId, offerPrice);
                    if (success) {
                        validationMessage.setFill(Color.GREEN);
                        validationMessage.setText("Offer made successfully: " + offerPrice);
                    } else {
                        validationMessage.setFill(Color.RED);
                        validationMessage.setText("Failed to make the offer.");
                    }

                } catch (NumberFormatException e) {
                    validationMessage.setFill(Color.RED);
                    validationMessage.setText("Invalid offer price entered.");
                }
            });
        }
    }

    private void handleAddToWishlist(Item item, Button wishlistButton) {
        if (item != null) {
            String username = Main.getCurrentUsername();
            if (username == null || username.isEmpty()) {
                validationMessage.setFill(Color.RED);
                validationMessage.setText("No user is logged in. Cannot add item to wishlist.");
                return;
            }

            UserController userController = new UserController();
            String userID = userController.getUserIdByUsername(username);

            if (userID == null || userID.isEmpty()) {
                validationMessage.setFill(Color.RED);
                validationMessage.setText("Failed to retrieve user ID.");
                return;
            }

            WishlistController wishlistController = new WishlistController();
            boolean alreadyWishlisted = wishlistController.isItemInWishlist(item.getItemId(), userID);

            if (alreadyWishlisted) {
                boolean success = wishlistController.removeFromWishlist(item.getItemId(), userID);
                if (success) {
                    wishlistButton.setText("Add to Wishlist");
                    validationMessage.setFill(Color.GREEN);
                    validationMessage.setText("Item removed from wishlist: " + item.getItemName());
                } else {
                    validationMessage.setFill(Color.RED);
                    validationMessage.setText("Failed to remove item from wishlist.");
                }
            } else {
                String wishlistID = generateUniqueWishlistID();
                boolean success = wishlistController.addToWishlist(wishlistID, item.getItemId(), userID);
                if (success) {
                    wishlistButton.setText("Remove from Wishlist");
                    validationMessage.setFill(Color.GREEN);
                    validationMessage.setText("Item added to wishlist: " + item.getItemName());
                } else {
                    validationMessage.setFill(Color.RED);
                    validationMessage.setText("Failed to add item to wishlist.");
                }
            }
        }
    }

    private String generateUniqueWishlistID() {
        return "WL-" + System.currentTimeMillis();
    }

    public VBox getView() {
        return layout;
    }
}
