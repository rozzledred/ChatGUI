package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javafx.collections.ObservableList;

public class ClientHandler implements Runnable{

	private final Socket clientSocket;
	private String username;
	private final ServerHandler serverHandler;

	public ClientHandler(Socket socket, ServerHandler serverHandler) {
		this.clientSocket = socket;
		this.serverHandler = serverHandler;
	}

	public void run() {
		try(
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);     
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				) 
		{
			String username = in.readLine();
			this.username = username;
			serverHandler.addToServerLog("Client username is: " + username);
			ClientModel currentClient = new ClientModel(out, username);
			serverHandler.clientList.add(currentClient);
			String fromClient;
			while((fromClient = in.readLine()) != null) {
				String fromClientFinal = fromClient;
				sendToClients(username, fromClientFinal);
				serverHandler.addToServerLog(username + ":" + fromClientFinal);
			}
		}
		catch (SocketException se) {
			se.printStackTrace();
			serverHandler.addToServerLog(username + " disconnected.");
			for(ClientModel cm : serverHandler.clientList)
				if(cm.getUsername().equals(username)) serverHandler.clientList.remove(cm);
			return;
		}
		catch(IOException io) {
			io.printStackTrace();
		}
	}
	
	private void sendToClients(String username, String message) {
		for(ClientModel cm : serverHandler.clientList) {
			PrintWriter pw = cm.getClientOut();
			pw.println(username +":"+ message);
		}
	}
}
