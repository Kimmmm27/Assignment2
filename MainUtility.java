package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import Controller.Dashboard_Controller;
import Controller.VIPdashboard_Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
public class MainUtility {
	
	public static void changeScene(javafx.event.ActionEvent event, String fxmlFile, String Title, String Firstname, String Lastname, String VIPstatus) {
		Parent root = null;
		if (Firstname != null && Lastname != null && VIPstatus != null) {
			try {
				FXMLLoader loader = new FXMLLoader(MainUtility.class.getResource(fxmlFile));
				root = loader.load();
				Dashboard_Controller Dashboard_Controller = loader.getController();
				Dashboard_Controller.setUserInformation(Firstname, Lastname, VIPstatus);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				root = FXMLLoader.load(MainUtility.class.getResource(fxmlFile));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle(Title);
		stage.setScene(new Scene(root, 700, 400));
		stage.show();
	}

public static void signupUser(javafx.event.ActionEvent event, String Firstname, String Lastname, String Username, String Password) {
    try {
        String filename = "usersignUpData.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true)); 

        // Check if the username already exists in the file
        if (checkIfUserExists(filename, Username)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Username already Exists. Please change !!!");
            alert.show();
        } else {
            // Write user data to the file
            String userData = Firstname + "," + Lastname + "," + Username + "," + Password;
            writer.write(userData);
            writer.newLine();
            writer.close();
			changeScene(event, "/View/Login.fxml", "Login", null, null, null);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private static boolean checkIfUserExists(String filename, String username) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String line;
    while ((line = reader.readLine()) != null) {
        String[] userData = line.split(",");
        if (userData.length >= 3 && userData[2].equals(username)) {
            reader.close();
            return true;
        }
    }
    reader.close();
    return false;
}

public static void loginUser(javafx.event.ActionEvent event, String Username, String Password) {
    try {
        String filename = "usersignUpData.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        boolean userFound = false;

        while ((line = reader.readLine()) != null) {
            String[] userData = line.split(",");
            if (userData.length >= 4 && userData[2].equals(Username) && userData[3].equals(Password)) {
                userFound = true;
                String retrieveFirstname = userData[0];
                String retrieveLastname = userData[1];
                    changeScene(event, "/View/Dashboard.fxml", "Welcome to the Dashboard", retrieveFirstname, retrieveLastname, "");
             }
        }
        reader.close();

        if (!userFound) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Provided credentials are incorrect.");
            alert.show();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
public static void updateUserProfile(javafx.event.ActionEvent event, String username, String firstName, String lastName, String password) {
    if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty()) {
        showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all the fields.");
        return;
    }

    try {
        String filename = "usersignUpData.txt";
        File file = new File(filename);
        List<String> updatedUserData = new ArrayList<>();
        boolean userFound = false;

        // Read user data from the file and update it
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] userData = line.split(",");
            if (userData.length >= 4 && userData[2].equals(username)) {
                userFound = true;
                userData[0] = firstName;
                userData[1] = lastName;
                userData[3] = password;
            }
            updatedUserData.add(String.join(",", userData));
        }
        reader.close();

        // Write the updated user data back to the file
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (String userData : updatedUserData) {
            writer.write(userData);
            writer.newLine();
        }
        writer.close();

        if (userFound) {
            changeScene(event, "/View/Dashboard.fxml", "Welcome to the Dashboard", firstName, lastName, "");
        } else {
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
public static void addPost(String content, String likes, String postId, String author, String dateTime, String shares) {
    if (content.isEmpty() || likes.isEmpty() || postId.isEmpty() || author.isEmpty() || dateTime.isEmpty() || shares.isEmpty()) {
        showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all the fields.");
        return;
    }

    try {
        String filename = "posts_data.txt";
        File file = new File(filename);

        // Check if the post ID already exists in the file
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] postData = line.split(",");
            if (postData.length >= 3 && postData[2].equals(postId)) {
                reader.close();
                showAlert(Alert.AlertType.ERROR, "Error", "Post ID already exists.");
                return;
            }
        }
        reader.close();

        // If the post ID doesn't exist, proceed to add the post
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true)); // Append to the file

        // Create a post data string in CSV format
        String postData = content + "," + likes + "," + postId + "," + author + "," + dateTime + "," + shares;
        writer.write(postData);
        writer.newLine();
        writer.close();

        showAlert(Alert.AlertType.INFORMATION, "Success", "Post added successfully.");
    } catch (IOException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Error", "Failed to add the post.");
    }
}

public static void removePost(String postId) {
    try {
        String filename = "posts_data.txt";
        File file = new File(filename);
        File tempFile = new File("temp_posts_data.txt");

        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String line;
        boolean postRemoved = false;

        while ((line = reader.readLine()) != null) {
            String[] postData = line.split(",");
            if (postData.length >= 3 && postData[2].equals(postId)) {
                postRemoved = true;
                continue; // Skip the line to delete
            }

            writer.write(line);
            writer.newLine();
        }

        writer.close();
        reader.close();

        if (postRemoved) {
            file.delete(); // Delete the original file
            tempFile.renameTo(new File(filename)); // Rename the temporary file to the original filename
            showAlert(Alert.AlertType.INFORMATION, "Success", "Post removed successfully.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Post with the specified ID not found.");
        }
    } catch (IOException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while removing the post.");
    }
}

public static String findPostDetails(String postID) {
    try {
        String filename = "posts_data.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] postData = line.split(",");
            if (postData.length >= 3 && postData[2].equals(postID)) {
                String content = postData[0];
                int likes = Integer.parseInt(postData[1]);
                String author = postData[3];
                int shares = Integer.parseInt(postData[5]);
                String dateTime = postData[4];

                String postDetails = "Post Content: " + content + "\nLikes: " + likes + "\nAuthor: " + author + "\nShares: " + shares + "\nDate/Time: " + dateTime;
                reader.close();
                return postDetails;
            }
        }
        reader.close();
    } catch (IOException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while fetching post details.");
    }

    return null; // Return null if post is not found
}
public static String findPostDetailsbyLike(String numberOfLikes) {
    try {
        String filename = "posts_data.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] postData = line.split(",");
            if (postData.length >= 2 && Integer.parseInt(postData[1]) == Integer.parseInt(numberOfLikes)) {
                String content = postData[0];
                int likes = Integer.parseInt(postData[1]);
                String author = postData[3];
                int shares = Integer.parseInt(postData[5]);
                String dateTime = postData[4];

                String postDetails = "Post Content: " + content + "\nLikes: " + likes + "\nAuthor: " + author + "\nShares: " + shares + "\nDate/Time: " + dateTime;
                reader.close();
                return postDetails;
            }
        }
        reader.close();
    } catch (IOException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while fetching post details.");
    }

    return null; // Return null if post is not found
}

public static List<String> getAllPostDataFromDB() {
    List<String> postDataList = new ArrayList<>();

    try {
        String filename = "posts_data.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            postDataList.add(line);
        }

        reader.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

    return postDataList;
}
	 
public static String findPostDetailsFromFile(String postID) {
    try {
        String filename = "posts_data.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] postData = line.split(",");
            if (postData.length >= 3 && postData[2].equals(postID)) {
                String content = postData[0];
                int likes = Integer.parseInt(postData[1]);
                String author = postData[3];
                int shares = Integer.parseInt(postData[5]);
                String dateTime = postData[4];

                String postDetails = "Post Content: " + content + "\nLikes: " + likes + "\nAuthor: " + author + "\nShares: " + shares + "\nDate/Time: " + dateTime;
                reader.close();
                return postDetails;
            }
        }

        reader.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

    return null; // Return null if post is not found
}

	 
public static int getPostLikesCount() {
    int likesCount = 0;

    try {
        String filename = "posts_data.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            // Count each line as a post like entry
            likesCount++;
        }

        reader.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

    return likesCount;
}

	    public static int getUsersCount() {
	        int usersCount = 0;

	        try {
	            String filename = "usersignUpData.txt";
	            BufferedReader reader = new BufferedReader(new FileReader(filename));
	            String line;

	            while ((line = reader.readLine()) != null) {
	                // Count each line as a user entry
	                usersCount++;
	            }

	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return usersCount;
	    }
	
	 public static void exportAllPostDataToCSV(String fileName, List<String> allPostData) {
		    // Specify the delimiter used in the CSV file
		    final String COMMA_DELIMITER = ",";
		    final String NEW_LINE_SEPARATOR = "\n";

		    FileWriter fileWriter = null;

		    try {
		        fileWriter = new FileWriter(fileName);

		        // Write the CSV file header
		        fileWriter.append("PostID,Content,Likes,Author,DateTime,Shares");
		        fileWriter.append(NEW_LINE_SEPARATOR);

		        // Write all post data to the CSV file
		        for (String postData : allPostData) {
		            fileWriter.append(postData);
		            fileWriter.append(NEW_LINE_SEPARATOR);
		        }

		        showAlert(Alert.AlertType.INFORMATION, "Success", "All post data exported to CSV file.");
		    } catch (IOException e) {
		        e.printStackTrace();
		        showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while exporting post data.");
		    } finally {
		        try {
		            if (fileWriter != null) {
		                fileWriter.flush();
		                fileWriter.close();
		            }
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}

	 public static String findPostlikeDetails(String postID) {
		    try {
		        String filename = "posts_data.txt";
		        BufferedReader reader = new BufferedReader(new FileReader(filename));
		        String line;

		        while ((line = reader.readLine()) != null) {
		            String[] postData = line.split(",");
		            if (postData.length >= 3 && postData[2].equals(postID)) {
		                int likes = Integer.parseInt(postData[1]);

		                String postDetails = "\nLikes: " + likes;
		                reader.close();
		                return postDetails;
		            }
		        }

		        reader.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		        showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while fetching post details.");
		    }

		    return null; // Return null if the post is not found
		}

	 public static int[] getPostSharesCount() {
	     int[] shareCounts = new int[3]; // To store counts for three share groups
	     Random random = new Random();

	     shareCounts[0] = random.nextInt(100); // 0-99 shares
	     shareCounts[1] = random.nextInt(900) + 100; // 100-999 shares
	     shareCounts[2] = random.nextInt(9000) + 1000; // 1000+ shares

	     return shareCounts;
	 }
	 public static int[] getShareCountsFromTextFile() {
		    int[] shareCounts = new int[3]; // Index 0: 0-99 shares, Index 1: 100-999 shares, Index 2: 1000+ shares

		    try {
		        String filename = "posts_data.txt";
		        File file = new File(filename);
		        BufferedReader reader = new BufferedReader(new FileReader(file));
		        String line;

		        while ((line = reader.readLine()) != null) {
		            String[] postData = line.split(",");
		            if (postData.length >= 6) {
		                int shares = Integer.parseInt(postData[5]); // Assuming shares data is in the 6th position

		                if (shares >= 0 && shares < 100) {
		                    shareCounts[0]++; // Increment for 0-99 shares
		                } else if (shares >= 100 && shares < 1000) {
		                    shareCounts[1]++; // Increment for 100-999 shares
		                } else if (shares >= 1000) {
		                    shareCounts[2]++; // Increment for 1000+ shares
		                }
		            }
		        }

		        reader.close();
		    } catch (IOException | NumberFormatException e) {
		        e.printStackTrace();
		    }

		    return shareCounts;
		}


	 public static void exportPostToCSV(String postID, String fileName, String content, int likes, String author, int shares, String dateTime) {
	        // Specify the delimiter used in CSV file
	        final String COMMA_DELIMITER = ",";
	        final String NEW_LINE_SEPARATOR = "\n";

	        // CSV file header
	        final String FILE_HEADER = "PostID,Content,Likes,Author,DateTime,Shares";

	        FileWriter fileWriter = null;

	        try {
	            fileWriter = new FileWriter(fileName);

	            // Write the CSV file header
	            fileWriter.append(FILE_HEADER);
	            fileWriter.append(NEW_LINE_SEPARATOR);

	            // Write post details to the CSV file
	            fileWriter.append(postID);
	            fileWriter.append(COMMA_DELIMITER);
	            fileWriter.append(content);
	            fileWriter.append(COMMA_DELIMITER);
	            fileWriter.append(String.valueOf(likes));
	            fileWriter.append(COMMA_DELIMITER);
	            fileWriter.append(author);
	            fileWriter.append(COMMA_DELIMITER);
	            fileWriter.append(dateTime);
	            fileWriter.append(NEW_LINE_SEPARATOR);
	            fileWriter.append(String.valueOf(shares));
	            fileWriter.append(COMMA_DELIMITER);
	            

	            showAlert(Alert.AlertType.INFORMATION, "Success", "Post details exported to CSV file.");
	        } catch (IOException e) {
	            e.printStackTrace();
	            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while exporting post details.");
	        } finally {
	            try {
	                if (fileWriter != null) {
	                    fileWriter.flush();
	                    fileWriter.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	 private static void showAlert(Alert.AlertType alertType, String title, String message) {
		    Alert alert = new Alert(alertType);
		    alert.setTitle(title);
		    alert.setContentText(message);
		    alert.showAndWait();
		}
}
