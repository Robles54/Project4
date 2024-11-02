// New Chris           Project 4 
// Utility program for reading XML file with inventory data
//package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.*;
import java.io.*;

public class InventoryReader {
	public static HashMap<String, String> readFile(String filename){
		HashMap<String, String> inventory = new HashMap<>();
//		Scanner data;
//		ArrayList<Inventory> inventory;
//		inventory = new ArrayList<Inventory>();
		
		try {
			File file = new File(filename);
			Scanner scanner = new Scanner(file);
			
			String stockNumber = null;
			String description = null;
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();
				
				if (line.startsWith("<stockNumber>")) {
					stockNumber = line.replace("<stockNumber>", "").replace("</stockNumber>", "");
				}
				else if (line.startsWith("Description")) {
					description = line.replace("<Description>", "").replace("</Description>", "");
				}
				
				//adding to HashMap if both stockNumber and description are set
				if (stockNumber != null && description != null) {
					inventory.put(stockNumber, description);
					stockNumber = null;
					description = null;
					
				}
			}
			scanner.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Can't find file!");
		}
		
		return inventory;

	}

}