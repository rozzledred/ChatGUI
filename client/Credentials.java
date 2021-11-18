package client;

public class Credentials {
	
	private String username;
	private int password;
	
	public Credentials(String username, String password) {
		this.username = username;
		this.password = password.hashCode();
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getPassword() {
		return password;
	}
	
	@Override
	public boolean equals(Object o) {
		Credentials newCredentials = (Credentials) o;
		if(newCredentials.getUsername().equals(username) 
				&& newCredentials.getPassword() == password) return true;
		return false;
	}
}
