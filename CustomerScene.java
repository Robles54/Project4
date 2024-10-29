//Modify Maria & Chris        Project 4  
//Menu for customer. Formerly ClientScene. NEW: Add buttons.
package application;

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

public class CustomerScene extends SceneBasic {
	Button logoutButton = new Button("Logout");
	Button passwordButton = new Button("Change password");
	Button profileButton = new Button("Show profile");

	public CustomerScene() {
        super("Customer Menu");
        int WIDTH = 200;
        logoutButton.setMinWidth(WIDTH);
        passwordButton.setMinWidth(WIDTH);
        profileButton.setMinWidth(WIDTH);
        root.getChildren().addAll(profileButton);
        profileButton.setOnAction(e -> SceneManager.setProfileScene());
        root.getChildren().addAll(passwordButton);
        passwordButton.setOnAction(e -> SceneManager.setChangePasswordScene());
        root.getChildren().addAll(logoutButton);
        logoutButton.setOnAction(e -> logout());
	}
}
