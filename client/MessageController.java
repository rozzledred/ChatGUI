package client;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import server.ServerController;

public class MessageController {
	
	@FXML
	private TextField messageTextField;
	
	@FXML
	private ListView<String> messageView;
	
	@FXML
	private Button sendBtn;
	
	private Client client;
	
	private ConnectionController connectionController;
	
	private ObservableList<String> serverMessageList = FXCollections.observableArrayList();
	
	@FXML
	private void handleSend(Event event) {
		String message = messageTextField.getText();
		client.sendMessage(message);
		messageTextField.clear();
	}
	
	@FXML
	private void handleSendEnter(KeyEvent keyEvent) {
		String message = messageTextField.getText();
		client.sendMessage(message);
		messageTextField.clear();
	}

	public void setMainController(ConnectionController connectionController) {
		this.connectionController = connectionController;
	}
	
	public void setupChatView() {
		messageView.setItems(serverMessageList);
		this.client = connectionController.getClient();
		BufferedReader clientInputStream = client.getInputStream();
		ClientMessageHandler cm = new ClientMessageHandler(clientInputStream, serverMessageList);
		new Thread(cm).start();
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public Client getClient() {
		return this.client;
	}
}
