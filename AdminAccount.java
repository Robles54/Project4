// Modify Maria Galarza          Project 4      
// Subclass for administrator account. New: variable “id”.
package application;

import java.util.ArrayList;

public class AdminAccount extends Account {
	private ArrayList<Account> accounts;
	
	public AdminAccount(int id, String username, String password, ArrayList<Account> accounts) {
		super(id,username, password);
		this.accounts = accounts;
		
	}
}
