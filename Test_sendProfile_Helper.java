// Author: Chris Fietkiewicz
// Creates ServerSocket for JUnit test for sendAccountList method in StoreServer.
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Test_sendProfile_Helper {
    public static void main(String[] args) {
		int LISTENING_PORT = 32007;
		ServerSocket listener;  // Listens for incoming connections.
        Socket client;      // For communication with the connecting program.
        try {
	        listener = new ServerSocket(LISTENING_PORT);
            System.out.println("Listening on port " + LISTENING_PORT);
           	client = listener.accept(); 
    		PrintWriter outgoing = new PrintWriter(client.getOutputStream());
    		StoreServer.userAccount = new CustomerAccount("client", "neverguess", "Best client ever");
    		StoreServer.sendProfile(outgoing);
    		client.close();
    		listener.close();
        }
        catch (Exception e) {
            System.out.println("Error:  " + e);
            return;
        }
    }
}
