//New Chris    Project 4
//Allows user to place a new order
//package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane; 
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
import javafx.scene.paint.Color;


public class PlaceOrderScene extends SceneBasic {
	private Button adminButton = new Button("Return to menu");
	private GridPane gridPane = new GridPane();
	private final int FONT_SIZE = 20;
	
	public PlaceOrderScene() {
		super("Ordering");
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
        adminButton.setMinWidth(WIDTH);
        root.getChildren().addAll(adminButton);
        adminButton.setOnAction(e -> SceneManager.setScene(SceneManager.SceneType.admin));
	}

	public void getInventory() {
		// TODO Auto-generated method stub
		
	}

}
