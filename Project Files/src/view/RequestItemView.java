package view;

import controller.ItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import main.Main;
import models.Item;

public class RequestItemView {
	private VBox layout;
    private TableView<Item> tableView;

    public RequestItemView(String username, String role) {
        layout = new VBox(10);
        tableView = new TableView<>();

        // Create the "Return" button to go back to the previous view
        Button returnButton = new Button("Return");
        returnButton.setOnAction(e -> {
            AdminView adminView = new AdminView(username, role);
            Main.updateLayout(adminView.getView()); // Switch back to AdminView
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

        // Action column for approve/reject buttons
        TableColumn<Item, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setPrefWidth(200); // Set the preferred width for the column
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final HBox buttonBox = new HBox(10);
            private final Button approveButton = new Button("Approve");
            private final Button rejectButton = new Button("Reject");

            {
                approveButton.setOnAction(e -> handleApprove(getTableRow().getItem()));
                rejectButton.setOnAction(e -> handleReject(getTableRow().getItem()));
                buttonBox.getChildren().addAll(approveButton, rejectButton);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    Item currentItem = getTableRow().getItem();
                    if ("Pending".equalsIgnoreCase(currentItem.getItemStatus())) {
                        setGraphic(buttonBox);
                    } else {
                        setGraphic(null);
                    }
                }
            }
        });

        // Add columns to the table
        tableView.getColumns().addAll(
            itemIdColumn,
            itemNameColumn,
            itemSizeColumn,
            itemPriceColumn,
            itemCategoryColumn,
            itemStatusColumn,
            actionColumn
        );

        ItemController itemController = new ItemController();
        ObservableList<Item> items = FXCollections.observableArrayList(
            itemController.getItemsByStatus("Pending") // Assuming a method to fetch items with "Pending" status
        );
        tableView.setItems(items);

        // Add the return button first, then the table to the layout
        layout.getChildren().addAll(returnButton, tableView);
    }

    private void handleApprove(Item item) {
        if (item != null) {
            ItemController itemController = new ItemController();
            itemController.updateItemStatus(item.getItemId(), "Available"); // Update item status to "Approved"
            item.setItemStatus("Available");
            tableView.refresh(); // Refresh the table view to reflect the status change
            System.out.println("Item approved: " + item.getItemName());
        }
    }

    private void handleReject(Item item) {
        if (item != null) {
            ItemController itemController = new ItemController();
            itemController.updateItemStatus(item.getItemId(), "Rejected"); // Update item status to "Rejected"
            item.setItemStatus("Rejected");
            tableView.refresh(); // Refresh the table view to reflect the status change
            System.out.println("Item rejected: " + item.getItemName());
        }
    }

    public VBox getView() {
        return layout;
    }
}
