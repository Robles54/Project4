//Modify Maria & Chris        Project 4  
//Menu for customer. Formerly ClientScene. NEW: Add buttons.
package application;

import javafx.scene.control.*;

public class CustomerScene extends SceneBasic {
	Button viewOrderButton = new Button("View Order");
	Button placeOrderButton = new Button("Place Order");
	Button profileButton = new Button("Show profile");
	Button passwordButton = new Button("Change password");
	Button logoutButton = new Button("Logout");
	Button chatButton = new Button("Chat");

	public CustomerScene() {
        super("Customer Menu");
        int WIDTH = 200;
        viewOrderButton.setMinWidth(WIDTH);
        placeOrderButton.setMinWidth(WIDTH);
        profileButton.setMinWidth(WIDTH);
        passwordButton.setMinWidth(WIDTH);
        logoutButton.setMinWidth(WIDTH);
        chatButton.setMinWidth(WIDTH);
        root.getChildren().addAll(viewOrderButton);
        viewOrderButton.setOnAction(e -> SceneManager.setScene(SceneManager.SceneType.viewOrders));
        root.getChildren().addAll(placeOrderButton);
        placeOrderButton.setOnAction(e -> SceneManager.setScene(SceneManager.SceneType.placeOrder));    
        root.getChildren().addAll(profileButton);
        profileButton.setOnAction(e -> SceneManager.setScene(SceneManager.SceneType.profile));
        root.getChildren().addAll(passwordButton);
        passwordButton.setOnAction(e -> SceneManager.setScene(SceneManager.SceneType.changePassword));
        root.getChildren().addAll(logoutButton);
        logoutButton.setOnAction(e -> logout());
        root.getChildren().addAll(chatButton);
       // chatButton.setOnAction(e -> chat());
	}
}
