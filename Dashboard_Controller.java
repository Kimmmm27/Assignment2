package Controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.MainUtility;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

public class Dashboard_Controller implements Initializable{
	
	@FXML
	private Button button_logout;
	
	@FXML
	private Label label_welcome;

	@FXML
	private Button add_post;
	
	@FXML
	private Button remove_post;
	
	@FXML
	private Button export_post;
	
	@FXML
	private Button edit_profile;
	
	@FXML
	private Button find_post_id;
	
	@FXML
	private Button find_post_likes;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		button_logout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MainUtility.changeScene(event, "/View/Login.fxml", "Login", null, null, null);
				
			}
		});

		add_post.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MainUtility.changeScene(event, "/View/AddRemove_Post.fxml", "AddPost", null, null, null);
				
			}
		});
		remove_post.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MainUtility.changeScene(event, "/View/AddRemove_Post.fxml", "RemovePost", null, null, null);
				
			}
		});
		export_post.setOnAction(event -> {
		    List<String> allPostData = MainUtility.getAllPostDataFromDB();
		    if (!allPostData.isEmpty()) {
		        FileChooser fileChooser = new FileChooser();
		        fileChooser.setTitle("Save All Post Data as CSV");
		        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
		        File file = fileChooser.showSaveDialog(null);

		        if (file != null) {
		            String fileName = file.getAbsolutePath(); // Get the absolute file path
		            MainUtility.exportAllPostDataToCSV(fileName, allPostData);
		        }
		    } else {
		        showAlert(Alert.AlertType.ERROR, "No Posts Found", "There are no posts in the database to export.");
		    }
		});

		edit_profile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MainUtility.changeScene(event, "/View/EditProfileVIP.fxml", "EditProfile", null, null, null);
				
			}
		});
		find_post_id.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MainUtility.changeScene(event, "/View/Post.fxml", "FindPostID", null, null, null);
				
			}
		});
		find_post_likes.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MainUtility.changeScene(event, "/View/Like.fxml", "FindPostLikes", null, null, null);
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

	public void setUserInformation(String Firstname, String Lastname, String VIPstatus) {
		label_welcome.setText("Welcome !");
	}	
}