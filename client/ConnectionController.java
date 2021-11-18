package client;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import server.ServerController;

public class ConnectionController {
	
	@FXML
	private TextField addrField, portField;
	
	@FXML
	private Button connectionBtn;
	
	@FXML
	private ProgressBar connectionProgressBar;
	
	private MainController mainController;
	
	protected Client client;
	
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
	
	public void handleConnection(Event event) throws IOException {
		connectionProgressBar.setVisible(true);
		String username = mainController.getUsername();
		String addr = addrField.getText();
		int port;
		try {
			port = Integer.parseInt(portField.getText());
		}
		catch (NumberFormatException ne) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Invalid port!");
			alert.setContentText("Enter a port between 1-65535.");
			alert.showAndWait();
			return;
		}
		
		ClientSocketHandler csh = new ClientSocketHandler(username, addr, port, this);
		Thread cshThread = new Thread(csh);
		cshThread.start();
	}
	
	public Client getClient() {
		return client;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public ProgressBar getProgressBar() {
		return connectionProgressBar;
	}
	
	public void setProgressBar(boolean status) {
		connectionProgressBar.setVisible(status);
	}
}
