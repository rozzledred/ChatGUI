package client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientSocketHandler implements Runnable {

	private String username, addr;
	private int port;
	private ConnectionController connectionController;
	
	public ClientSocketHandler(String username, String addr, int port, ConnectionController connectionController) {
		this.username = username;
		this.addr = addr;
		this.port = port;
		this.connectionController = connectionController;
	}
	
	@Override
	public void run() {
		Client client;
		try {
			client = new Client(username, addr, port);
			//throwAlert(AlertType.INFORMATION, "Connected", "Successfully connected!", 
					//"Connected to room at port " + port + ".");
			connectionController.setClient(client);
			client.sendMessage(username);

			FXMLLoader messageLoader = new FXMLLoader(getClass().getResource("Message.fxml"));
			VBox messageParent = (VBox)messageLoader.load();
			MessageController messageView = messageLoader.getController();
			messageView.setMainController(connectionController);
			messageView.setClient(client);
			
			messageView.setupChatView();
			
			Platform.runLater(new Runnable(){
				@Override
				public void run() {
					Stage messageStage = new Stage();
					Scene messageScene = new Scene(messageParent);
					
					messageStage.setScene(messageScene);
					messageStage.setTitle("Message");
					messageStage.setResizable(false);
					messageStage.show();
					
					connectionController.getProgressBar().getScene().getWindow().hide();
				}
				});
		}
		catch(ConnectException ce) {
			throwAlert(AlertType.ERROR, "Error", "Connection timed out!", 
					"Make sure the IP and port are accessible.");
			return;
		}
		catch(UnknownHostException uh) {
			throwAlert(AlertType.ERROR, "Error", "Unknown host!", 
					"Please enter a valid IP address.");
			return;
		}
		catch (IOException e) {
			return;
		}
		finally {
			setProgressBar(false);
		}
	}
	
	public void throwAlert(AlertType al, String title, String header, String content) {
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				Alert alert = new Alert(al);
				alert.setTitle(title);
				alert.setHeaderText(header);
				alert.setContentText(content);
				alert.showAndWait();
			}
			});
	}
	
	public void setProgressBar(boolean status) {
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				connectionController.setProgressBar(status);
			}
			});
	}
	
	
}
