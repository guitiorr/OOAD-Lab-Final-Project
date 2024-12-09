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

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Main Application");

        // Main layout (this will hold the active view)
        VBox mainLayout = new VBox(10);

        // Home Page Layout
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        mainLayout.getChildren().addAll(loginButton, registerButton);

        // Initialize Login and Register Views
        LoginView loginView = new LoginView((username, role) -> {
            // Clear mainLayout and load the appropriate role view
            mainLayout.getChildren().clear();
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

        // Button Actions for Home Page
        loginButton.setOnAction(e -> {
            mainLayout.getChildren().clear();
            mainLayout.getChildren().add(loginView.getView());
        });

        registerButton.setOnAction(e -> {
            mainLayout.getChildren().clear();
            mainLayout.getChildren().add(registerView.getView());
        });

        // Set the primary stage with a single Scene
        primaryStage.setScene(new javafx.scene.Scene(mainLayout, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
