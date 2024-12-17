package view;

import controller.ItemController;
import controller.OfferController;
import controller.TransactionController;
import controller.UserController;
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
import models.Offer;

public class OffersView {
    private VBox layout;
    private TableView<Offer> tableView;

    public OffersView(String username, String role) {
        layout = new VBox(10);
        tableView = new TableView<>();

        // Create "Return" button
        Button returnButton = new Button("Return");
        returnButton.setOnAction(e -> {
            // Return to seller's main view (placeholder implementation)
            SellerView sellerMainView = new SellerView(username, role);
            Main.updateLayout(sellerMainView.getView());
        });

        // Define table columns
        TableColumn<Offer, String> offerIdColumn = new TableColumn<>("Offer ID");
        offerIdColumn.setCellValueFactory(new PropertyValueFactory<>("offerId"));

        TableColumn<Offer, String> itemIdColumn = new TableColumn<>("Item ID");
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<Offer, String> offerPriceColumn = new TableColumn<>("Offer Price");
        offerPriceColumn.setCellValueFactory(new PropertyValueFactory<>("offerPrice"));

        TableColumn<Offer, String> buyerIdColumn = new TableColumn<>("Buyer ID");
        buyerIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<Offer, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        TableColumn<Offer, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final HBox buttonBox = new HBox(10);
            private final Button acceptButton = new Button("Accept");
            private final Button rejectButton = new Button("Reject");

            {
                acceptButton.setOnAction(e -> handleAccept(getTableRow().getItem()));
                rejectButton.setOnAction(e -> handleReject(getTableRow().getItem()));
                buttonBox.getChildren().addAll(acceptButton, rejectButton);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
//                super.updateItem(item, empty);
//                if (empty || getTableRow().getItem() == null) {
//                    setGraphic(null);
//                } else {
//                    Offer currentOffer = getTableRow().getItem();
//                    if ("Pending".equalsIgnoreCase(currentOffer.getStatus())) {
//                        setGraphic(buttonBox);
//                    } else {
//                        setGraphic(null);
//                    }
//                }
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    Offer currentOffer = getTableRow().getItem();
                    if ("Pending".equalsIgnoreCase(currentOffer.getStatus())) {
                        setGraphic(buttonBox);
                    } else {
                        setGraphic(null);
                    }
                }
            }
        });


        // Add columns to the table
        tableView.getColumns().addAll(
            offerIdColumn,
            itemIdColumn,
            offerPriceColumn,
            buyerIdColumn,
            statusColumn,
            actionColumn
        );

        UserController uc = new UserController();
        
        String sellerId = uc.getUserIdByUsername(username);

        // Load offers for the seller's items
        OfferController offerController = new OfferController();
        ObservableList<Offer> offers = FXCollections.observableArrayList(
            offerController.getOffersBySellerId(sellerId) // Fetch offers for items owned by the seller
        );
        tableView.setItems(offers);

        // Add components to layout
        layout.getChildren().addAll(returnButton, tableView);

    }

    private void handleAccept(Offer offer) {
        if (offer != null) {
        	UserController uc = new UserController();
        	TransactionController tc = new TransactionController();
            OfferController offerController = new OfferController();
            ItemController ic = new ItemController();
            offerController.updateOfferStatus(offer.getOfferId(), "Accepted"); // Update status to "Accepted"
            offer.setStatus("Accepted");
            String userId = offer.getUserId();
            String itemId = offer.getItemId();
            double updatedPrice = offer.getOfferPrice();
            
            ic.updateItemPrice(itemId, updatedPrice);
            ic.switchStatusToSold(itemId);
            
            tc.insertTransaction(userId, itemId);
            tableView.refresh(); // Refresh the table view
            System.out.println("Offer accepted: " + offer.getOfferId());
        }
    }

    private void handleReject(Offer offer) {
        if (offer != null) {
            OfferController offerController = new OfferController();
            offerController.updateOfferStatus(offer.getOfferId(), "Rejected"); // Update status to "Rejected"
            offer.setStatus("Rejected");
            tableView.refresh(); // Refresh the table view
            System.out.println("Offer rejected: " + offer.getOfferId());
        }
    }

    public VBox getView() {
        return layout;
    }
}
