package view;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class AdminView {
    private VBox layout;

    public AdminView(String username, String role) {
        layout = new VBox(10);

        // User Information
        Label loggedInAsLabel = new Label("Logged in as: " + username);
        Label roleLabel = new Label("Role: " + role);

        // Buttons for Admin Actions
        Button viewRequests = new Button("View Requested Items");
        Button approveItem = new Button("Approve Item");
        Button declineItem = new Button("Decline Item");

        layout.getChildren().addAll(
            loggedInAsLabel,
            roleLabel,
            viewRequests,
            approveItem,
            declineItem
        );

        // Add event handlers for buttons
        viewRequests.setOnAction(e -> System.out.println("View Requested Items clicked"));
        approveItem.setOnAction(e -> System.out.println("Approve Item clicked"));
        declineItem.setOnAction(e -> System.out.println("Decline Item clicked"));
    }

    public VBox getView() {
        return layout;
    }
}
