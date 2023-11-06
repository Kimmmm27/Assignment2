

package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.MainUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;

public class DataVisualization_Controller_VIP implements Initializable{
	
	@FXML
	private Button button_logout;
	
	@FXML
	private Button home;

    @FXML
    private PieChart chartsDatas;
    
//    int[] shareCounts = MainUtility.getPostSharesCount();
//    int postLikes = shareCounts[0] + shareCounts[1] + shareCounts[2];
//    int usersCount = (int) (Math.random() * 10000); 
    
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

		 // Read share counts from text file or any other data source
        int[] shareCounts = MainUtility.getShareCountsFromTextFile();
        int postLikes = shareCounts[0] + shareCounts[1] + shareCounts[2];
        int usersCount = (int) (Math.random() * 10000);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("0-99 Shares", shareCounts[0]),
                new PieChart.Data("100-999 Shares", shareCounts[1]),
                new PieChart.Data("1000+ Shares", shareCounts[2])
        );

        chartsDatas.setData(pieChartData);
        chartsDatas.setTitle("Distribution of Shares");

        ObservableList<PieChart.Data> likesVsUsersData = FXCollections.observableArrayList(
                new PieChart.Data("Post Likes", postLikes),
                new PieChart.Data("Users", usersCount)
        );
    }
}