// Author: Chris Fietkiewicz
import java.util.ArrayList;

public class CustomerAccount extends Account {
	private String profile;
	
	public CustomerAccount(String username, String password, String profile) {
		super(username, password);
		this.profile = profile;
	}

	// Get profile string
	public String getProfile() {
		return profile;
	}
	
}
