package Channels;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class channel_list extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        // stage requirements

        stage.setTitle("Display channels");
        stage.setWidth(330);
        stage.setHeight(650);
        // initializing Vbox
        VBox root = new VBox();
        //HBox h = new HBox();
        ImageView arrow = new ImageView("red.png");
        // label
        /*
        * ImageView iv2 = new ImageView();
        iv2.setImage(hashtag);
        iv2.setFitWidth(25);
        iv2.setPreserveRatio(true);
        iv2.setSmooth(true);
        iv2.setCache(true);
        */
        Label label1 = new Label("CHANNEL LIST", arrow);
        label1.setId("customize_label");

        // initializing the image
        Image hashtag = new Image("hastag.png");
        // Button 1
        ImageView iv2 = new ImageView();
        iv2.setImage(hashtag);
        iv2.setFitWidth(25);
        iv2.setPreserveRatio(true);
        iv2.setSmooth(true);
        iv2.setCache(true);

        //Button 2
        ImageView iv3 = new ImageView();
        iv3.setImage(hashtag);
        iv3.setFitWidth(25);
        iv3.setPreserveRatio(true);
        iv3.setSmooth(true);
        iv3.setCache(true);

        Button button = new Button("Chat Channel", iv2);
        Button button2 = new Button("Voice Chat Channel", iv3);
        root.getChildren().addAll(label1);
        root.getChildren().addAll(button,button2);


        Scene scene = new Scene(root,300,500);
        scene.getStylesheets().add("customCss.css");
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args){
        launch(args);
    }
}
