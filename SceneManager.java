// Provided
//package application;


import java.net.Socket;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    private static Socket connection; // Socket connection to server
	private static Stage stage; // Stage used for all scenes
	private static LoginScene loginScene; // For user login
	private static AdminScene adminScene; // Menu for administrator accounts
	private static CustomerScene clientScene; // Menu for client accounts
	private static ChangePasswordScene changePasswordScene; // Form for all users to change password
	private static AccountListScene accountListScene; // Displays accounts (for administrators only)
	private static ProfileScene profileScene; // Displays client profile (for clients only)
	private static SettingsScene settingsScene; // Allows user to change Socket host and port number

	// Constructor
	public SceneManager() {
		loginScene = new LoginScene();
		adminScene = new AdminScene();
		clientScene = new CustomerScene();
		changePasswordScene = new ChangePasswordScene();
		accountListScene = new AccountListScene();
		profileScene = new ProfileScene();
		settingsScene = new SettingsScene();
	}
	
	// Set socket connection to server
	public static void setSocket(Socket setConnection) {
		connection = setConnection;
	}

	// Get socket connection to server
	public static Socket getSocket() {
		return connection;
	}

	// Set initial stage to be used by all scenes
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	// Change view to LoginScene
	public static void setLoginScene() {
		stage.setScene(loginScene.getScene());
	}
	
	// Change view to AdminScene
	public static void setAdminScene() {
		stage.setScene(adminScene.getScene());
	}
	
	// Change view to ClientScene
	public static void setClientScene() {
		stage.setScene(clientScene.getScene());
	}
	
	// Change view to ChangePasswordScene
	public static void setChangePasswordScene() {
		stage.setScene(changePasswordScene.getScene());
	}
	
	// Change view to AccountListScene
	public static void setAccountListScene() {
		accountListScene.getAccountList(); // Make AccountListScene request account list from server
		stage.setScene(accountListScene.getScene());
	}
	
	// Change view to ProfileScene
	public static void setProfileScene() {
		profileScene.getProfile(); // Make ProfileScene request profile from server
		stage.setScene(profileScene.getScene());
	}
	
	// Change view to SettingsScene
	public static void setSettingsScene() {
		stage.setScene(settingsScene.getScene());
	}
}
