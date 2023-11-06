package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class AddRemovePost_Controller implements Initializable{
	
	@FXML
	private Button button_logout;
	
	@FXML
	private Button home;
	
	 @FXML
	    private Button addPostText;

	    @FXML
	    private TextField contentField;

	    @FXML
	    private TextField likesField;

	    @FXML
	    private TextField postIdField;

	    @FXML
	    private TextField postidforDelete;
	    
	    @FXML
	    private TextField authorField;

	    @FXML
	    private DatePicker dateTimePicker;

	    @FXML
	    private PasswordField passwordField;

	    @FXML
	    private TextField sharesField;
	    @FXML
	    private Button addPost;
	    @FXML
	    private Button RemovePost;
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
				MainUtility.changeScene(event, "/View/Dashboard.fxml", "Home", null, null, null);
				
			}
		});
		  addPost.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                String content = contentField.getText();
	                String likes = likesField.getText();
	                String postId = postIdField.getText();
	                String author = authorField.getText();
	                String dateTime = dateTimePicker.getValue().toString();
	                String shares = sharesField.getText();

	                MainUtility.addPost(content, likes, postId, author, dateTime, shares);
	                clearFields();
	            }
	        });
		  RemovePost.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                String postId = postidforDelete.getText();
	                MainUtility.removePost(postId);
	                postidforDelete.clear();
	            }
	        });
	}
	 private void clearFields() {
	        contentField.clear();
	        likesField.clear();
	        postIdField.clear();
	        authorField.clear();
	        dateTimePicker.getEditor().clear();
	        sharesField.clear();
	    }
}
