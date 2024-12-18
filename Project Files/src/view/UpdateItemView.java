package view;

import controller.ItemController;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import main.Main;
import models.Item;

public class UpdateItemView {
    private VBox layout;

    public UpdateItemView(Item item, String username, String role) {
        layout = new VBox(10);
        layout.setPadding(new Insets(10));

        // Title Label
        Label titleLabel = new Label("Update Item Details");

        // Form fields
        TextField itemNameField = new TextField(item.getItemName());
        itemNameField.setPromptText("Item Name");

        TextField itemPriceField = new TextField(String.valueOf(item.getItemPrice()));
        itemPriceField.setPromptText("Item Price");

//        ComboBox<String> itemStatusComboBox = new ComboBox<>();
//        itemStatusComboBox.getItems().addAll("Available", "Unavailable");
//        itemStatusComboBox.setValue(item.getItemStatus());

        // Update button
        Button updateButton = new Button("Update Item");
        updateButton.setOnAction(e -> {
            String updatedName = itemNameField.getText();
            String updatedPrice = itemPriceField.getText();
//            String updatedStatus = itemStatusComboBox.getValue();

            if (validateInputs(updatedName, updatedPrice)) {
                try {
                    // Parse and validate price
                    double price = Double.parseDouble(updatedPrice);
                    String prices = Double.toString(price);

                    // Update item in the database
                    ItemController itemController = new ItemController();
                    item.setItemName(updatedName);
                    item.setItemPrice(prices); // Ensure `itemPrice` is handled as a double

                    itemController.updateItem(item);

                    // Success message
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Item updated successfully!");
                    successAlert.showAndWait();

                    // Return to SellerItemView
                    SellerItemView sellerItemView = new SellerItemView(username, role);
                    Main.updateLayout(sellerItemView.getView());
                } catch (NumberFormatException ex) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Price", "Please enter a valid numerical price.");
                }
            }
        });

        // Cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            SellerItemView sellerItemView = new SellerItemView(username, role);
            Main.updateLayout(sellerItemView.getView());
        });

        // Layout grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        grid.add(new Label("Item Name:"), 0, 0);
        grid.add(itemNameField, 1, 0);

        grid.add(new Label("Item Price:"), 0, 1);
        grid.add(itemPriceField, 1, 1);

//        grid.add(new Label("Item Status:"), 0, 2);
//        grid.add(itemStatusComboBox, 1, 2);

        // Add components to layout
        layout.getChildren().addAll(titleLabel, grid, updateButton, cancelButton);
    }

    public VBox getView() {
        return layout;
    }

    private boolean validateInputs(String name, String price) {
        if (name.isEmpty() || price.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "All fields must be filled out.");
            return false;
        }
        try {
            Double.parseDouble(price); // Validate numerical price
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Price", "Price must be a valid number.");
            return false;
        }
        return true;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}