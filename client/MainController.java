package client;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainController {
	
	private ArrayList<Credentials> credentialsList = new ArrayList<Credentials>();
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private Button loginBtn;
	
	@FXML
	private void handleLogin(Event event) {
		String username = usernameField.getText();
		String password = passwordField.getText();
		if(username.length() <= 0 || password.length() <= 0) {
			System.out.println("Too short!");
			return;
		}
		Credentials currentCreds = new Credentials(username, password);
		if(credentialsList.contains(currentCreds)) {
			System.out.println("Already contains!");
			return;
		}
		else {
			System.out.println(currentCreds.getUsername() + " " + currentCreds.getPassword());
			credentialsList.add(currentCreds);
		}
		
		try {
			FXMLLoader connectionLoader = new FXMLLoader(getClass().getResource("Connection.fxml"));
			
			VBox connectionParent = (VBox)connectionLoader.load();
			
			ConnectionController connectionView = connectionLoader.getController();
			connectionView.setMainController(this);
			
			Stage connectionStage = new Stage();
			Scene connectionScene = new Scene(connectionParent);
			connectionStage.setScene(connectionScene);
			connectionStage.setTitle("Connection");
			connectionStage.setResizable(false);
			connectionStage.show();
			loginBtn.getScene().getWindow().hide();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getUsername() {
		return usernameField.getText();
	}
}
