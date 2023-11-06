package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.MainUtility;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

public class VIPdashboard_Controller implements Initializable{
	
	@FXML
	private Button button_logout;
	
	@FXML
	private Button add_post;
	
	@FXML
	private Label label_welcome;
	
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
	
	@FXML
	private Button importposts;
	
	@FXML
	private Button stats;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		button_logout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MainUtility.changeScene(event, "/View/Login.fxml", "Login", null, null, null);
				
			}
		});

		stats.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MainUtility.changeScene(event, "/View/DataVisualization_VIP.fxml", "Home", null, null, null);
				
			}
		});
		add_post.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MainUtility.changeScene(event, "/View/AddRemove_Post_VIP.fxml", "AddPost", null, null, null);
				
			}
		});
		remove_post.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MainUtility.changeScene(event, "/View/AddRemove_Post_VIP.fxml", "RemovePost", null, null, null);
				
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
		            String fileName = file.getAbsolutePath(); 
		            MainUtility.exportAllPostDataToCSV(fileName, allPostData);
		        }
		    } else {
		        showAlert(Alert.AlertType.ERROR, "No Posts Found", "There are no posts in the database to export.");
		    }
		});

		edit_profile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MainUtility.changeScene(event, "/View/EditProfileVIP2.fxml", "EditProfile", null, null, null);
				
			}
		});
		find_post_id.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MainUtility.changeScene(event, "/View/Post_VIP.fxml", "FindPostID", null, null, null);
				
			}
		});
		find_post_likes.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MainUtility.changeScene(event, "/View/Like_VIP.fxml", "FindPostLikes", null, null, null);
				
			}
		});
		importposts.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				handleImportPosts(event);				
			}
		});
	}
	
	private void handleImportPosts(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                importPostsFromCSV(selectedFile);
                showAlert(Alert.AlertType.INFORMATION, "Import Successful", "Social media posts imported successfully.");
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Import Error", "An error occurred while importing posts.");
                e.printStackTrace();
            }
        }
    }

	private void importPostsFromCSV(File file) throws IOException {
	    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	        String line;
	        StringBuilder postsText = new StringBuilder();

	        while ((line = reader.readLine()) != null) {
	            String[] data = line.split(",");
	            if (data.length != 3) {
//	                showAlert(Alert.AlertType.ERROR, "CSV Format Error", "The CSV file does not follow the correct format.");
	                return;
	            }
	            String postText = data[0];
	            String username = data[1];
	            String timestamp = data[2];
	            postsText.append("Post Text: ").append(postText).append("\n");
	            postsText.append("Username: ").append(username).append("\n");
	            postsText.append("Timestamp: ").append(timestamp).append("\n");
	            postsText.append("\n");
	            saveDataToTextFile(postsText.toString());
	        }
	    }
	}

	private void saveDataToTextFile(String data) {
	    try {
	        File textFile = new File("imported_posts.txt");
	        FileWriter fileWriter = new FileWriter(textFile, true); 

	        try (BufferedWriter writer = new BufferedWriter(fileWriter)) {
	            writer.write(data);
	            writer.newLine();
	        }

	        showAlert(Alert.AlertType.INFORMATION, "Import Successful", "Social media posts imported successfully and saved to 'imported_posts.txt'");
	    } catch (IOException e) {
	        showAlert(Alert.AlertType.ERROR, "File Save Error", "An error occurred while saving the imported posts to 'imported_posts.txt'.");
	        e.printStackTrace();
	    }
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
