package client;
import java.io.*;
import java.net.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Client {
	private String username;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	public Client(String username, String addr, int port) throws UnknownHostException, IOException {
		this.username = username;
		socket = new Socket(addr, port);
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
