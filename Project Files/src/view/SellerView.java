package view;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class SellerView {
    private VBox layout;

    public SellerView(String username, String role) {
        layout = new VBox(10);

        // User Information
        Label loggedInAsLabel = new Label("Logged in as: " + username);
        Label roleLabel = new Label("Role: " + role);

        // Buttons for Seller Actions
        Button addItem = new Button("Add Item");
        Button editItem = new Button("Edit Item");
        Button deleteItem = new Button("Delete Item");
        Button viewOffers = new Button("View Offers");
        Button acceptOffer = new Button("Accept Offer");
        Button declineOffer = new Button("Decline Offer");

        layout.getChildren().addAll(
            loggedInAsLabel,
            roleLabel,
            addItem,
            editItem,
            deleteItem,
            viewOffers,
            acceptOffer,
            declineOffer
        );

        // Add event handlers for buttons
        addItem.setOnAction(e -> System.out.println("Add Item clicked"));
        editItem.setOnAction(e -> System.out.println("Edit Item clicked"));
        deleteItem.setOnAction(e -> System.out.println("Delete Item clicked"));
        viewOffers.setOnAction(e -> System.out.println("View Offers clicked"));
        acceptOffer.setOnAction(e -> System.out.println("Accept Offer clicked"));
        declineOffer.setOnAction(e -> System.out.println("Decline Offer clicked"));
    }

    public VBox getView() {
        return layout;
    }
}
