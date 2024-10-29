// Author: Chris Fietkiewicz
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Test_readAccounts_JUnit {

	// Test Admin username
	@Test
	void testAdminUsername() {
		ArrayList<Account> accounts = new ArrayList<Account>();
		StoreServer.readAccounts(accounts);
		String s = accounts.get(0).getUsername();
		assert s.equals("admin");
	}

	// Test Admin password
	@Test
	void testAdminPassword() {
		ArrayList<Account> accounts = new ArrayList<Account>();
		StoreServer.readAccounts(accounts);
		String s = accounts.get(0).getPassword();
		assert s.equals("password");
	}

	// Test Client username
	@Test
	void testClientUsername() {
		ArrayList<Account> accounts = new ArrayList<Account>();
		StoreServer.readAccounts(accounts);
		String s = accounts.get(1).getUsername();
		assert s.equals("c");
	}

	// Test Client password
	@Test
	void testClientPassword() {
		ArrayList<Account> accounts = new ArrayList<Account>();
		StoreServer.readAccounts(accounts);
		String s = accounts.get(1).getPassword();
		assert s.equals("p");
	}

	// Test Client profile
	@Test
	void testClientProfile() {
		ArrayList<Account> accounts = new ArrayList<Account>();
		StoreServer.readAccounts(accounts);
		String s = ((CustomerAccount) accounts.get(1)).getProfile();
		assert s.equals("Best client ever");
	}
	
}
