// Author: Chris Fietkiewicz
import static org.junit.jupiter.api.Assertions.*;

import java.net.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

class Test_sendAccountList_JUnit {
	private static int LISTENING_PORT = 32007;
    private static BufferedReader incoming;
    private static PrintWriter outgoing;   // Stream for sending data.
    private static Socket client;

	// Setup socket and streams for all tests
	@BeforeEach
	void setUp() throws Exception {
		client = new Socket("localhost", LISTENING_PORT);
        incoming = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outgoing = new PrintWriter( client.getOutputStream() );
	}

	// Main test for sendAccountList: Expects usernames and account typess
	@Test
	void test() {
        ArrayList<String> users = new ArrayList<String>();
        ArrayList<String> types = new ArrayList<String>();
		try {
			outgoing.println("ACCOUNT_LIST");
			outgoing.flush();
	        System.out.println("Waiting for account list...");
	        String username = incoming.readLine();
	        while (!username.equals("DONE")) {
	        	System.out.println("Received " + username);
		        users.add(username);
		        String type = incoming.readLine();
	        	System.out.println("Received " + type);
		        types.add(type);
	            username = incoming.readLine();
	        }
        	System.out.println("Done receiving");

		}
        catch (Exception e) {
            System.out.println("Error:  " + e);
        }
	        
        assert users.get(0).equals("admin");
        assert users.get(1).equals("client");
        assert types.get(0).equals("Administrator");
        assert types.get(1).equals("Client");
	}

	// Close everything
	@AfterEach
	void shutDown() throws Exception {
		incoming.close();
		outgoing.close();
		client.close();
	}
}
