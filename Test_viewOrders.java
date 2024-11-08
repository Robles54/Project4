//New Maria Galarza         Project 4
package application;



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.Test;

class Test_viewOrders {

	@Test
	void test() throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(0);
				Socket clientSocket = new Socket("localhost", serverSocket.getLocalPort());
				PrintWriter outgoing = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader incoming = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){
			StoreThread storeThread = new StoreThread(serverSocket.accept());
			storeThread.accounts = AccountsReader.readFile("accounts.xml");
			storeThread.inventory = InventoryReader.readFile("inventory.xml");
			storeThread.userAccount = storeThread.accounts.get("chris"); // Or any existing user

			storeThread.viewOrders(outgoing);

		}
	}
	@Test
	void createTestOrdersFile() throws IOException {
		try (FileOutputStream fileOut = new FileOutputStream("orders.bin");
				DataOutputStream dataOut = new DataOutputStream(fileOut)) {

			dataOut.writeByte(23);
			dataOut.writeByte(45);
			dataOut.writeByte(5);

			dataOut.writeByte(23);
			dataOut.writeByte(51);
			dataOut.writeByte(2);

			dataOut.writeByte(76);
			dataOut.writeByte(69);
			dataOut.writeByte(2);
		}
	}

}
