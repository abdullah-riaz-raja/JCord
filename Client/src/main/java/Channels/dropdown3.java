package Channels;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class dropdown3 extends Application{


    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Channel");
        stage.setWidth(400);
        stage.setHeight(500);

        VBox root = new VBox();

        //Menu Buttons
        //First, create the menu options
        MenuItem item1 = new MenuItem("Chat channel");
        MenuItem item2 = new MenuItem("Voice chat");

        //Now, create the main button to hold these options
        MenuButton menuButton = new MenuButton("Chanel List", null, item1, item2);
        Label food = new Label("No channel selected");

        //Add Events for when an option is selected
        item1.setOnAction(e -> {
            food.setText("Channel: Chat channel");
        });
        item2.setOnAction(e -> {
            food.setText("Food: Voice chat");
        });

        root.getChildren().addAll(food, menuButton);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
