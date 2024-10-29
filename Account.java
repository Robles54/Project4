//Modify Maria & Chris        Project 4      
//(Superclass for an account. Similar to Project #3. New: variable “id”.)

public abstract class Account {
	private String username;
	private String password;
	private int id;
	
	//NEW id
	public int getID(){
		return id;
	}

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
	public Account(int id, String username, String password) {
		this.username = username;
		this.password = password;
	}
}
