////Provided
////package application;
//
//import java.io.BufferedReader;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.util.ArrayList;
//
//public class ServerTemplate {
//	private static ArrayList<Account> accounts = new ArrayList<Account>();  // For readAccounts
//	private static Account userAccount;
//    private static BufferedReader incoming;
//    private static PrintWriter outgoing;   // Stream for sending data.
//	
//	public ServerTemplate() {
//		StoreServer.readAccounts(accounts);
//		StoreServer.login(accounts, incoming, outgoing);
//		StoreServer.sendAccountList(outgoing);
//		StoreServer.sendProfile(outgoing);
//		StoreServer.changePassword(userAccount, incoming, outgoing);
//	}
//}