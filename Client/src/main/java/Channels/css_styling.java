package Channels;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class css_styling extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Episode 7 - CSS Styling");
        stage.setWidth(400);
        stage.setHeight(500);

        VBox root = new VBox();


        Label label1 = new Label("This is a cool.");
        root.getChildren().addAll(label1);
        Scene scene = new Scene(root);
        //Scene specific stylesheets
        scene.getStylesheets().add("customCss.css");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
