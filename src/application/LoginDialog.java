package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class LoginDialog{
	
	
	
	static List<Map<String,String>> environmentList = new ArrayList<Map<String,String>>();
	static {
		
		environmentList.add(new HashMap<String,String>());
		environmentList.add(new HashMap<String,String>());
		environmentList.add(new HashMap<String,String>());
		environmentList.get(0).put("andrzej", "anszej");
		environmentList.get(0).put("mati", "blazeit420");
		environmentList.get(1).put("supertester22", "lolol12");
		environmentList.get(1).put("exem", "machen");
		environmentList.get(2).put("marger", "superdev");
		environmentList.get(2).put("head22", "division11");
	}

	Dialog<Pair<String, String>> dialog = new Dialog<>();
	ButtonType loginButtonType = new ButtonType("Logon", ButtonData.OK_DONE);
	ComboBox<String> users = new ComboBox<String>();
	ChoiceBox<String> environment = new ChoiceBox<String>(FXCollections.observableArrayList("Production", "Test", "Developer"));
	PasswordField password = new PasswordField();
	
	enum Environment 
	{
		Production(0), Test(1), Developer(2);
		
		 private final int value;

	    private Environment(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
	

	
	Optional<Pair<String, String>> showAndWait()
	{
		return dialog.showAndWait();
	}
	
	LoginDialog()
	{
		dialog.setTitle("Login");
		dialog.setHeaderText("Login to system STYLEman");
		// Set the icon (must be included in the project).
		ImageView image = new ImageView(this.getClass().getResource("login.png").toString());
		image.setFitWidth(64);
		image.setFitHeight(64);
		dialog.setGraphic(image); 
		// Set the button types.
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
		users.setEditable(true);
		// Create the user name and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		//changing list of users listener
		environment.getSelectionModel().selectedIndexProperty().addListener( (obs, oldValue, newValue) -> 
					users.setItems(FXCollections.observableArrayList(environmentList.get((int)newValue).keySet()))
				);	


		password.setPromptText("Password");
		grid.add(new Label("Environment:"), 0, 0);
		grid.add(environment, 1,0);
		grid.add(new Label("Username:"), 0, 1);
		grid.add(users, 1, 1);
		grid.add(new Label("Password:"), 0, 2);
		grid.add(password, 1, 2);
		
		dialog.getDialogPane().lookupButton(loginButtonType).setDisable(true);
		
		//adding listeners
		environment.valueProperty().addListener((obs, oldValue, newValue) -> formChanged());
		users.getEditor().textProperty().addListener((obs, oldValue, newValue) -> 
		{
			formChanged();
			users.setValue(newValue);
		});
		users.valueProperty().addListener((obs, oldValue, newValue) -> formChanged());
		password.textProperty().addListener((obs, oldValue, newValue) -> formChanged());
		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(button -> 
		{
			if(button == loginButtonType)
			{
				if(environmentList.get(Environment.valueOf(environment.getValue()).getValue()).containsKey(users.getValue()) 
						&& environmentList.get(Environment.valueOf(environment.getValue()).getValue()).get(users.getValue()).equals(password.getText()))
				{
					return new Pair<>(environment.getValue(), users.getValue());
				}
			}
			return null;
		});
	}
		
	void formChanged()
	{
		dialog.getDialogPane().lookupButton(loginButtonType).setDisable((password.getText().isEmpty() 
				|| users.getValue() == null || environment.getValue() == null || users.getValue().toString().isEmpty() || environment.getValue().toString().isEmpty()));
	}
	
	
}
