// Author: Chris Fietkiewicz
public abstract class Account {
	private String username;
	private String password;

	// Get username
	public String getUsername() {
		return username;
	}

	// Compare String to password
	public boolean verifyPassword(String password) {
		return this.password.equals(password);
	}

	// Set password
	public void setPassword(String password) {
		this.password = password;
	}

	// Get password
	public String getPassword() {
		return password;
	}

	// String representation of account
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "username: " + username + ", " + this.getClass();
	}
	
	// Constructor
	public Account(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
