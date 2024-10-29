//Modify Maria & chris    Project 4 
//Application class that controls ServerSocket.
//package application;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.*;

public class StoreServer {

	private static int LISTENING_PORT = 32007;
	public static ArrayList<Account> accounts = new ArrayList<Account>(); // NOTE: Changed to "public" as an easy, last-minute solution to testing sendAccountList()
	public static Account userAccount; // NOTE: Changed to "public" as an easy, last-minute solution to testing sendProfile()
	private static BufferedReader incoming;
	private static PrintWriter outgoing;   // Stream for sending data.

	public static void main(String[] args) {
		ServerSocket listener;  // Listens for incoming connections.
		Socket client;      // For communication with the connecting program.
		if (args.length == 1) // Allow for different port number on command line
			LISTENING_PORT = Integer.parseInt(args[0]);
		else if (args.length > 1 )
			System.out.println("Usage:  java StoreServer <listening-port>");
		// Create listener socket and begin listening
		//readAccounts(accounts);
		

		try {
			listener = new ServerSocket(LISTENING_PORT);
			System.out.println("Listening on port " + LISTENING_PORT);
			// Client loop
			while (true) {
				client = listener.accept();
//				String request = incoming.readLine();
//				incoming = new BufferedReader(new InputStreamReader(client.getInputStream()));
//				outgoing = new PrintWriter(client.getOutputStream());
//				System.out.println("Waiting for request...");
				StoreThread handler = new StoreThread(client);
				handler.start();
			}
			//                incoming = new BufferedReader(new InputStreamReader(client.getInputStream()));
			//                outgoing = new PrintWriter( client.getOutputStream() );
			//                System.out.println("Waiting for request...");
			//                String request = incoming.readLine();
			//                System.out.println("Request: " + request);
			//                System.out.println("Quitting"); // For debugging
			//client.close();
		}
		catch (Exception e) {
			System.out.println("Sorry, the server has shut down.");
			System.out.println("Error:  " + e);
			return;
		}

	}  // end main()

} //end class
