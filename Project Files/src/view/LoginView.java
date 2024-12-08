package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class LoginView {
    private GridPane gridPane;
    private UserController userController;

    public LoginView() {
        userController = new UserController();

        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);

        Button loginButton = new Button("Login");
        gridPane.add(loginButton, 1, 2);

        Label validationLabel = new Label();
        gridPane.add(validationLabel, 1, 3);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                validationLabel.setText("Fields cannot be empty.");
                return;
            }

            if (username.equals("admin") && password.equals("admin")) {
                validationLabel.setText("Admin login successful!");
                goToHomePage();
                return;
            }

            if (userController.validateUser(username, password)) {
                validationLabel.setText("Login successful!");
                goToHomePage();
            } else {
                validationLabel.setText("Invalid credentials.");
            }
        });
    }

    private void goToHomePage() {
        System.out.println("Navigating to home page...");
    }

    public GridPane getView() {
        return gridPane;
    }
}
