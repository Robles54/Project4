//New Maria Galarza         Project 4
//JUnit test for readFile method in AccountsReader
package application;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

class Test_AccountsReader {

	@Test
	void test() {
		HashMap<String, Account> accounts = AccountsReader.readFile("accounts.xml");
		
        assertNotNull(accounts);

        assertEquals(3, accounts.size());

        Account chris = accounts.get("chris");
        assertEquals("chris", chris.getUsername());
        assertEquals("123", chris.getPassword());
        assertEquals(23, chris.getID());

        Account chris2 = accounts.get("chris2");
        assertEquals("chris2", chris2.getUsername());
        assertEquals("123", chris2.getPassword());
        assertEquals(36, chris2.getID());

        Account maria = accounts.get("maria");
        assertEquals("maria", maria.getUsername());
        assertEquals("123", maria.getPassword());
        assertEquals(76, maria.getID());
    }
}