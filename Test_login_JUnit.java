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

class Test_login_JUnit {
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

	// Test good (successful) Admin login
	@Test
	void goodAdmin() {
		String reply = "";
		try {
			outgoing.println("admin");
			outgoing.println("password");
			outgoing.flush();
	        reply = incoming.readLine();
		}
        catch (Exception e) {
            System.out.println("Error:  " + e);
        }
	        
        assert reply.equals("ADMIN");
	}

	// Test good (successful) Client login
	@Test
	void goodClient() {
		String reply = "";
		try {
			outgoing.println("client123");
			outgoing.println("neverguess");
			outgoing.flush();
			reply = incoming.readLine();
		}
		catch (Exception e) {
			System.out.println("Error:  " + e);
		}
		
		assert reply.equals("CLIENT");
	}
	
	// Test bad username
	@Test
	void badUsername() {
		String reply = "";
		try {
			outgoing.println("BAD_USERNAME");
			outgoing.println("neverguess");
			outgoing.flush();
			reply = incoming.readLine();
		}
		catch (Exception e) {
			System.out.println("Error:  " + e);
		}
		
		assert reply.equals("ERROR: Invalid username");
	}
	
	// Test bad password
	@Test
	void badPassword() {
		String reply = "";
		try {
			outgoing.println("client123");
			outgoing.println("BAD_PASSWORD");
			outgoing.flush();
			reply = incoming.readLine();
//			System.out.println("reply = " + reply);
		}
		catch (Exception e) {
			System.out.println("Error:  " + e);
		}
		
		assert reply.equals("ERROR: Invalid password");
	}
	
	// Close everything
	@AfterEach
	void shutDown() throws Exception {
		incoming.close();
		outgoing.close();
		client.close();
	}
}
