package view;

import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BuyerView {
    private VBox layout;

    public BuyerView(String username, String role) {
        layout = new VBox(10);

        // Add labels for logged-in user and role
        Label loggedInAsLabel = new Label("Logged in as: " + username);
        Label roleLabel = new Label("Role: " + role);

        // Buttons for buyer actions
        Button purchaseItem = new Button("Purchase Item");
        Button makeOffer = new Button("Make Offer");
        Button viewWishlist = new Button("View Wishlist");
        Button removeWishlistItem = new Button("Remove Item from Wishlist");
        Button viewHistory = new Button("View Purchase History");

        layout.getChildren().addAll(loggedInAsLabel, roleLabel, purchaseItem, makeOffer, viewWishlist, removeWishlistItem, viewHistory);

        // Add event handlers for buttons
        purchaseItem.setOnAction(e -> System.out.println("Purchase Item clicked"));
        makeOffer.setOnAction(e -> System.out.println("Make Offer clicked"));
        viewWishlist.setOnAction(e -> System.out.println("View Wishlist clicked"));
        removeWishlistItem.setOnAction(e -> System.out.println("Remove Item from Wishlist clicked"));
        viewHistory.setOnAction(e -> System.out.println("View Purchase History clicked"));
    }

    public VBox getView() {
        return layout;
    }
}
