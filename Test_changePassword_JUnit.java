// Author: Chris Fietkiewicz
import static org.junit.jupiter.api.Assertions.*;

import java.net.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class Test_changePassword_JUnit {
	private static int LISTENING_PORT = 32007;
    private static BufferedReader incoming;   // Stream for reading data.
    private static PrintWriter outgoing;   // Stream for sending data.
    private static Socket client;

	// Setup socket and streams for all tests
	@BeforeEach
	void setUp() throws Exception {
		client = new Socket("localhost", LISTENING_PORT);
        incoming = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outgoing = new PrintWriter( client.getOutputStream() );
	}

	// Must test all three possibilities in one test because each test requires a specific account setup
	@Test
	void goodAdmin() {
		String reply1 = "", reply2 = "", reply3 = "";
		
		try {
			// Test successful change for admin password
			outgoing.println("password");
			outgoing.println("test");
			outgoing.flush();
	        reply1 = incoming.readLine();
	    	// Test successful change for client password
			outgoing.println("neverguess");
			outgoing.println("test");
			outgoing.flush();
	        reply2 = incoming.readLine();
	    	// Test unsuccessful change for client password
			outgoing.println("BAD_PASSWORD");
			outgoing.println("DOESN'T_MATTER");
			outgoing.flush();
	        reply3 = incoming.readLine();
		}
        catch (Exception e) {
            System.out.println("Error:  " + e);
        }
	        
        assert reply1.equals("ADMIN") && reply2.equals("CLIENT") && reply3.equals("ERROR: Invalid password");
	}

	// Close everything
	@AfterEach
	void shutDown() throws Exception {
		incoming.close();
		outgoing.close();
		client.close();
	}
}
