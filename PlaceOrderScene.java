//New Chris    Project 4
//Allows user to place a new order
//package application;

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
import java.util.*;
import javafx.scene.paint.Color;


public class PlaceOrderScene extends SceneBasic {
	private Button returnButton = new Button("Return to menu");
	private Button submitButton = new Button("Submit Order");
	private GridPane gridPane = new GridPane();
	private final int FONT_SIZE = 20;
	private TextField stockNumberField = new TextField();
	private TextField quantityField = new TextField();
	
	public PlaceOrderScene() {
		super("Ordering");
        //Creating Grid Pane 
		final int FONT_SIZE = 20;
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.setVgap(5); 
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        
        Label userLabel = new Label("Stock Number");
        userLabel.setFont(new Font(FONT_SIZE));
        Label accountLabel = new Label("Description");
        accountLabel.setFont(new Font(FONT_SIZE));
        Label quantityLabel = new Label ("Quantity");
        quantityLabel.setFont(new Font (FONT_SIZE));
        
        gridPane.add(userLabel, 0, 0);
        gridPane.add(accountLabel, 1, 0);
        gridPane.add(quantityLabel, 2, 0);
        
        gridPane.add(new Label("Enter Stock Number: "), 0, 1);
        stockNumberField.setPromptText("Stock Number");
        gridPane.add(stockNumberField, 1, 2);
        
        gridPane.add(new Label("Enter Quantity: "), 0, 2);
        quantityField.setPromptText("Quantity");
        gridPane.add(quantityField, 1, 2);
        
        gridPane.add(submitButton, 1, 3);
        submitButton.setOnAction(e -> sendOrder());
        
        gridPane.setAlignment(Pos.CENTER);
        root.getChildren().addAll(gridPane);
        
        int WIDTH = 200;
        returnButton.setMinWidth(WIDTH);
        root.getChildren().addAll(returnButton);
        returnButton.setOnAction(e -> SceneManager.setScene(SceneManager.SceneType.customer));
        gridPane.add(returnButton, 1, 4);
        
        root.getChildren().addAll(gridPane);
        
	}

	public void getInventory() {
		// TODO Auto-generated method stub
		try {
			Socket connection = SceneManager.getSocket();
			PrintWriter outgoing = new PrintWriter(connection.getOutputStream());
			System.out.println("Sending... VIEW_INVENTORY");
			outgoing.println("VIEW_ORDERS");
			outgoing.flush();
			
			BufferedReader incoming = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			System.out.println("Waiting for orders...");
			
			String line;
			int row = 2;
			
			while ((line = incoming.readLine()) != null && !line.equals("DONE")) {
				String[] orderData = line.split(",");
				
				if (orderData.length == 3) {
					Label stockNumLabel = new Label (orderData[0]);
					stockNumLabel.setFont(new Font(FONT_SIZE));
					gridPane.add(stockNumLabel, 0, row);
					
					Label descLabel = new Label(orderData[1]);
					descLabel.setFont(new Font(FONT_SIZE));
					gridPane.add(descLabel, 1, row);
					
					Label quantityLabel = new Label(orderData[2]);
					quantityLabel.setFont(new Font(FONT_SIZE));
					gridPane.add(quantityLabel, 2, row);
					
					row++;
					
				} else {
					System.out.println("There was an error. Invalid order data format: " + line);
				}
			}
		}
		catch (Exception e) {
			System.out.println("Error: " + e);
		}
		
	}
	
	public void sendOrder() {
		TextField stockNumberField = (TextField) gridPane.lookup("#stockNumber");
		TextField descriptionField = (TextField) gridPane.lookup("description");
		
		String stockNumber = stockNumberField.getText();
		String description = descriptionField.getText();
		
		if (stockNumber.isEmpty() || description.isEmpty()) {
			System.out.println("ERROR: Please Fill in both fields.");
			return;
		}
		
		try {
			Socket connection = SceneManager.getSocket();
			PrintWriter outgoing = new PrintWriter(connection.getOutputStream(), true);
			System.out.println("Sending order...");
			
			String orderData = stockNumber + "," + description;
			outgoing.println("PLACE_ORDER");
			outgoing.println(orderData);
			
			BufferedReader incoming = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String response = incoming.readLine();
			
			if (response != null && response.equals("ORDER_PLACED")) {
				System.out.println("Your Order Was Placed Successfully!");
			} else {
				System.out.println("Sorry!! There was an error placing your order.\nPlease try again!");
			}
		}
		catch (Exception e) {
			System.out.println("Error: " + e);
		}
		
	}

}
