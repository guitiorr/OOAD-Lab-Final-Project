package view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.function.BiConsumer;

import controller.UserController;

public class LoginView {
    private GridPane layout;
    private Text validationMessage; // Pesan validasi

    public LoginView(BiConsumer<String, String> onLoginSuccess) {
        UserController uc = new UserController();
        layout = new GridPane();
        layout.setAlignment(Pos.CENTER);

        // Input fields
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        // Validation message
        validationMessage = new Text();
        validationMessage.setFill(Color.RED);

        VBox loginBox = new VBox(10, loginButton, validationMessage);
        loginBox.setAlignment(Pos.CENTER);
        layout.add(usernameLabel, 0, 0);
        layout.add(usernameField, 0, 1);
        layout.add(passwordLabel, 0, 2);
        layout.add(passwordField, 0, 3);
        layout.add(loginBox, 0, 4);

        layout.setMargin(loginBox, new Insets(20, 0, 0, 0));

        // Login Button Action
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Reset validation message
            validationMessage.setText("");

            if (username.isEmpty() || password.isEmpty()) {
                validationMessage.setText("Username and Password cannot be empty!");
                return;
            }

            if (uc.validateCredentials(username, password)) {
                String role = uc.getUserRole(username);
                System.out.println("Role retrieved: " + role);

                if (role == null) {
                    validationMessage.setText("Failed to retrieve role. Please try again.");
                } else if (role.equalsIgnoreCase("buyer") || role.equalsIgnoreCase("seller") || role.equalsIgnoreCase("admin")) {
                    onLoginSuccess.accept(username, role);
                } else {
                    validationMessage.setText("Invalid role assigned to user.");
                }
            } else {
                validationMessage.setText("Invalid username or password. Please try again.");
            }
        });
    }

    public GridPane getView() {
        return layout;
    }
}
