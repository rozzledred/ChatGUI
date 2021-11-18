package server;

import java.io.PrintWriter;

public class ClientModel {
	
	private PrintWriter clientOut;
	private String username;
	
	public ClientModel(PrintWriter clientOut, String username) {
		this.clientOut = clientOut;
		this.username = username;
	}
	
	public PrintWriter getClientOut() {
		return clientOut;
	}
	
	public String getUsername() {
		return username;
	}
}
