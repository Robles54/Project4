// From  Project #3
package application;

import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.scene.text.Font;

import javafx.scene.layout.GridPane; 
import javafx.geometry.Insets;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class AccountListScene extends SceneBasic {
	private Button adminButton = new Button("Admin Menu");
	private Button logoutButton = new Button("Logout");
	private GridPane gridPane = new GridPane();
	private final int FONT_SIZE = 17;

	public AccountListScene() {
        super("Account List");
        //Creating Grid Pane 
		final int FONT_SIZE = 20;
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.setVgap(5); 
        gridPane.setHgap(5);  
        Label userLabel = new Label("Username");
        userLabel.setFont(new Font(FONT_SIZE));
        Label accountLabel = new Label("Account");
        accountLabel.setFont(new Font(FONT_SIZE));
        gridPane.add(userLabel, 0, 0);
        gridPane.add(accountLabel, 1, 0);
        gridPane.setAlignment(Pos.CENTER);
        root.getChildren().addAll(gridPane);

        int WIDTH = 200;
        logoutButton.setMinWidth(WIDTH);
        adminButton.setMinWidth(WIDTH);
        root.getChildren().addAll(adminButton);
        root.getChildren().addAll(logoutButton);
        adminButton.setOnAction(e -> SceneManager.setScene(SceneManager.SceneType.admin));
        logoutButton.setOnAction(e -> logout());
	}
	
	// Send signal to server and retrieve account usernames and types
	public void getAccountList() {
		try {
			Socket connection = SceneManager.getSocket(); // Server socket
	    	PrintWriter outgoing;   // Stream for sending data.
			outgoing = new PrintWriter( connection.getOutputStream() );
			System.out.println("Sending... ACCOUNT_LIST");
			outgoing.println("ACCOUNT_LIST");
			outgoing.flush();

	        BufferedReader incoming = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        System.out.println("Waiting for account list...");
	        String username = incoming.readLine();
	        int row = 1; // Gridepane row index
	        while (!username.equals("DONE")) {
	        	// Add username
	        	Label userLabel2 = new Label(username);
	        	userLabel2.setFont(new Font(FONT_SIZE));
	            gridPane.add(userLabel2, 0, row);

	            // Add account type
		        String type = incoming.readLine();
	            Label accountLabel2 = new Label(type);
	            accountLabel2.setFont(new Font(FONT_SIZE));
	            gridPane.add(accountLabel2, 1, row);

	            // Start reading next account
		        System.out.println("Received " + username + ", " + type); // For debugging
		        row++;
	            username = incoming.readLine();
	        }
		}
        catch (Exception e) {
            System.out.println("Error:  " + e);
        }
	}
}

