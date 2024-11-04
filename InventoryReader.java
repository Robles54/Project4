// New Chris           Project 4 
// Utility program for reading XML file with inventory data
//package application;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import java.io.*;

public class InventoryReader {
	public static HashMap<String, String> readFile(String filename){
		HashMap<String, String> inventory = new HashMap<>();
		Scanner data;
		
		try {
			data = new Scanner(new File(filename));
		}
		catch (FileNotFoundException e) {
			System.out.println("Can't find file!");
			return null;
		}
		
		String stockNumber = null;
		String description = null;
		
		
		while (data.hasNextLine()) {
			String line = data.nextLine().trim();
			
			if (line.equals("<PRODUCT>")) {
				stockNumber = null;
				description = null;
				
				while (!line.equals("</PRODUCT>")) {
					line = data.nextLine().trim();
					
					if (line.equals("<stockNumber>")) {
						stockNumber = data.nextLine().trim();
						data.nextLine();
					}
					else if (line.equals("<Description>")) {
						description = data.nextLine().trim();
						data.nextLine();
					}
				}
				
				if (stockNumber != null && description != null) {
					inventory.put(stockNumber, description);
				}
			}
		}
		
		data.close();
		return inventory;

	}

}