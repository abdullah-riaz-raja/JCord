package Channels;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class display extends Application {
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        System.out.println("Hello");

    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
