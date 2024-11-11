//New Maria Galarza         Project 4
//package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane; 


public class ViewOrdersScene extends SceneBasic {
	private Button returnButton = new Button("Return to menu");
	private GridPane gridPane = new GridPane();
	private final int FONT_SIZE = 20;

	public ViewOrdersScene() {
		super("Your Orders");
		//Creating Grid Pane 
		final int FONT_SIZE = 20;
		gridPane.setPadding(new Insets(10, 10, 10, 10)); 
		gridPane.setVgap(20); 
		gridPane.setHgap(20);

		Label userLabel = new Label("Stock Number");
		userLabel.setFont(new Font(FONT_SIZE));
		Label accountLabel = new Label("Description");
		accountLabel.setFont(new Font(FONT_SIZE));
		Label quantityLabel = new Label("Quantity");
		quantityLabel.setFont(new Font(FONT_SIZE));

		gridPane.add(userLabel, 0, 0);
		gridPane.add(accountLabel, 1, 0);
		gridPane.add(quantityLabel, 2, 0);
		gridPane.setAlignment(Pos.CENTER);
		root.getChildren().addAll(gridPane);

		int WIDTH = 200;
		returnButton.setMinWidth(WIDTH);
		root.getChildren().addAll(returnButton);
		returnButton.setOnAction(e -> SceneManager.setScene(SceneManager.SceneType.customer));
	}

	public void getOrders() {
		try {
			Socket connection = SceneManager.getSocket();
			PrintWriter outgoing = new PrintWriter(connection.getOutputStream());
			System.out.println("Sending... VIEW_ORDERS");
			outgoing.println("VIEW_ORDERS"); 
			outgoing.flush();

			BufferedReader incoming = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        System.out.println("Waiting for orders...");

	        String line;
	        int row = 1;
	        
			while ((line = incoming.readLine()) != null && !line.equals("DONE")) { 
				String[] orderData = line.split(","); 
				
				if (orderData.length == 3) {
					Label stockNumLabel = new Label(orderData[0]);
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
                    System.err.println("Invalid order data format: " + line);
                }
            }
        } catch (Exception e) {
            System.out.println("Error:  " + e);
        }
    }
}
