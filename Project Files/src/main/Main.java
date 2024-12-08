package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.RegisterView;
import view.LoginView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Main Application");

        // Create Home Page Layout
        VBox homeLayout = new VBox(10); // Vertical layout with spacing
        homeLayout.setSpacing(20);

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        // Add buttons to the layout
        homeLayout.getChildren().addAll(loginButton, registerButton);

        // Create a Scene for Home Page
        Scene homeScene = new Scene(homeLayout, 400, 300);

        // Login View Scene
        LoginView loginView = new LoginView();
        Scene loginScene = new Scene(loginView.getView(), 800, 600);

        // Register View Scene
        RegisterView registerView = new RegisterView();
        Scene registerScene = new Scene(registerView.getView(), 800, 600);

        // Button Actions
        loginButton.setOnAction(e -> primaryStage.setScene(loginScene));
        registerButton.setOnAction(e -> primaryStage.setScene(registerScene));

        // Set Home Scene as the initial scene
        primaryStage.setScene(homeScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
