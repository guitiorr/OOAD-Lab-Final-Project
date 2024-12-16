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

    public LoginView(BiConsumer<String, String> onLoginSuccess) {
        UserController uc = new UserController();
        layout = new GridPane();

        layout.setAlignment(Pos.CENTER);
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        VBox loginBox = new VBox(loginButton);
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

            if (uc.validateCredentials(username, password)) {
            	String role = uc.getUserRole(username);
            	System.out.println("Role retrieved: " + role);

            	if (role == null) {
            	    System.out.println("Role is null for username: " + username);
            	} 
            	else if (role.equalsIgnoreCase("buyer")) {
            	    onLoginSuccess.accept(username, role);
            	} 
            	else if (role.equalsIgnoreCase("seller")) {
            		onLoginSuccess.accept(username, role);
            	}
            	else if (role.equalsIgnoreCase("admin")) {
            	    onLoginSuccess.accept(username, role);
            	} 
            	else {
            	    System.out.println("Invalid role: " + role);
            	}

            } else {
                System.out.println("Invalid credentials.");
            }
        });
    }

    private String determineUserRole(String username, UserController uc) {
        System.out.println("Determining role for: " + username);  // Debugging line
        String role = uc.getUserRole(username); 
        System.out.println("Role found: " + role);  // Debugging line
        return role; 
    }


    public GridPane getView() {
        return layout;
    }
}