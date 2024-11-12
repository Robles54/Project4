//New Maria & Chris     Project 4
package application;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class StoreThread extends Thread{
	private Socket client;
	public HashMap<String, Account> accounts;
	public HashMap<String, String> inventory;
	public Account userAccount;
	private BufferedReader incoming;
	private PrintWriter outgoing;

	
	public StoreThread(Socket client) {
		this.client = client;
		accounts = AccountsReader.readFile("accounts.xml");
		inventory = InventoryReader.readFile("inventory.xml");
		System.out.println("Accounts HashMap: " + accounts);
	}
	
	public void run(){	
		try {
			System.out.println("CONNECTED");
			outgoing = new PrintWriter(client.getOutputStream());
			incoming = new BufferedReader (new InputStreamReader(client.getInputStream()));

			System.out.println("Waiting for Request...");
	        String request = incoming.readLine();
	        System.out.println("Request: " + request);
	        while (request != null && !request.equals("QUIT")) {
	            switch (request) {
	                case "LOGIN":
	                    login( accounts, incoming, outgoing);
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
	public void login(HashMap<String, Account> accounts, BufferedReader incoming, PrintWriter outgoing) {
        try {
            String username = incoming.readLine();
            String password = incoming.readLine();
            System.out.println("Received: " + username + ", " + password);
            Account account = accounts.get(username);
            String reply = "";
            if (account != null) { 
                if (account.verifyPassword(password)) {
                    if (account instanceof AdminAccount) {
                        reply = "ADMIN";
                        System.out.println("Found admin account");
                    } else {
                        reply = "CLIENT";
                        System.out.println("Found client account");
                    }
                    userAccount = account; 
                } else {
                    reply = "ERROR: Invalid password";
                }
            } else {
                reply = "ERROR: Invalid username";
            }

            System.out.println("Sending reply...");
            outgoing.println(reply);
            outgoing.flush();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
	public void sendAccountList(PrintWriter outgoing) {
		for (Account account : accounts.values()) { 
	        outgoing.println(account.getUsername());
	        if (account instanceof AdminAccount) {
	            outgoing.println("Admin");
	        } else {
	            outgoing.println("Customer");
	        }
	    }
	    outgoing.println("DONE");
	    outgoing.flush(); 

	}
	
	public void sendProfile(PrintWriter outgoing) {
    	outgoing.println(((CustomerAccount) userAccount).getProfile());
    	outgoing.flush();
    }

	public static void changePassword(Account userAccount, BufferedReader incoming, PrintWriter outgoing) {
    	try {
    		String oldPassword = incoming.readLine();
    		String newPassword = incoming.readLine();
    		System.out.println("Received: " + oldPassword + ", " + newPassword);
            String reply = "";
			if (userAccount.verifyPassword(oldPassword)) {
				userAccount.setPassword(newPassword);
				// Save new password
				try {
	                Path path = Paths.get("accounts.xml");
	                List<String> lines = Files.readAllLines(path);

	                for (int i = 0; i < lines.size(); i++) {
	                    String line = lines.get(i).trim(); // Trim whitespace for consistency
	                    if (line.contains("<username>" + userAccount.getUsername() + "</username>")) {
	                        String passwordLine = lines.get(i + 1).trim(); 
	                        
	                        // Extract the old password from passwordLine
	                        int start = passwordLine.indexOf("<password>") + "<password>".length();
	                        int end = passwordLine.indexOf("</password>");
	                        String oldPasswordInXml = passwordLine.substring(start, end); 

	                        passwordLine = passwordLine.replace(
	                                "<password>" + oldPasswordInXml + "</password>", // Use oldPasswordInXml
	                                "<password>" + newPassword + "</password>"
	                        );
	                        lines.set(i + 1, passwordLine); 
	                        break; 
	                    }
	                }

	                Files.write(path, lines);

	            } catch (IOException e) {
	                System.out.println("Error updating XML file: " + e);
	            }
				
				if (userAccount instanceof AdminAccount) {
					reply = "ADMINISTRATOR";
					System.out.println("Found admin account");
				} else {
					reply = "CUSTOMER";
					System.out.println("Found client account");
				}
			} else {
				reply = "ERROR: Invalid password";
			}
    		System.out.println("Sending reply...");
    		outgoing.println(reply);
    		outgoing.flush();
    	} catch (Exception e){
    		System.out.println("Error: " + e);
    	}
    }
	
	public void getOrder(BufferedReader incoming) {
		try {
	        int customerId = Integer.parseInt(incoming.readLine());
	        int stockNumber = Integer.parseInt(incoming.readLine());
	        int quantity = Integer.parseInt(incoming.readLine());

	        FileOutputStream fileOut = new FileOutputStream("orders.bin", true);
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
		try {
			for (HashMap.Entry<String, String> entry: inventory.entrySet()) {
				String stockNumber = entry.getKey();
				String description = entry.getValue();
				
				outgoing.println(stockNumber + "," + description);
			}
			
			outgoing.println("Done!");
			outgoing.flush();
			
			System.out.println("Inventory sent successfully.");
		}
		catch (Exception e) {
			System.out.println("Error sending the inventory: " + e);
		}
		
	}
	
	public void viewOrders(PrintWriter outgoing) {
		try {
			System.out.println("Inventory: " + inventory);
	        FileInputStream fileIn = new FileInputStream("orders.bin");
	        DataInputStream dataIn = new DataInputStream(fileIn);

	        while (dataIn.available() > 0) {
	            int customerId = dataIn.readByte();
	            int stockNumber = dataIn.readByte();
	            int quantity = dataIn.readByte();
	            
	            System.out.println("Order: " + customerId + ", " + stockNumber + ", " + quantity);

	            String description = inventory.get(String.valueOf(stockNumber));
	            if (description == null) {
	                description = "Unknown Product";
	            }

	            if (customerId == userAccount.getID()) { 
	            	outgoing.println(stockNumber + "," + description + "," + quantity);
	            }
	        }

	        dataIn.close();
	        fileIn.close();

	        outgoing.println("DONE");
	        outgoing.flush();

	    } catch (IOException e) {
	        System.err.println("Error reading orders from file: " + e.getMessage());
	    }
	}
}