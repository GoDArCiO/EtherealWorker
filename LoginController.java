package application;
	
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class LoginController extends Application {
	
	@FXML
	Button loginBtn;

	public static void main(String[] args) {
		launch(args);
	}
	
	@FXML
	public void login(ActionEvent e)throws Exception {
//pobierz dane i wstaw do plikow
		
	    Stage stage = (Stage) loginBtn.getScene().getWindow();
	    stage.close();
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
