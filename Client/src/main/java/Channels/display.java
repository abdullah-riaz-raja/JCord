package Channels;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class display extends Application {
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Creating the border pane
        BorderPane pane_frame = new BorderPane();

        // Allowing the nodes inside the pane
        pane_frame.setTop(createHbox());
        pane_frame.setBottom(createVBox());

        Scene display_scene = new Scene(pane_frame);//placing pane in scene
        primaryStage.setTitle("Display Channels");// setting stage title
        primaryStage.setScene(display_scene);// placing scene into the stage
        primaryStage.show();// displaying stage

    }
    //Hbox
    private HBox createHbox(){
        HBox hBox = new HBox(50);
        hBox.setPadding(new Insets(15,15,15,15));
        hBox.setStyle("-fx-background-color: grey");
        hBox.getChildren().add(new Button("Channel List"));
        return hBox;
    }

    private VBox createVBox(){
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(150,5,5,5));
        vBox.getStylesheets().add("ChannelCss.css");
        Label[] channels = {new Label("Normal chat window"),
                new Label("Voice chat window")};

        for(Label channel:channels){
            VBox.setMargin(channel, new Insets(0,0,0,15));
            vBox.getChildren().add(channel);
        }
        return vBox;


    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
