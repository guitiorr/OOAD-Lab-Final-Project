package view;

import java.util.Map;

import controller.ItemController;
import controller.OfferController;
import controller.TransactionController;
import controller.UserController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
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
        private TableView<Map<String, Object>> tableView;

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
            TableColumn<Map<String, Object>, String> offerIdColumn = new TableColumn<>("Offer ID");
            offerIdColumn.setCellValueFactory(cellData -> 
                new SimpleStringProperty(String.valueOf(((Offer) cellData.getValue().get("offer")).getOfferId()))
            );

            TableColumn<Map<String, Object>, String> itemIdColumn = new TableColumn<>("Item ID");
            itemIdColumn.setCellValueFactory(cellData -> 
                new SimpleStringProperty(((Offer) cellData.getValue().get("offer")).getItemId())
            );

            TableColumn<Map<String, Object>, String> itemNameColumn = new TableColumn<>("Item Name");
            itemNameColumn.setCellValueFactory(cellData -> 
                new SimpleStringProperty((String) cellData.getValue().get("itemName"))
            );

            TableColumn<Map<String, Object>, String> offerPriceColumn = new TableColumn<>("Offer Price");
            offerPriceColumn.setCellValueFactory(cellData -> 
                new SimpleStringProperty(String.valueOf(((Offer) cellData.getValue().get("offer")).getOfferPrice()))
            );

            TableColumn<Map<String, Object>, String> buyerIdColumn = new TableColumn<>("Buyer ID");
            buyerIdColumn.setCellValueFactory(cellData -> 
                new SimpleStringProperty(((Offer) cellData.getValue().get("offer")).getUserId())
            );

            TableColumn<Map<String, Object>, String> statusColumn = new TableColumn<>("Status");
            statusColumn.setCellValueFactory(cellData -> 
                new SimpleStringProperty(((Offer) cellData.getValue().get("offer")).getStatus())
            );

            // Actions Column
            TableColumn<Map<String, Object>, Void> actionColumn = new TableColumn<>("Actions");
            actionColumn.setCellFactory(param -> new TableCell<>() {
                private final HBox buttonBox = new HBox(10);
                private final Button acceptButton = new Button("Accept");
                private final Button rejectButton = new Button("Reject");

                {
                    acceptButton.setOnAction(e -> handleAccept(getTableRow().getItem()));
                    rejectButton.setOnAction(e -> handleReject(getTableRow().getItem()));
                    buttonBox.getChildren().addAll(acceptButton, rejectButton);
                    buttonBox.setAlignment(Pos.CENTER);
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                        setGraphic(null);
                    } else {
                        Map<String, Object> rowData = getTableRow().getItem();
                        Offer currentOffer = (Offer) rowData.get("offer");
                        if ("Pending".equalsIgnoreCase(currentOffer.getStatus())) {
                            setGraphic(buttonBox);
                        } else {
                            setGraphic(null);
                        }
                    }
                }
            });
            actionColumn.setPrefWidth(200);  // Set preferred width for the column to make it wider
            actionColumn.setMinWidth(150); 
            // Add columns to the table
            tableView.getColumns().addAll(
                offerIdColumn,
                itemIdColumn,
                itemNameColumn,
                offerPriceColumn,
                buyerIdColumn,
                statusColumn,
                actionColumn
            );

            UserController uc = new UserController();
            String sellerId = uc.getUserIdByUsername(username);

            // Load offers for the seller's items
            OfferController offerController = new OfferController();
            ObservableList<Map<String, Object>> offers = FXCollections.observableArrayList(offerController.getOffersBySellerId(sellerId));

            tableView.setItems(offers);

            // Add components to layout
            layout.getChildren().addAll(returnButton, tableView);
        }

        private void handleAccept(Map<String, Object> rowData) {
            Offer offer = (Offer) rowData.get("offer");
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

        private void handleReject(Map<String, Object> rowData) {
            Offer offer = (Offer) rowData.get("offer");
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
