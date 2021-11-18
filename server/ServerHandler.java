package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javafx.application.Platform;
import javafx.collections.ObservableList;

public class ServerHandler implements Runnable {

	private ServerSocket serverSocket;
	private final ObservableList<String> serverLogList;
	protected Collection<ClientModel> clientList = 
			Collections.synchronizedCollection(new ArrayList<ClientModel>());

	public ServerHandler(int port, ServerController serverController) {
		this.serverLogList = serverController.serverLogList;
		try {
			ServerSocket serverSocket = null;
			serverSocket = new ServerSocket(port);
			serverSocket.setReuseAddress(true);
			serverLogList.add("Server started on port " + port + "...");
			this.serverSocket = serverSocket;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			while(true) {
				Socket client = serverSocket.accept();
				addToServerLog("Client connected!");
				ClientHandler clientHandler = new ClientHandler(client, this);
				new Thread(clientHandler).start();
			}
		}
		catch (BindException be) {
			addToServerLog("Port already in use!");
			return;
		}
		catch (IOException io) {
			io.printStackTrace();
			System.out.println("IOException");
			return;
		}
		finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
				}
				catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}
	
	public void addToServerLog(String message) {
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				serverLogList.add(message);
			}
			});
	}
}
