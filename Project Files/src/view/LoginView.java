package view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.function.BiConsumer;

import controller.UserController;

public class LoginView {
    private GridPane layout;
    private BorderPane layoutLuar;

    public LoginView(BiConsumer<String, String> onLoginSuccess) {
        UserController uc = new UserController();
        layout = new GridPane();

        layout.setAlignment(Pos.CENTER);
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        VBox loginBox = new VBox(loginButton); // Membuat VBox untuk tombol
        loginBox.setAlignment(Pos.CENTER);
        layout.add(usernameLabel, 0, 0);
        layout.add(usernameField, 0, 1);
        layout.add(passwordLabel, 0, 3);
        layout.add(passwordField, 0, 4);
        layout.add(loginBox, 0, 5);
        layout.setMargin(loginBox, new Insets(20, 0, 0, 0));
        // Login Logic
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.equals("admin") && password.equals("admin")) {
                onLoginSuccess.accept(username, "Admin");
            } else if (uc.validateCredentials(username, password)) {
                String role = determineUserRole(username); // Example logic
                onLoginSuccess.accept(username, role);
            } else {
                System.out.println("Invalid credentials.");
            }
        });
    }

    private boolean validateUser(String username, String password) {
        // Replace this with actual database check logic
        return !username.isEmpty() && !password.isEmpty();
    }

    private String determineUserRole(String username) {
        // Example: Retrieve role from database based on username
        return username.startsWith("buyer") ? "Buyer" : "Seller";
    }

    public GridPane getView() {
        return layout;
    }

}