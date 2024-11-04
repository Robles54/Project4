//New Chris          Project 4
//JUnit test for readFile method in InventoryReader.
//package application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

class Test_InventoryReader {

	@Test
	public void testReadFile() {
		InventoryReader reader = new InventoryReader();
		HashMap<String, String> inventory = reader.readFile("inventory.xml");
		
		assertEquals("This is Product #1!", inventory.get("45"));
		assertEquals("This is Product #2!", inventory.get("51"));
		assertEquals("This is Product #3!", inventory.get("69"));
	}

}
