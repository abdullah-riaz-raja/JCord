package Channels;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class channel_list extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Display channels");
        stage.setWidth(350);
        stage.setWidth(500);

        VBox root = new VBox();
        Label label1 = new Label("CHANNEL LIST");
        label1.setId("customize_label");
        root.getChildren().addAll(label1);

        Scene scene = new Scene(root,200,600);
        scene.getStylesheets().add("customCss.css");
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args){
        launch(args);
    }
}
