package view;

import controller.ItemController;
import controller.UserController;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.Main;

public class AddItemView {
    private VBox layout;

    public AddItemView(String username) {
        layout = new VBox(10);

        // Input Fields
        Label itemNameLabel = new Label("Item Name:");
        TextField itemNameField = new TextField();

        Label itemCategoryLabel = new Label("Item Category:");
        ComboBox<String> itemCategoryComboBox = new ComboBox<>();
        itemCategoryComboBox.getItems().addAll("Electronics", "Clothing", "Books", "Home Appliances", "Other");

        Label itemSizeLabel = new Label("Item Size:");
        TextField itemSizeField = new TextField();

        Label itemPriceLabel = new Label("Item Price:");
        TextField itemPriceField = new TextField();

        Text validationMessage = new Text();
        validationMessage.setFill(Color.RED);

        // Submit Button
        Button submitButton = new Button("Add Item");

        layout.getChildren().addAll(
            itemNameLabel, itemNameField,
            itemCategoryLabel, itemCategoryComboBox,
            itemSizeLabel, itemSizeField,
            itemPriceLabel, itemPriceField,
            validationMessage,
            submitButton
        );

        // Add event handler for submit button
        submitButton.setOnAction(e -> {
            String itemName = itemNameField.getText().trim();
            String itemCategory = itemCategoryComboBox.getValue();
            String itemSize = itemSizeField.getText().trim();
            String itemPrice = itemPriceField.getText().trim();

            // Validation
            if (itemName.isEmpty()) {
                validationMessage.setText("Item name cannot be empty.");
                return;
            }

            if (itemCategory == null) {
                validationMessage.setText("Please select an item category.");
                return;
            }

            if (itemSize.isEmpty()) {
                validationMessage.setText("Item size cannot be empty.");
                return;
            }

            if (itemPrice.isEmpty()) {
                validationMessage.setText("Item price cannot be empty.");
                return;
            }

            double price;
            try {
                price = Double.parseDouble(itemPrice);
            } catch (NumberFormatException ex) {
                validationMessage.setText("Item price must be a valid number.");
                return;
            }

            // Call ItemController
            String priceString = String.valueOf(price);
            
            UserController uc = new UserController();
            
            String userId = uc.getUserIdByUsername(username);

            ItemController itemController = new ItemController();
            boolean isAdded = itemController.addItem(itemName, itemCategory, itemSize, priceString, userId);

            if (isAdded) {
                validationMessage.setFill(Color.GREEN);
                validationMessage.setText("Item successfully added!");
            } else {
                validationMessage.setFill(Color.RED);
                validationMessage.setText("Failed to add item. Please try again.");
            }

        });

    }

    public VBox getView() {
        return layout;
    }
}
