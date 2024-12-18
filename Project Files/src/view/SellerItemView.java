package view;

import controller.ItemController;
import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;
import models.Item;

public class SellerItemView {
    private VBox layout;
    private TableView<Item> tableView;
    private ObservableList<Item> sellerItems;

    public SellerItemView(String username, String role) {
        layout = new VBox(10);
        tableView = new TableView<>();

        // "Return" button to go back to SellerView
        Button returnButton = new Button("Return");
        returnButton.setOnAction(e -> {
            SellerView sellerView = new SellerView(username, role);
            Main.updateLayout(sellerView.getView());
        });

        // Define columns
        TableColumn<Item, String> itemIdColumn = new TableColumn<>("Item ID");
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<Item, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Item, String> itemPriceColumn = new TableColumn<>("Item Price");
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        TableColumn<Item, String> itemStatusColumn = new TableColumn<>("Item Status");
        itemStatusColumn.setCellValueFactory(new PropertyValueFactory<>("itemStatus"));

        // Add columns to table
        tableView.getColumns().addAll(itemIdColumn, itemNameColumn, itemPriceColumn, itemStatusColumn);

        UserController uc = new UserController();
        String userId = uc.getUserIdByUsername(username);

        // Fetch and display seller's items
        ItemController ic = new ItemController();
        sellerItems = FXCollections.observableArrayList(ic.getItemsBySeller(userId)); // Fetch items from the database
        tableView.setItems(sellerItems);

        // Create action buttons
        Button updateButton = new Button("Update Item");
        Button deleteButton = new Button("Delete Item");

        // Update item functionality
        updateButton.setOnAction(e -> {
            Item selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // Open a form to update the selected item
                UpdateItemView updateItemView = new UpdateItemView(selectedItem, username, role);
                Main.updateLayout(updateItemView.getView());
            } else {
                showAlert(Alert.AlertType.WARNING, "No Item Selected", "Please select an item to update.");
            }
        });

        // Delete item functionality
        deleteButton.setOnAction(e -> {
            Item selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // Confirm deletion
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, 
                    "Are you sure you want to delete this item?", 
                    ButtonType.YES, ButtonType.NO);
                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        ic.deleteItem(selectedItem.getItemId()); // Delete from database
                        sellerItems.remove(selectedItem); // Update TableView
                    }
                });
            } else {
                showAlert(Alert.AlertType.WARNING, "No Item Selected", "Please select an item to delete.");
            }
        });

        // Layout for action buttons
        HBox buttonLayout = new HBox(10, updateButton, deleteButton);

        // Add components to layout
        layout.getChildren().addAll(returnButton, tableView, buttonLayout);
    }

    public VBox getView() {
        return layout;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}