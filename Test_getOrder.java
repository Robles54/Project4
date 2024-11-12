//New Chris         Project 4
//JUnit test for getOrder method in StoreThread.
//package application;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.net.Socket;

class Test_getOrder {

    private StoreThread storeThread;
    private String ordersFilePath = "orders.bin";

    @BeforeEach
    public void setUp() {
        // Initialize StoreThread with a mock socket (or null if not needed)
        storeThread = new StoreThread((Socket) null);
    }

    @AfterEach
    public void tearDown() {
        // Cleanup orders.bin after each test
        File ordersFile = new File(ordersFilePath);
        if (ordersFile.exists()) {
            ordersFile.delete();
        }
    }

    @Test
    void testGetOrder() {
        // Simulate input for getOrder: customerId=1, stockNumber=10, quantity=5
        String simulatedInput = "1\n10\n5\n";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        BufferedReader mockIncoming = new BufferedReader(new InputStreamReader(byteArrayInputStream));

        // Call getOrder with the simulated BufferedReader input
        storeThread.getOrder(mockIncoming);

        // Read the orders.bin file to verify the written data
        try (FileInputStream fileIn = new FileInputStream(ordersFilePath)) {
            int customerId = fileIn.read();
            int stockNumber = fileIn.read();
            int quantity = fileIn.read();

            // Verify the content of orders.bin matches the expected values
            assertEquals(1, customerId);      // Customer ID
            assertEquals(10, stockNumber);    // Stock Number
            assertEquals(5, quantity);        // Quantity

            // Ensure no extra bytes are present
            assertEquals(-1, fileIn.read());
        } catch (IOException e) {
            fail("Failed to read from orders.bin file: " + e.getMessage());
        }
    }
}
