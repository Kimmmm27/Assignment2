package Controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import application.MainUtility;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class Post_Controller_VIP implements Initializable{
	
	@FXML
	private Button button_logout;
	
	@FXML
	private Button home;
	
	@FXML
	private Button findPost;
	
    @FXML
    private TextField findPostTextField;

   
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		    String postDetails = MainUtility.findPostDetails(postID);

		    if (postDetails != null) {
		        Alert alert = new Alert(Alert.AlertType.INFORMATION);
		        alert.setTitle("Post Details");
		        alert.setHeaderText("Post ID: " + postID);
		        alert.setContentText(postDetails);
		        alert.showAndWait();
		    } else {
		        showAlert(Alert.AlertType.ERROR, "Post Not Found", "The post with ID " + postID + " does not exist.");
		    }
		});
	}

	public static void showAlert(Alert.AlertType alertType, String title, String contentText) {
	    Alert alert = new Alert(alertType);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(contentText);
	    alert.showAndWait();
	}
}
