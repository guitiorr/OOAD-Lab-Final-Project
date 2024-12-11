package view;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SellerView {
    private VBox layout;

    public SellerView(String username, String role) {
        layout = new VBox(10);

        // User Information
        Label loggedInAsLabel = new Label("Logged in as: " + username);
        Label roleLabel = new Label("Role: " + role);

        // Navigation Buttons
        Button addItem = new Button("Add Item");
        Button viewOffers = new Button("View Offers");

        HBox navigation = new HBox(10, addItem, viewOffers);

        // TableView to display items
        TableView<String> itemsTable = new TableView<>();

        TableColumn<String, String> itemNameColumn = new TableColumn<>("Item Name");
        TableColumn<String, String> priceColumn = new TableColumn<>("Price");
        TableColumn<String, String> actionsColumn = new TableColumn<>("Actions");

        itemsTable.getColumns().addAll(itemNameColumn, priceColumn, actionsColumn);

        // Add components to layout
        layout.getChildren().addAll(
            loggedInAsLabel,
            roleLabel,
            navigation,
            itemsTable
        );

        // Add event handlers for buttons
        addItem.setOnAction(e -> System.out.println("Add Item clicked"));
        viewOffers.setOnAction(e -> System.out.println("View Offers clicked"));
    }

    public VBox getView() {
        return layout;
    }
}

