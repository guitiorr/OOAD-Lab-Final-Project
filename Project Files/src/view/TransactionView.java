package view;

import java.util.List;

import controller.ItemController;
import controller.TransactionController;
import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import main.Main;
import models.Item;
import models.Transaction;

public class TransactionView {
    private VBox layout;
    private TableView<Item> tableView;

    public TransactionView(String username) {
        layout = new VBox(10);
        tableView = new TableView<>();

        // Display the user's purchase history
        Label titleLabel = new Label("Purchase History for User: " + username);

        // Create the "Return" button
        Button returnButton = new Button("Return");
        returnButton.setOnAction(e -> {
            // Return to BuyerView
            BuyerView buyerView = new BuyerView(username, "buyer");
            Main.updateLayout(buyerView.getView());
        });

        
        
        // Define columns for Item details
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
        
        // Add columns to the table
        tableView.getColumns().addAll(
                itemIdColumn,
                itemNameColumn,
                itemSizeColumn,
                itemPriceColumn,
                itemCategoryColumn,
                itemStatusColumn
        );

        UserController userController = new UserController();
        String userId = userController.getUserIdByUsername(username);

        // Fetch and populate data
        TransactionController transactionController = new TransactionController();
        List<Transaction> transactions = transactionController.getTransactionsWithItemsByUserId(userId);

        // Extract all Items from Transactions
        ObservableList<Item> items = FXCollections.observableArrayList();
        for (Transaction transaction : transactions) {
            // Assume TransactionController fetches transactions with their associated items
            items.addAll(transactionController.getItemsByTransactionId(transaction.getTransactionID()));
        }

        tableView.setItems(items);

        // Add components to layout
        layout.getChildren().addAll(titleLabel, returnButton, tableView);
    }

    public VBox getView() {
        return layout;
    }
}