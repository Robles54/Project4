// New Chris Project 4
// Allows user to place a new order
// package application;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javafx.scene.paint.Color;

public class PlaceOrderScene extends SceneBasic {
    private Button returnButton = new Button("Return to menu");
    private Button submitButton = new Button("Submit Order");
    private TextField stockNumberField = new TextField();
    private TextField quantityField = new TextField();
    private GridPane gridPane = new GridPane();
    private final int FONT_SIZE = 20;

    public PlaceOrderScene() {
        super("Ordering");
        
        root.setPrefSize(400, 900);

        // Set up grid pane for inventory display
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.setVgap(5); 
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        // Column headers
        Label stockHeader = new Label("Stock #");
        stockHeader.setFont(new Font(FONT_SIZE));
        Label descHeader = new Label("Description");
        descHeader.setFont(new Font(FONT_SIZE));
        
        gridPane.add(stockHeader, 0, 0);
        gridPane.add(descHeader, 1, 0);

        // Set up text fields for order input
        TextField stockNumberField = new TextField();
        stockNumberField.setPromptText("Enter stock #");
        stockNumberField.setId("stockNumber");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Enter quantity");
        quantityField.setId("quantity");

        // Input fields with labels
        HBox stockInputBox = new HBox(5, new Label("Stock #"), stockNumberField);
        HBox quantityInputBox = new HBox(5, new Label("Quantity"), quantityField);

        // Set up buttons
        submitButton.setMinWidth(100);
        returnButton.setMinWidth(100);

        // Action listeners for buttons
        submitButton.setOnAction(e -> sendOrder());
        returnButton.setOnAction(e -> SceneManager.setScene(SceneManager.SceneType.customer));

        // Button layout
        HBox buttonBox = new HBox(10, submitButton, returnButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Layout for entire scene
        VBox layout = new VBox(10, gridPane, stockInputBox, quantityInputBox, buttonBox);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        root.getChildren().add(layout);
    }

    public void getInventory() {
        new Thread(() -> {
            try {
                Socket connection = SceneManager.getSocket();
                PrintWriter outgoing = new PrintWriter(connection.getOutputStream());
                System.out.println("Sending... VIEW_INVENTORY");
                outgoing.println("VIEW_INVENTORY");
                outgoing.flush();

                BufferedReader incoming = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                System.out.println("Waiting for inventory...");

                String line;
                int row = 2;  // Start after header and input fields

                while ((line = incoming.readLine()) != null) {
                    // Stop processing if the DONE message is received
                    if (line.equalsIgnoreCase("DONE")) {
                        System.out.println("Inventory data transmission completed.");
                        break;
                    }

                    String[] inventoryData = line.split(",");

                    // Process two-field and three-field formats
                    if (inventoryData.length == 2 || inventoryData.length == 3) {
                        String stockNumber = inventoryData[0];
                        String description = inventoryData[1];
                        String quantity = (inventoryData.length == 3) ? inventoryData[2] : "0"; // Default quantity

                        // Update the UI on the JavaFX Application Thread
                        final int currentRow = row;
                        Platform.runLater(() -> {
                            Label stockNumLabel = new Label(stockNumber);
                            stockNumLabel.setFont(new Font(FONT_SIZE));
                            gridPane.add(stockNumLabel, 0, currentRow);

                            Label descLabel = new Label(description);
                            descLabel.setFont(new Font(FONT_SIZE));
                            gridPane.add(descLabel, 1, currentRow);

                            Label qtyLabel = new Label(quantity);
                            qtyLabel.setFont(new Font(FONT_SIZE));
                            gridPane.add(qtyLabel, 2, currentRow);
                        });

                        row++;
                    } else {
                        System.out.println("Error: Invalid inventory data format: " + line);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error fetching inventory: " + e);
                e.printStackTrace();
            }
        }).start();
    }

    public void sendOrder() {
        // Collect input from text fields
        String stockNumber = stockNumberField.getText();
        String quantity = quantityField.getText();

        if (stockNumber.isEmpty() || quantity.isEmpty()) {
            System.out.println("ERROR: Please fill in both Stock Number and Quantity fields.");
            return;
        }

        try {
            Socket connection = SceneManager.getSocket();
            if (connection == null) {
            	System.out.println("ERROR: No Socket Connection");
            	return;
            }
            PrintWriter outgoing = new PrintWriter(connection.getOutputStream(), true);
            System.out.println("Sending order...");

            String orderData = stockNumber + "," + quantity;
            outgoing.println("PLACE_ORDER");
            outgoing.println(orderData);
            
            System.out.println("Order Data Sent");

            BufferedReader incoming = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = incoming.readLine();

            if ("ORDER_PLACED".equals(response)) {
                System.out.println("Order placed successfully!");
            } else {
                System.out.println("Error placing order. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Error sending order: " + e);
        }
    }
}
