package view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class RegisterView {

    private GridPane gridPane;

    public RegisterView() {
        // Create a GridPane layout
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        // Username Field
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);

        // Password Field
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);

        // Phone Number Field
        Label phoneLabel = new Label("Phone Number:");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter phone number (+62...)");
        gridPane.add(phoneLabel, 0, 2);
        gridPane.add(phoneField, 1, 2);

        // Address Field
        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField();
        addressField.setPromptText("Enter your address");
        gridPane.add(addressLabel, 0, 3);
        gridPane.add(addressField, 1, 3);

        // Roles Radio Buttons
        Label roleLabel = new Label("Role:");
        RadioButton sellerRadio = new RadioButton("Seller");
        ToggleGroup roleGroup = new ToggleGroup();
        sellerRadio.setToggleGroup(roleGroup);
        gridPane.add(roleLabel, 0, 4);
        gridPane.add(sellerRadio, 1, 4);

        // Register Button
        Button registerButton = new Button("Register");
        gridPane.add(registerButton, 1, 5);

        // Validation Message
        Label validationLabel = new Label();
        gridPane.add(validationLabel, 1, 6);

        // Register Button Action
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();
            RadioButton selectedRole = (RadioButton) roleGroup.getSelectedToggle();

            if (username.isEmpty() || username.length() < 3) {
                validationLabel.setText("Username must be at least 3 characters long and cannot be empty.");
                return;
            }

            if (password.isEmpty() || password.length() < 8 || !password.matches(".*[!@#$%^&*].*")) {
                validationLabel.setText("Password must be at least 8 characters long and include a special character.");
                return;
            }

            if (!phone.matches("^\\+62\\d{10}$")) {
                validationLabel.setText("Phone number must start with +62 and be 10 digits long.");
                return;
            }

            if (address.isEmpty()) {
                validationLabel.setText("Address cannot be empty.");
                return;
            }

            if (selectedRole == null) {
                validationLabel.setText("You must select a role.");
                return;
            }

            validationLabel.setText("Registration successful!");
        });
    }

    // Method to return the view (GridPane)
    public GridPane getView() {
        return gridPane;
    }
}