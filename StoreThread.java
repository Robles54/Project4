//New Maria & Chris     Project 4
package application;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class StoreThread extends Thread{
	private Socket client;
	//public HashMap<String, Account> accounts;
	public ArrayList<Account> accounts = new ArrayList<Account>();
	public Account userAccount;
	private BufferedReader incoming;
	private PrintWriter outgoing;

	
	public StoreThread(Socket client) {
		this.client = client;
	}
	
	public void run(){
		String clientAddress = client.getInetAddress().toString();
		BufferedReader incoming;
		PrintWriter outgoing;
		String line;
		
		try {
			System.out.println("CONNECTED");
			outgoing = new PrintWriter(client.getOutputStream());
			incoming = new BufferedReader (new InputStreamReader(client.getInputStream()));
			//line = incoming.readLine();
			
//			//other stuff from before
//			listener = new ServerSocket(LISTENING_PORT);
//			System.out.println("Listening on Port: " + LISTENING_PORT);
//			client = listener.accept();
//			System.out.println("Connection from " + client.getInetAddress().toString());
//			incoming = new BufferedReader (new InputStreamReader(client.getInputStream()));
//			outgoing = new PrintWriter (client.getOutputStream());
			System.out.println("Waiting for Request...");
	        String request = incoming.readLine();
	        System.out.println("Request: " + request);
	        while (request != null && !request.equals("QUIT")) {
	            switch (request) {
	                case "LOGIN":
	                    login(StoreServer.accounts, incoming, outgoing);
	                    break;
	                case "ACCOUNT_LIST":
	                    sendAccountList(outgoing);
	                    break;
	                case "PROFILE":
	                    sendProfile(outgoing);
	                    break;
	                case "CHANGE_PASSWORD":
	                    changePassword(userAccount, incoming, outgoing);
	                    break;
	                case "GET_ORDER":
	                    getOrder(incoming);
	                    break;
	                case "VIEW_INVENTORY":
	                    sendInventory(outgoing);
	                    break;
	                case "VIEW_ORDERS":
	                    viewOrders(outgoing);
	                    break;
	                case "QUIT":
	                    System.out.println("Client disconnected.");
	                    return; // Exit the thread
	                default:
	                    outgoing.println("Invalid request.");
	            }
	            outgoing.flush();
	            request = incoming.readLine();
	        }
	    } catch (IOException e) {
	        System.err.println("Error in StoreThread: " + e.getMessage());
	    } finally {
	        try {
	            client.close();
	        } catch (IOException e) {
	            System.err.println("Error closing client connection: " + e.getMessage());
	        }
	    }
		
	}
	public void login(ArrayList<Account> accounts, BufferedReader incoming, PrintWriter outgoing) {
        try {
            String username = incoming.readLine();
            String password = incoming.readLine();
            System.out.println("Received: " + username + ", " + password);
            Account account;
            String reply = "";
    		boolean foundUser = false;
    		for (int i = 0; i < accounts.size(); i++) {
    			if (accounts.get(i).getUsername().equals(username)) {
    				foundUser = true;
    				if (accounts.get(i).verifyPassword(password)) {
    					account = accounts.get(i);
    					if (account instanceof AdminAccount) {
        	    			reply = "ADMIN";
        	                System.out.println("Found admin account");
    					}
    					else {
        	    			reply = "CLIENT";
        	                System.out.println("Found client account");
    					}
    					userAccount = account; // Set current user
    				}
    				else {
    					reply = "ERROR: Invalid password";
    				}
    			}
    		}
    		if (!foundUser)
    			reply = "ERROR: Invalid username";
            System.out.println("Sending reply...");
            outgoing.println(reply);
            outgoing.flush();  // Make sure the data is actually sent!
        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }
    }
	public void sendAccountList(PrintWriter outgoing) {
		for (int i = 0; i < accounts.size(); i++) {
	        outgoing.println(accounts.get(i).getUsername());
			if (accounts.get(i) instanceof AdminAccount)
				outgoing.println("Administrator");
			else
				outgoing.println("Customer");
		}
        outgoing.println("DONE");
        outgoing.flush();
    }
	public void sendProfile(PrintWriter outgoing) {
    	outgoing.println(((CustomerAccount) userAccount).getProfile());
    	outgoing.flush();
    }
	public void changePassword(Account userAccount, BufferedReader incoming, PrintWriter outgoing) {
    	try {
    		String oldPassword = incoming.readLine();
    		String newPassword = incoming.readLine();
    		System.out.println("Received: " + oldPassword + ", " + newPassword);
            String reply = "";
			if (userAccount.verifyPassword(oldPassword)) {
				userAccount.setPassword(newPassword);
				// Save new password
		        try {
		        	PrintWriter file = new PrintWriter("accounts.txt");
		            for (int i = 0; i < accounts.size(); i++) {
						if (accounts.get(i) instanceof AdminAccount)
			            	file.println("admin" + "%" + accounts.get(i).getUsername() + "%" + accounts.get(i).getPassword() + "%");
						else
			            	file.println("client" + "%" + accounts.get(i).getUsername() + "%" + accounts.get(i).getPassword() + "%" + ((CustomerAccount) accounts.get(i)).getProfile());
					}
		            file.close();
		        }
		        catch (IOException e) {
		            System.out.println("Error writing data file.");
		            System.exit(1);
		        }
				// Send reply to client
				if (userAccount instanceof AdminAccount) {
					reply = "ADMIN";
					System.out.println("Found admin account");
				}
				else {
					reply = "CLIENT";
					System.out.println("Found client account");
				}
			}
			else {
				reply = "ERROR: Invalid password";
			}
    		System.out.println("Sending reply...");
    		outgoing.println(reply);
    		outgoing.flush();  // Make sure the data is actually sent!
    	}
    	catch (Exception e){
    		System.out.println("Error: " + e);
    	}
    }
	public void getOrder(BufferedReader incoming) {
		try {
	        int customerId = Integer.parseInt(incoming.readLine());
	        int stockNumber = Integer.parseInt(incoming.readLine());
	        int quantity = Integer.parseInt(incoming.readLine());

	        FileOutputStream fileOut = new FileOutputStream("orders.bin", true); // Append to file
	        DataOutputStream dataOut = new DataOutputStream(fileOut);

	        dataOut.writeByte(customerId);
	        dataOut.writeByte(stockNumber);
	        dataOut.writeByte(quantity);

	        dataOut.close();
	        fileOut.close();

	    } catch (IOException e) {
	        System.err.println("Error writing order to file: " + e.getMessage());
	    }
	}
	public void sendInventory(PrintWriter outgoing) {
		
	}
	public void viewOrders(PrintWriter outgoing) {
		
	}


}