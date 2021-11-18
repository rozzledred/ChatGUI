package client;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
		if(username.length() <= 0 || password.length() <= 8) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Username or password is too short!");
			alert.setContentText("Password minimum is 8 characters, username cannot be empty.");
			alert.showAndWait();
			return;
		}
		Credentials currentCreds = new Credentials(username, password);
		if(credentialsList.contains(currentCreds)) {
			Credentials check = credentialsList.get(credentialsList.indexOf(currentCreds));
			if(!(check.getPassword() == currentCreds.getPassword())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Incorrect password!");
				alert.setContentText("The password associated with this username is incorrect, please try again.");
				alert.showAndWait();
				return;
			}
		}
		else {
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
