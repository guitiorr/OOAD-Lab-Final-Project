package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class RegisterView {

    private GridPane gridPane;
    private UserController userController;

    public RegisterView() {
        // Initialize UserController
        userController = new UserController();

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
        RadioButton buyerRadio = new RadioButton("Buyer");
        ToggleGroup roleGroup = new ToggleGroup();
        sellerRadio.setToggleGroup(roleGroup);
        buyerRadio.setToggleGroup(roleGroup);
        gridPane.add(roleLabel, 0, 4);
        gridPane.add(sellerRadio, 1, 4);
        gridPane.add(buyerRadio, 1, 5);

        // Register Button
        Button registerButton = new Button("Register");
        gridPane.add(registerButton, 1, 6);

        // Validation Message
        Label validationLabel = new Label();
        gridPane.add(validationLabel, 1, 7);
        gridPane.setAlignment(Pos.CENTER);
        // Register Button Action
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();
            RadioButton selectedRole = (RadioButton) roleGroup.getSelectedToggle();
            String role = selectedRole != null ? selectedRole.getText() : null;

            // Validate inputs
            if (!validateUsername(username)) {
                validationLabel.setText("Username must be at least 3 characters long and cannot be empty.");
                return;
            }
            if (!validatePassword(password)) {
                validationLabel.setText("Password must be at least 8 characters long and include a special character (!, @, #, $, %, ^, &, *).");
                return;
            }
            if (!validatePhoneNumber(phone)) {
                validationLabel.setText("Phone number must start with +62 and be at least 10 digits long.");
                return;
            }
            if (address.isEmpty() || role == null) {
                validationLabel.setText("All fields must be filled out, and a role must be selected.");
                return;
            }

            // Generate unique ID
            String newID = generateUserID();

            // Call UserController to handle registration
            userController.insertUser(newID, username, phone, address, role, password);
            validationLabel.setText("Registration successful!");
            clearFields(usernameField, passwordField, phoneField, addressField, roleGroup);
        });
    }

    // Validate Username
    private boolean validateUsername(String username) {
        return username != null && username.length() >= 3;
    }

    // Validate Password
    private boolean validatePassword(String password) {
        return password != null && password.length() >= 8 && password.matches(".*[!@#$%^&*].*");
    }

    // Validate Phone Number
    private boolean validatePhoneNumber(String phone) {
        return phone != null && phone.startsWith("+62") && phone.length() >= 12; // +62 counts as 3 characters
    }

    // Method to generate unique user IDs
    private String generateUserID() {
        int nextID = userController.getAllUser().size() + 1;
        return String.format("US%03d", nextID);
    }

    // Method to clear input fields after successful registration
    private void clearFields(TextField usernameField, PasswordField passwordField, TextField phoneField, TextField addressField, ToggleGroup roleGroup) {
        usernameField.clear();
        passwordField.clear();
        phoneField.clear();
        addressField.clear();
        roleGroup.selectToggle(null);
    }

    // Method to return the view (GridPane)
    public GridPane getView() {
        return gridPane;
    }
}
