package Controller;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.MainUtility;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class EditProfile_Controller_VIP implements Initializable{
	
	@FXML
	private Button button_logout;
	
	@FXML
	private Button home;
	
  @FXML
  private TextField firstNameField;
  
  @FXML
  private TextField lastNameField;
  
  @FXML
  private TextField usernameField;
  
  @FXML
  private PasswordField passwordField;
  
  @FXML
  private Button saveChangesButton;
  
  private String storedFirstName;
  private String storedLastName;
  private String storedUsername;
  private String storedPassword;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		button_logout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MainUtility.changeScene(event, "/View/Login.fxml", "Login", null, null, null);
				
			}
		});

		home.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MainUtility.changeScene(event, "/View/VIPdashboard.fxml", "Home", null, null, null);
				
			}
		});
	
		saveChangesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();

                MainUtility.updateUserProfile(event, username, firstName, lastName, password);
				showAlert(Alert.AlertType.INFORMATION, "Success", "Profile updated successfully");
				clearFields(); 
            }
        });

        firstNameField.setText(storedFirstName);
        lastNameField.setText(storedLastName);
        usernameField.setText(storedUsername);
        passwordField.setText(storedPassword);
	}
	@FXML
	private void clearFields() {
	    firstNameField.clear();
	    lastNameField.clear();
	    usernameField.clear();
	    passwordField.clear();
	}

    public void setUserData(String firstName, String lastName, String username, String password) {
        storedFirstName = firstName;
        storedLastName = lastName;
        storedUsername = username;
        storedPassword = password;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}