package view;

import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;

public class AdminView {
    private VBox layout;

    public AdminView(String username, String role) {
        layout = new VBox(10);

        // User Information
        Label loggedInAsLabel = new Label("Logged in as: " + username);
        Label roleLabel = new Label("Role: " + role);

        // Buttons for Admin Actions
        Button viewRequests = new Button("View Requested Items");
      
        Button logout=new Button("logout");
        logout.setOnAction(e -> {
       	 Stage currentStage = (Stage) logout.getScene().getWindow();
       	    currentStage.close();

       	    // Membuka stage baru (tampilan utama)
       	    Main main = new Main();
       	    Stage newStage = new Stage();
       	    try {
       	        main.start(newStage);
       	    } catch (Exception ex) {
       	        ex.printStackTrace();
       	    }
       });
        layout.getChildren().addAll(
            loggedInAsLabel,
            roleLabel,
            viewRequests,
            logout
        );
        layout.setAlignment(Pos.CENTER);
        // Add event handlers for buttons
        viewRequests.setOnAction(e -> {
            RequestItemView requestItemView = new RequestItemView(username, role);
            Main.updateLayout(requestItemView.getView());
        });
   
    }

    public VBox getView() {
        return layout;
    }
}

