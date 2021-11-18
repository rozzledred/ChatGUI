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
		int port = Integer.parseInt(serverPortField.getText());
		ServerHandler serverHandler = new ServerHandler(port, this);
		new Thread(serverHandler).start();
	}
}
