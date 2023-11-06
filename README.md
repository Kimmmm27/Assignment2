DataAnalyticsHub - S3928680
	•	Eclipse IDE for Java Developers
Java Version:
	•	Java SE 17
JavaFX Version:
	•	JavaFX 21.0.1
Database Used:
	•	The application use text file for data storage. No external database system is used.
Installation and Running Instructions:
Prerequisites:
	•	Ensure you have Java SE 17 installed on your system.
	•	Make sure you have JavaFX 21.0.1 installed or configured with your project.
Steps to Install and Run:
	•	Clone or download the project repository to your local machine via Github link or ZIP file.
	•	Open Eclipse IDE.
	•	Import the project into Eclipse.
	•	In Eclipse, go to File > Open Projects from File System...
	•	Browse and select the project directory.
	•	Click Finish to import the project.
	•	Configure JavaFX in Eclipse.
	•	Once the project is imported and configured, you can run it.
Object-Oriented Design:
The application follows an MVC (Model-View-Controller) design pattern to separate concerns. Here's a brief description:
	•	Model (Milestone1 and Model packages): This is where the data and business logic are managed. User profiles and posts are stored and processed in the User and Post classes.
	•	View (View package): This package contains the FXML files that define the user interface. These files describe the layout and appearance of the application's windows and components.
	•	Controller (Controller package): The controller classes, such as LoginController, SignUpController, UserProfileController, and Milestone1Controller, manage the interaction between the model and view. They handle user actions, data retrieval, and user interface updates.
The SOLID principles have been applied to enhance maintainability, reduce coupling, and improve code organization where suitable.
