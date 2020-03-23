package Channels;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class dropdown2 extends Application {

    private Stage window;
    private Scene scene;
    private Button button;
    public static void main(String[] args) {

        launch(args);

    }
    @Override

    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;

        window.setTitle("ChoiceBox Demo");

        button = new Button("Click me");
        ChoiceBox<String> choiceBox = new ChoiceBox<>();

        //getItems returns the ObservableList object which you can add items to

        choiceBox.getItems().add("Chat channel");

        choiceBox.getItems().add("Voice chat channel");

        //Set a default value

        choiceBox.setValue("Chat channel");
        button.setOnAction(e -> getChoice(choiceBox));


        VBox layout = new VBox(10);

        layout.setPadding(new Insets(20, 20, 20, 20));

        layout.getChildren().addAll(choiceBox, button);



        scene = new Scene(layout, 300, 250);

        window.setScene(scene);

        window.show();

    }
    //To get the value of the selected item

    private void getChoice(ChoiceBox<String> choiceBox){

        String channel = choiceBox.getValue();

        System.out.println(channel);

    }





}