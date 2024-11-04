//Provided
//package application;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class ServerTemplate {
	private static Account userAccount;
	private static BufferedReader incoming;
	private static PrintWriter outgoing;   // Stream for sending data.

	public ServerTemplate() {
		HashMap<String, Account> accounts = AccountsReader.readFile("accounts.xml");
		HashMap<String, String> inventory = InventoryReader.readFile("inventory.xml");
		Socket client = new Socket(); // Needed for StoreThread (does not connect)
        StoreThread worker = new StoreThread(client);
        worker.login(accounts, incoming, outgoing);
        worker.sendAccountList(outgoing);
        worker.sendProfile(outgoing);
        worker.changePassword(userAccount, incoming, outgoing);
        worker.getOrder(incoming);
        worker.sendInventory(outgoing);
        worker.viewOrders(outgoing);
	}
}
