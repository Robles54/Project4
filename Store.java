// Modify Maria & Chris   Project 4
// GUI application class that launches the application.
//package application;

import javafx.application.Application;

import javafx.stage.Stage;


import java.io.PrintWriter;
import java.net.Socket;

public class Store extends Application {
	private SceneManager sceneManager;
	private Socket connection; 

	// Start GUI
    public void start(Stage stage) {
    	sceneManager = new SceneManager();
    	sceneManager.setStage(stage);
    	SceneManager.setScene(SceneManager.SceneType.login);
        stage.setTitle("Store Application");
        stage.show();
    } // end start();
    

    public void stop() {
    	try {
            System.out.println("Quitting");
        	Socket connection = sceneManager.getSocket();
        	if (connection == null)
    			connection = new Socket("localhost", 32007);
	    	PrintWriter outgoing;  
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