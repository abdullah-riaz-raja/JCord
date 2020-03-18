package Channels;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.awt.*;

public class drop_down extends Application {
    private Stage window;
    private  Scene scene;
    private Button button;

    @Override
    public void start(Stage primarStage) throws Exception{
        window = primarStage;
        window.setTitle("Dropdown testing");
        button = new Button("Channel List");

        ChoiceBox<String> choiceBox = new ChoiceBox<>();

        choiceBox.getItems().add("Channel 1");
        choiceBox.getItems().add("Channel 2");

        //choiceBox.setValue("Channel 1");
        //button.setOnAction(e -> getChoice(choiceBox));

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(button);

        scene = new Scene(layout,300,250);
        window.setScene(scene);
        window.show();
    }

    /*
    private void getChoice(ChoiceBox<String> choiceBox){
        String channel = choiceBox.getValue();
        System.out.println(channel);
    }
     */
    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
