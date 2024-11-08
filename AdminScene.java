// From  Project #3
package application;

import javafx.scene.control.*;

import java.net.Socket;

public class AdminScene extends SceneBasic {
	private Socket connection;
	Button logoutButton = new Button("Logout");
	Button passwordButton = new Button("Change password");
	Button listButton = new Button("List accounts");

	public AdminScene() {
        super("Administrator Menu");
        int WIDTH = 200;
        logoutButton.setMinWidth(WIDTH);
        passwordButton.setMinWidth(WIDTH);
        listButton.setMinWidth(WIDTH);
        root.getChildren().addAll(listButton);
        listButton.setOnAction(e -> SceneManager.setScene(SceneManager.SceneType.accountList));
        root.getChildren().addAll(passwordButton);
        passwordButton.setOnAction(e -> SceneManager.setScene(SceneManager.SceneType.changePassword));
        root.getChildren().addAll(logoutButton);
        logoutButton.setOnAction(e -> logout()); 
	}
}
