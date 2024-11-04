//Modify Chris         Project 4
//Allows user to change socket settings for host and port. NEW: Add “Chat” button
//package application;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane; 
import javafx.geometry.Insets; 
import javafx.scene.paint.Color;
import java.net.*;

public class SettingsScene extends SceneBasic {
	private Label hostText = new Label("Host");
	private TextField hostField = new TextField("localhost");
	private Label portText = new Label("Port");
	private TextField portField = new TextField("32007"); // FOR TESTING
	private Button applyButton = new Button("Apply");
	private Button cancelButton = new Button("Cancel");
	private Label errorMessage = new Label();

	public SettingsScene() {
        super("Connection Settings");
        //Creating Grid Pane 
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(400, 200); 
        gridPane.setPadding(new Insets(10, 10, 10, 10)); 
        gridPane.setVgap(5); 
        gridPane.setHgap(5);       
        gridPane.add(hostText, 0, 0);
        gridPane.add(hostField, 1, 0);
        gridPane.add(portText, 0,1);
        gridPane.add(portField, 1, 1);
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(applyButton, cancelButton);
        gridPane.add(buttonBox, 1, 2);
        errorMessage.setTextFill(Color.RED);
        gridPane.add(errorMessage, 1, 3);
        gridPane.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(gridPane);
        applyButton.setOnAction(e -> apply());
        cancelButton.setOnAction(e -> cancel());
	}
	
	// Apply settings and attempt a connection
	private void apply() {
		String host = hostField.getText();
		String port = portField.getText();
		try {
			Socket connection = new Socket(host, Integer.parseInt(port));
			SceneManager.setSocket(connection); // Client socket
			System.out.println("Connection = " + connection); // For debugging
        	errorMessage.setText(""); // Clear any previous error messages
        	SceneManager.setScene(SceneManager.SceneType.login);
        }
        catch (Exception e) {
        	errorMessage.setText("Error trying to connect to server.");
            System.out.println("Error:  " + e);
        }
	}
	
	// Cancel settings change and return to login scene
	private void cancel() {
		errorMessage.setText(""); // Clear any previous error messages
		SceneManager.setScene(SceneManager.SceneType.login);
	}
}