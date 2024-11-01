// Modify Maria & Chris   Project 4
// GUI application class that launches the application.
package application;

import javafx.application.Application;

import javafx.stage.Stage;


import java.io.PrintWriter;
import java.net.Socket;

public class Store extends Application {
	private SceneManager sceneManager; // Contains methods for switching between Scenes
	private Socket connection; // Socket for connection to server

	// Start GUI
    public void start(Stage stage) {
    	sceneManager = new SceneManager();
    	sceneManager.setStage(stage);
		SceneManager.setLoginScene();
        stage.setTitle("Store Application");
        stage.show();
    } // end start();
    
    // When GUI is closed, let server know that we're disconnecting
    public void stop() {
    	try {
            System.out.println("Quitting");
        	Socket connection = sceneManager.getSocket();
        	if (connection == null)
    			connection = new Socket("localhost", 32007);
	    	PrintWriter outgoing;   // Stream for sending data.
			outgoing = new PrintWriter( connection.getOutputStream() );
			outgoing.println("QUIT");
			outgoing.flush();
			connection.close();
    	}
        catch (Exception e) {
            System.out.println("Error:  " + e);
        }
    }

    public static void main(String[] args) {
        launch(args);  // Run this Application.
    }
}