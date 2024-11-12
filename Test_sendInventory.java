// New Chris        Project 4 
// JUnit test for sendInventory method in StoreThread.
//package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Test_sendInventory {
    
    private StoreThread storeThread;
    private StringWriter outputWriter;
    private PrintWriter outgoing;

    @BeforeEach
    public void setUp() {
        HashMap<String, String> mockInventory = new HashMap<>();
        mockInventory.put("001", "Widget A");
        mockInventory.put("002", "Widget B");

        storeThread = new StoreThread(null);
        storeThread.inventory = mockInventory;
        
        outputWriter = new StringWriter();
        outgoing = new PrintWriter(outputWriter);
    }

    @Test
    public void testSendInventory() {
        storeThread.sendInventory(outgoing);

        outgoing.flush();

        String expectedOutput = "001,Widget A\n002,Widget B\nDone!\n";

        assertEquals(expectedOutput, outputWriter.toString());
    }
}
