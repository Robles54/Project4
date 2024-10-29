// Author: Chris Fietkiewicz
import java.util.ArrayList;

public class AdminAccount extends Account {
	private ArrayList<Account> accounts;
	
	public AdminAccount(String username, String password, ArrayList<Account> accounts) {
		super(username, password);
		this.accounts = accounts;
	}
}
