package main;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.RegisterView;
import view.LoginView;
import view.BuyerView;
import view.SellerView;
import view.AdminView;
import view.ItemView;

public class Main extends Application {

    private static VBox mainLayout;
    private static String currentUsername; // Static variable to store the logged-in username

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Main Application");

        // Initial layout (Home page with login and register options)
        mainLayout = new VBox(10);

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        mainLayout.getChildren().addAll(loginButton, registerButton);

        // Create Login and Register views
        LoginView loginView = new LoginView((username, role) -> {
            currentUsername = username; // Store the username when the user logs in
            mainLayout.getChildren().clear(); // Clear current layout
            switch (role) {
                case "Buyer":
                    BuyerView buyerView = new BuyerView(username, role);
                    mainLayout.getChildren().add(buyerView.getView());
                    break;
                case "Seller":
                    SellerView sellerView = new SellerView(username, role);
                    mainLayout.getChildren().add(sellerView.getView());
                    break;
                case "Admin":
                    AdminView adminView = new AdminView(username, role);
                    mainLayout.getChildren().add(adminView.getView());
                    break;
                default:
                    System.out.println("Invalid role");
            }
        });

        RegisterView registerView = new RegisterView();

        // Button actions
        loginButton.setOnAction(e -> {
            mainLayout.getChildren().clear();
            mainLayout.getChildren().add(loginView.getView());
        });

        registerButton.setOnAction(e -> {
            mainLayout.getChildren().clear();
            mainLayout.getChildren().add(registerView.getView());
        });

        // Set the initial layout on the stage
        primaryStage.setScene(new javafx.scene.Scene(mainLayout, 800, 600));
        primaryStage.show();
    }

    // Method to update the layout (no scenes)
    public static void updateLayout(VBox newLayout) {
        mainLayout.getChildren().clear(); // Clear current layout
        mainLayout.getChildren().addAll(newLayout.getChildren()); // Add new layout content
    }

    // Getter for the current username
    public static String getCurrentUsername() {
        return currentUsername;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

