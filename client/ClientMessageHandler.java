package client;

import java.io.BufferedReader;
import java.io.IOException;

import javafx.application.Platform;
import javafx.collections.ObservableList;

public class ClientMessageHandler implements Runnable {
	
	private final BufferedReader in;
	private final ObservableList<String> serverMessageList;
	public ClientMessageHandler(BufferedReader in, ObservableList<String> serverMessageList) {
		this.in = in;
		this.serverMessageList = serverMessageList;
	}
	
	@Override
	public void run() {
		try {
			String fromServer;
			while((fromServer = in.readLine()) != null) {
				String fromServerFinal = fromServer;
				addToMessageList(fromServerFinal);
			}
		} 
		catch(IOException io) {
			return;
		} 
		finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
	}
	
	private void addToMessageList(String message) {
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				serverMessageList.add(message);
			}
			});
	}

}
