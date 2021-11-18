package client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	/**
	 * The start method to render the GUI and set various parameters for the look of the GUI
	 * via the MainView.fxml file.
	 * @param primaryStage the initial stage that is displayed.
	 */
	
	Stage primaryStage;
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		try {
			VBox root = (VBox)FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Chat GUI");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Main method to launch the program.
	 * @param args String arguments given via the console.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
