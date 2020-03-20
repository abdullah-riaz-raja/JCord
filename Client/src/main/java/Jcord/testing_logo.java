package Jcord;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.*;

public class testing_logo extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Title for window");

        VBox root = new VBox();

        //Add controls(child nodes) to the parent node

        //Displaying Images with ImageView
        Image hashtag = new Image("hastag.png");
        //Image image = new Image("https://static.interestingengineering.com/images/APRIL/sizes/black_hole_resize_md.jpg");
        //Button btOK = new Button("Normal chat channel");
        //Labels - Display text
        //ImageView hash = new ImageView();
        //hash.setImage(hashtag);
        ImageView iv2 = new ImageView();
        iv2.setImage(hashtag);
        iv2.setFitWidth(20);
        iv2.setPreserveRatio(true);
        iv2.setSmooth(true);
        iv2.setCache(true);

        Button button = new Button("I love you", iv2);
        root.getChildren().addAll(button);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("customCss.css");
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
