package Channels;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class display extends Application {
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Creating the border pane
        BorderPane pane_frame = new BorderPane();

        // Allowing the nodes inside the pane
        pane_frame.setTop(createHbox());
        //pane_frame.setBottom(getVBox());

        Scene display_scene = new Scene(pane_frame);//placing pane in scene
        primaryStage.setTitle("Display Channels");// setting stage title
        primaryStage.setScene(display_scene);// placing scene into the stage
        primaryStage.show();// displaying stage

    }
    //Hbox
    public HBox createHbox(){
        HBox hBox = new HBox(15);
        hBox.setPadding(new Insets(15,15,15,15));
        hBox.setStyle("-fx-background-color: grey");
        hBox.getChildren().add(new Label("Channel List"));
        return hBox;
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
