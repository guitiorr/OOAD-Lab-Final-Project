package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import main.Main;
import models.Item;
import controller.ItemController;
import controller.TransactionController;
import controller.UserController;

public class ItemView {
    private VBox layout;
    private TableView<Item> tableView;

    public ItemView(String username, String role) { // Accept username and role as arguments
        layout = new VBox(10);
        tableView = new TableView<>();

        // Create the "Return" button to go back to the previous view (BuyerView)
        Button returnButton = new Button("Return");
        returnButton.setOnAction(e -> {
            // Create and show the BuyerView again with the same username and role
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
                wishlistButton.setOnAction(e -> handleAddToWishlist(getTableRow().getItem()));

                buttonBox.getChildren().addAll(purchaseButton, offerButton, wishlistButton);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
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

//        ItemDAO itemDAO = new ItemDAO();
        ItemController itemController = new ItemController();
        ObservableList<Item> items = FXCollections.observableArrayList(itemController.getItems()); // Assuming getItems() returns a List<Item>

        tableView.setItems(items);

        // Add the return button first, then the table to the layout
        layout.getChildren().addAll(returnButton, tableView);
    }

    // Action handlers for buttons
    private void handlePurchase(Item item) {
        if (item != null) {
            String username = Main.getCurrentUsername();
            UserController uc = new UserController();
            String userID = uc.getUserIdByUsername(username);
            if (username == null || username.isEmpty()) {
                System.out.println("No user is logged in. Cannot proceed with the purchase.");
                return;
            }

            if ("Sold".equalsIgnoreCase(item.getItemStatus())) {
                System.out.println("Item is already sold. Purchase cannot proceed.");
                return;
            }

            // Assuming you have a controller instance
            TransactionController tc = new TransactionController();

//            System.out.println("ITEM ID -> " + item.getItemId());
            
            // Insert the transaction
            tc.insertTransaction(userID, item.getItemId());

            System.out.println("Successfully purchased item: " + item.getItemName());
            
            ItemController itc = new ItemController();
            
            itc.switchStatusToSold(item.getItemId());

            // Update the item's status (mark it as sold, etc.)
            item.setItemStatus("Sold");
            tableView.refresh(); // Refresh the table view to reflect the status change
        }
    }



    private void handleMakeOffer(Item item) {
        if (item != null) {
            System.out.println("Make offer on item: " + item.getItemName());
            // Add logic for making an offer on the item
        }
    }

    private void handleAddToWishlist(Item item) {
        if (item != null) {
            System.out.println("Add item to wishlist: " + item.getItemName());
            // Add logic for adding the item to the wishlist
        }
    }

    public VBox getView() {
        return layout;
    }
}
