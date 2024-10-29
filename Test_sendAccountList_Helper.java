// Author: Chris Fietkiewicz
// Creates ServerSocket for JUnit test for sendAccountList method in StoreServer.
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Test_sendAccountList_Helper {
    public static void main(String[] args) {
		ArrayList<Account> accounts = new ArrayList<Account>();
		accounts.add(new AdminAccount("admin", "password", accounts));
		accounts.add(new CustomerAccount("client", "neverguess", "Best client ever."));
		int LISTENING_PORT = 32007;
		ServerSocket listener;  // Listens for incoming connections.
        Socket client;      // For communication with the connecting program.
        try {
	        listener = new ServerSocket(LISTENING_PORT);
            System.out.println("Listening on port " + LISTENING_PORT);
           	client = listener.accept(); 
    		PrintWriter outgoing = new PrintWriter(client.getOutputStream());
    		StoreServer.accounts = accounts;
    		StoreServer.sendAccountList(outgoing);
    		client.close();
    		listener.close();
        }
        catch (Exception e) {
            System.out.println("Error:  " + e);
            return;
        }
    }
}
