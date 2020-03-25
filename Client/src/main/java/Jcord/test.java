package Jcord;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class test extends Application {
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Create a scene and place a button in the scene
        StackPane pane = new StackPane();
        Button btOK = new Button("Normal chat channel");
        btOK.getStylesheets().add("ChannelCss.css");
        pane.getChildren().add(btOK);
        Scene scene = new Scene(pane, 350, 500);
        primaryStage.setTitle("three"); // Set the stage title
        scene.getStylesheets().add("ChannelCss.css");
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}