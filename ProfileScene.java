// From  Project #3
//package application;

import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane; 
import javafx.geometry.Insets;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import javafx.scene.paint.Color;

public class ProfileScene extends SceneBasic {
	Button clientButton = new Button("Client Menu");
	Button logoutButton = new Button("Logout");
	Label profile = new Label("temp");

	public ProfileScene() {
        super("User Profile");
		final int FONT_SIZE = 20;
        profile.setFont(new Font(FONT_SIZE));
        profile.setAlignment(Pos.CENTER);
        root.getChildren().addAll(profile);
        int WIDTH = 200;
        logoutButton.setMinWidth(WIDTH);
        clientButton.setMinWidth(WIDTH);
        root.getChildren().addAll(clientButton);
        root.getChildren().addAll(logoutButton);
        clientButton.setOnAction(e -> SceneManager.setClientScene());
        logoutButton.setOnAction(e -> logout());
	}

	// Send signal to server and retrieve profile
	public void getProfile() {
		try {
			Socket connection = SceneManager.getSocket(); // Server socket
	    	PrintWriter outgoing;   // Stream for sending data.
			outgoing = new PrintWriter( connection.getOutputStream() );
			System.out.println("Sending... PROFILE");
			outgoing.println("PROFILE");
			outgoing.flush();

	        BufferedReader incoming = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        System.out.println("Waiting for profile..."); // For debugging
	        String profileText = incoming.readLine();
	        profile.setText(profileText);
		}
        catch (Exception e) {
            System.out.println("Error:  " + e);
        }
	}
}
