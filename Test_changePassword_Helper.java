// Author: Chris Fietkiewicz
// Creates ServerSocket for JUnit test for login method in StoreServer.
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Test_changePassword_Helper {
    public static void main(String[] args) {
		ArrayList<Account> accounts = new ArrayList<Account>();
		accounts.add(new AdminAccount("admin", "password", accounts));
		accounts.add(new CustomerAccount("client", "neverguess", "Best client ever."));
		accounts.add(new CustomerAccount("client", "neverguess", "Best client ever."));
		int LISTENING_PORT = 32007;
		ServerSocket listener;  // Listens for incoming connections.
        Socket client;      // For communication with the connecting program.
        try {
	        listener = new ServerSocket(LISTENING_PORT);
            System.out.println("Listening on port " + LISTENING_PORT);
           	client = listener.accept(); 
    		BufferedReader incoming = new BufferedReader(new InputStreamReader(client.getInputStream()));
    		PrintWriter outgoing = new PrintWriter(client.getOutputStream());
	        for (int i = 0; i < 3; i++) { // Planning to run 3 tests
	    		StoreServer.changePassword(accounts.get(i), incoming, outgoing);
	        }
    		client.close();
    		listener.close();
        }
        catch (Exception e) {
            System.out.println("Error:  " + e);
            return;
        }
    }
}
