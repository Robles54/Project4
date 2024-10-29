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

class Test_sendProfile_JUnit {
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

	// Main test for sendProfile: Expects a String which is the required profile
	@Test
	void test() {
		String reply = "";
		try {
			outgoing.println("PROFILE");
			outgoing.flush();
	        reply = incoming.readLine();
		}
        catch (Exception e) {
            System.out.println("Error:  " + e);
        }
	        
        assert reply.equals("Best client ever");
	}

	// Close everything
	@AfterEach
	void shutDown() throws Exception {
		incoming.close();
		outgoing.close();
		client.close();
	}
}
