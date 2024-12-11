package view;

import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.Main;

public class BuyerView {
    private VBox layout;

    public BuyerView(String username, String role) {
        layout = new VBox(10);

        // Add labels for logged-in user and role
        Label loggedInAsLabel = new Label("Logged in as: " + username);
        Label roleLabel = new Label("Role: " + role);

        // Buttons for buyer actions
        Button viewItemButton = new Button("View Item");
//        Button makeOffer = new Button("Make Offer");
        Button viewWishlist = new Button("View Wishlist");
        Button removeWishlistItem = new Button("Remove Item from Wishlist");
        Button viewHistory = new Button("View Purchase History");

//        layout.getChildren().addAll(loggedInAsLabel, roleLabel, viewItemButton, makeOffer, viewWishlist, removeWishlistItem, viewHistory);
        layout.getChildren().addAll(loggedInAsLabel, roleLabel, viewItemButton, viewWishlist, removeWishlistItem, viewHistory);

        // Add event handlers for buttons
        viewItemButton.setOnAction(e -> {
            // Create and show the ItemView when the "View Item" button is clicked
        	ItemView itemView = new ItemView(username, role); // Pass username and role
            Main.updateLayout(itemView.getView());  // Switch to ItemView
        });

//        makeOffer.setOnAction(e -> System.out.println("Make Offer clicked"));
        viewWishlist.setOnAction(e -> System.out.println("View Wishlist clicked"));
        removeWishlistItem.setOnAction(e -> System.out.println("Remove Item from Wishlist clicked"));
        viewHistory.setOnAction(e -> System.out.println("View Purchase History clicked"));
    }

    public VBox getView() {
        return layout;
    }
}
