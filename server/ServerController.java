package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ServerController {

	@FXML
	private TextField serverPortField;

	@FXML
	private Button startServerBtn;

	@FXML
	private ListView<String> serverLogView;

	protected ObservableList<String> serverLogList = FXCollections.observableArrayList();

	public void initialize() {
		serverLogView.setItems(serverLogList);
	}

	@FXML
	private void handleStart(Event event) throws IOException, InterruptedException {
		int port;
		try {
			port = Integer.parseInt(serverPortField.getText());
		}
		catch(NumberFormatException nf) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Invalid port value!");
			alert.setContentText("Enter a value between 0-65535.");
			alert.showAndWait();
			return;
		}
		
		ServerHandler serverHandler;
		try {
			serverHandler = new ServerHandler(port, this);
		}
		catch(IllegalArgumentException ia) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Invalid port value!");
			alert.setContentText("Enter a value between 0-65535.");
			alert.showAndWait();
			return;
		}
		new Thread(serverHandler).start();
	}
}
