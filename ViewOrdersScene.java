//New Maria Galarza         Project 4
//package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;

import javafx.scene.control.*;

import javafx.scene.layout.GridPane; 


public class ViewOrdersScene extends SceneBasic {
	private Button adminButton = new Button("Return to menu");
	private GridPane gridPane = new GridPane();
	private final int FONT_SIZE = 20;
	
	public ViewOrdersScene() {
		super("Your Order");
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

	public void getOrders() {
		// TODO Auto-generated method stub
		
	}

}
