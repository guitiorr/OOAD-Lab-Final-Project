package view;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import controller.ItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import models.Item;

public class SellerView {
    private VBox layout;

    public SellerView(String username, String role) {
        layout = new VBox(10);

        // User Information
        Label loggedInAsLabel = new Label("Logged in as: " + username);
        Label roleLabel = new Label("Role: " + role);

        // Navigation Buttons
        Button viewMyItems = new Button("View My Items");
        Button addItem = new Button("Add Item");
        Button viewOffers = new Button("View Offers");
        Button logout=new Button("logout");

        // Organize navigation buttons in an HBox
        HBox navigation = new HBox(10, viewMyItems, addItem, viewOffers);

        // Event handlers
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
        viewMyItems.setOnAction(e -> {
            // Navigate to SellerItemView
            SellerItemView sellerItemView = new SellerItemView(username, role);
            Main.updateLayout(sellerItemView.getView());
        });

        addItem.setOnAction(e -> {
            // Navigate to AddItemView
            AddItemView addItemView = new AddItemView(username);
            Main.updateLayout(addItemView.getView());
        });

        viewOffers.setOnAction(e -> {
        	OffersView offersView = new OffersView(username, role);
            Main.updateLayout(offersView.getView());
        });

        // Add elements to the layout
        layout.getChildren().addAll(loggedInAsLabel, roleLabel, navigation,logout);
        navigation.setAlignment(Pos.CENTER);
        layout.setAlignment(Pos.CENTER);
    }

    public VBox getView() {
        return layout;
    }
}
