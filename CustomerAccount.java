// Modify Chris               Project 4   
// Subclass for customer account. New: variable “id”.
//package application;

public class CustomerAccount extends Account {
	private String profile;
	
	public CustomerAccount(int id, String username, String password, String profile) {
		super(id, username, password);
		this.profile = profile;
	}

	// Get profile string
	public String getProfile() {
		return profile;
	}
	
}