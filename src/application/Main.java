package application;
import java.util.Optional;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			LoginDialog dialog = new LoginDialog();
			Optional<Pair<String, String>> result = dialog.showAndWait();
			if(result.isPresent())
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Login successful");
				alert.setContentText("You are logged in Environment: " + result.get().getKey() + ", as: " + result.get().getValue());
				alert.showAndWait();
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR);
			    alert.setTitle("Login failed");
			    alert.setHeaderText("Your username or password is wrong.");
			    alert.showAndWait();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
