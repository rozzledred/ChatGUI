package client;
import java.io.*;
import java.net.*;

public class Client {
	private String username;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	public Client(String username, String addr, int port) {
		this.username = username;
		try {
			socket = new Socket(addr, port);
			System.out.println("Successfully connected!");
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String message) {
		out.println(message);
	}
	
	public BufferedReader getInputStream() {
		return in;
	}
	
	public String toString() {
		return username;
	}
	
}
