package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import main.Main;
import models.Item;
import connection.ItemDAO; // Import your ItemDAO class

public class ItemView {
    private VBox layout;
    private TableView<Item> tableView;

    public ItemView(String username, String role) {  // Accept username and role as arguments
        layout = new VBox(10);
        tableView = new TableView<>();

        // Create the "Return" button to go back to the previous view (BuyerView)
        Button returnButton = new Button("Return");
        returnButton.setOnAction(e -> {
            // Create and show the BuyerView again with the same username and role
            BuyerView buyerView = new BuyerView(username, role);
            Main.updateLayout(buyerView.getView()); // Switch back to BuyerView
        });

        // Define columns
        TableColumn<Item, String> itemIdColumn = new TableColumn<>("Item ID");
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<Item, String> itemNameColumn = new TableColumn<>("Item Name");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Item, String> itemSizeColumn = new TableColumn<>("Item Size");
        itemSizeColumn.setCellValueFactory(new PropertyValueFactory<>("itemSize"));

        TableColumn<Item, String> itemPriceColumn = new TableColumn<>("Item Price");
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        TableColumn<Item, String> itemCategoryColumn = new TableColumn<>("Item Category");
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));

        TableColumn<Item, String> itemStatusColumn = new TableColumn<>("Item Status");
        itemStatusColumn.setCellValueFactory(new PropertyValueFactory<>("itemStatus"));

        TableColumn<Item, String> itemWishlistColumn = new TableColumn<>("Wishlist");
        itemWishlistColumn.setCellValueFactory(new PropertyValueFactory<>("itemWishlist"));

        TableColumn<Item, String> itemOfferStatusColumn = new TableColumn<>("Offer Status");
        itemOfferStatusColumn.setCellValueFactory(new PropertyValueFactory<>("itemOfferStatus"));

        // Add columns to the table
        tableView.getColumns().addAll(
            itemIdColumn,
            itemNameColumn,
            itemSizeColumn,
            itemPriceColumn,
            itemCategoryColumn,
            itemStatusColumn,
            itemWishlistColumn,
            itemOfferStatusColumn
        );

        // Fetch the list of items from the ItemDAO
        ItemDAO itemDAO = new ItemDAO();
        ObservableList<Item> items = FXCollections.observableArrayList(itemDAO.getItems()); // Assuming getItems() returns a List<Item>

        tableView.setItems(items);

        // Add the return button first, then the table to the layout
        layout.getChildren().addAll(returnButton, tableView);
    }

    public VBox getView() {
        return layout;
    }
}
