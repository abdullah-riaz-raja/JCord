package Jcord;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Launcher extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox pane = new VBox();


        pane.getChildren().add(MessageCreator.GenerateMessageBox());

        Scene scene = new Scene(pane);
        scene.getStylesheets().add("customCss.css");


        primaryStage.setScene(scene);
        primaryStage.show();
        
    }


}
