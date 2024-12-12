package view;

import controller.ItemController;
import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import main.Main;
import models.Item;

public class SellerItemView {
    private VBox layout;
    private TableView<Item> tableView;

    public SellerItemView(String username, String role) {
        layout = new VBox(10);
        tableView = new TableView<>();

        // "Return" button to go back to SellerView
        Button returnButton = new Button("Return");
        returnButton.setOnAction(e -> {
            SellerView sellerView = new SellerView(username, role);
            Main.updateLayout(sellerView.getView());
        });

        // Define columns
        TableColumn<Item, String> itemIdColumn = new TableColumn<>("Item ID");
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<Item, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Item, String> itemPriceColumn = new TableColumn<>("Item Price");
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        TableColumn<Item, String> itemStatusColumn = new TableColumn<>("Item Status");
        itemStatusColumn.setCellValueFactory(new PropertyValueFactory<>("itemStatus"));

        // Add columns to table
        tableView.getColumns().addAll(itemIdColumn, itemNameColumn, itemPriceColumn, itemStatusColumn);
        
        UserController uc = new UserController();
        
        String userId = uc.getUserIdByUsername(username);

        // Fetch and display seller's items
        ItemController ic = new ItemController();
        ObservableList<Item> sellerItems = FXCollections.observableArrayList(
            ic.getItemsBySeller(userId) // This method should return a List<Item> for the seller
        );
        tableView.setItems(sellerItems);

        // Add components to layout
        layout.getChildren().addAll(returnButton, tableView);
    }

    public VBox getView() {
        return layout;
    }
}