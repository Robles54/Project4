//New Maria Galarza         Project 4
//package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ServerChat {

	private TextArea chatWindow;
    private TextField messageField;
    private TextField nameField;
    private PrintWriter output;
    private Scanner input;
 /*   
    public ServerChat() {
    	nameField = new TextField();
        nameField.setPromptText("Enter your name");
        Label nameLabel = new Label("Your Name:");

        chatWindow = new TextArea();
        chatWindow.setEditable(false);
        
        messageField = new TextField();
        messageField.setPromptText("Enter message");

        Button sendButton = new Button("Send");
        sendButton.setOnAction(e -> sendMessage());

        HBox inputBox = new HBox(10, messageField, sendButton);
        inputBox.setAlignment(Pos.CENTER);

        VBox topBox = new VBox(10, nameLabel, nameField);
        topBox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(chatWindow);
        root.setBottom(inputBox);
        root.setTop(topBox);

        new Thread(() -> startServer()).start();

	}
    
    private void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(5000)) { // Choose your desired port
            Socket clientSocket = serverSocket.accept();
            input = new Scanner(clientSocket.getInputStream());
            output = new PrintWriter(clientSocket.getOutputStream(), true);

            while (input.hasNextLine()) {
                String clientMessage = input.nextLine();
                chatWindow.appendText("Customer: " + clientMessage + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void sendMessage() {
        String message = messageField.getText();
        String name = nameField.getText();
        if (!message.isEmpty() && !name.isEmpty()) {
            output.println(name + ": " + message);
            chatWindow.appendText(name + ": " + message + "\n");
            messageField.clear();
        }
    }
    */
}
