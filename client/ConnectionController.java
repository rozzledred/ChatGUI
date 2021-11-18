package client;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import server.ServerController;

public class ConnectionController {
	
	@FXML
	private TextField addrField, portField;
	
	@FXML
	private Button connectionBtn;
	
	private MainController mainController;
	
	protected Client client;
	
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
	
	public void handleConnection(Event event) throws IOException {
		String username = mainController.getUsername();
		String addr = addrField.getText();
		int port = Integer.parseInt(portField.getText());
		Client client = new Client(username, addr, port);
		this.client = client;
		client.sendMessage(username);

		FXMLLoader messageLoader = new FXMLLoader(getClass().getResource("Message.fxml"));
		VBox messageParent = (VBox)messageLoader.load();
		MessageController messageView = messageLoader.getController();
		messageView.setMainController(this);
		messageView.setClient(client);
		
		messageView.setupChatView();
		
		Stage messageStage = new Stage();
		Scene messageScene = new Scene(messageParent);
		
		messageStage.setScene(messageScene);
		messageStage.setTitle("Message");
		messageStage.setResizable(false);
		messageStage.show();
		
		connectionBtn.getScene().getWindow().hide();
	}
	
	public Client getClient() {
		return client;
	}
}
