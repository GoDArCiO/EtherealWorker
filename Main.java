package application;
	
import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

//zaladuj pierwsza scene
public class Main extends Application {
	

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("background.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 200, 250));
        primaryStage.show();
        primaryStage.setAlwaysOnTop(true);
    }
	
}
