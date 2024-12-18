package view;

import controller.UserController;
import controller.WishlistController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.Main;
import models.Item;

public class WishlistView {

    private final TableView<Item> wishlistTable;
    private final WishlistController wishlistController;
    private final UserController userController;
    private final String username;
    private Text validationMessage; 

    public WishlistView(String username) {
        this.username = username;
        wishlistController = new WishlistController();
        userController = new UserController();
        validationMessage = new Text();
        validationMessage.setFill(Color.RED); 
        wishlistTable = new TableView<>();
        setupWishlistTable();
    }

    private void setupWishlistTable() {
        // Create columns for itemName and description
        TableColumn<Item, String> nameColumn = new TableColumn<>("Item Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
//
//        TableColumn<Item, String> descriptionColumn = new TableColumn<>("Description");
//        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Item, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button removeButton = new Button("Remove from Wishlist");

            {
                removeButton.setOnAction(e -> {
                    Item item = getTableRow().getItem();
                    handleRemoveFromWishlist(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    setGraphic(removeButton);
                }
            }
        });

        // Only add the columns you need
        wishlistTable.getColumns().addAll(nameColumn, actionColumn);
        actionColumn.setPrefWidth(140);  
        actionColumn.setMinWidth(140);
        // Load wishlist data
        loadWishlistData();
    }

    private void loadWishlistData() {
        // Reset pesan validasi
        validationMessage.setText("");

        // Get the logged-in user's username
        String username = Main.getCurrentUsername();
        if (username == null || username.isEmpty()) {
            validationMessage.setText("No user is logged in. Cannot load wishlist.");
            return;
        }

        // Get the user ID
        String userId = userController.getUserIdByUsername(username);
        if (userId == null || userId.isEmpty()) {
            validationMessage.setText("Failed to retrieve user ID. Cannot load wishlist.");
            return;
        }

        // Fetch wishlist items
        ObservableList<Item> wishlistItems = FXCollections.observableArrayList(wishlistController.getWishlistItemsByUserId(userId));

        if (wishlistItems.isEmpty()) {
            validationMessage.setText("Your wishlist is empty.");
        } else {
            validationMessage.setFill(Color.GREEN);
            validationMessage.setText("Wishlist loaded successfully.");
        }

        wishlistTable.setItems(wishlistItems);
    }

    private void handleRemoveFromWishlist(Item item) {
        // Reset pesan validasi
        validationMessage.setText("");

        if (item != null) {
            // Get the logged-in user's user ID
            String username = Main.getCurrentUsername();
            String userId = userController.getUserIdByUsername(username);

            if (userId == null || userId.isEmpty()) {
                validationMessage.setText("Failed to retrieve user ID. Cannot remove item.");
                return;
            }

            // Remove item from wishlist
            boolean success = wishlistController.removeFromWishlist(item.getItemId(), userId);
            if (success) {
                validationMessage.setFill(Color.GREEN);
                validationMessage.setText("Item removed successfully: " + item.getItemName());
                loadWishlistData(); // Refresh the table
            } else {
                validationMessage.setFill(Color.RED);
                validationMessage.setText("Failed to remove item: " + item.getItemName());
            }
        } else {
            validationMessage.setText("No item selected to remove.");
        }
    }

    public VBox getView() {
        VBox layout = new VBox(10);

        // Create "Return" button
        Button returnButton = new Button("Return");
        returnButton.setOnAction(e -> {
            // Return to the BuyerView
            String role = "Buyer";  // You can retrieve the role similarly if needed
            BuyerView buyerView = new BuyerView(username, role);
            Main.updateLayout(buyerView.getView()); // Update the layout with BuyerView
        });

        // Add return button and wishlist table to the layout
        layout.getChildren().addAll(returnButton, wishlistTable);
        return layout;
    }
}
