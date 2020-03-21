package Channels;
/*References
* https://www.youtube.com/watch?v=EO62ud_q9s0
* https://stackoverflow.com/questions/36566197/javafx-button-with-transparent-background
* https://www.youtube.com/watch?v=0FVXRp0vmVg&t=190s
* https://www.youtube.com/watch?v=X_HS2hLneto
* https://www.youtube.com/watch?v=Rf3QrfGA6nE
* https://www.javacodegeeks.com/2012/07/javafx-20-layout-panes-hbox-and-vbox.html
* http://fxexperience.com/2011/12/styling-fx-buttons-with-css/
* https://stackoverflow.com/questions/27894945/how-do-i-resize-an-imageview-image-in-javafx
* https://stackoverflow.com/questions/12678197/add-image-to-a-button-at-a-specific-position-javafx
* */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class hbox extends Application {
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Creating the border pane
        VBox root = new VBox();
        //BorderPane pane_frame = new BorderPane();

        // Allowing the nodes inside the pane
        //pane_frame.setTop(createHbox());
        //pane_frame.setTop(createHbox2());
        //pane_frame.setBottom(createVBox());
        root.getChildren().addAll(Hbox_label(),createHbox(),createHbox2());
        root.getChildren().addAll();
        Scene display_scene = new Scene(root,310,600);//placing pane in scene
        primaryStage.setTitle("Display Channels");// setting stage title
        display_scene.getStylesheets().add("customCss.css");
        primaryStage.setScene(display_scene);// placing scene into the stage
        primaryStage.show();// displaying stage

    }

    // HBox(Label), channel list
    private HBox Hbox_label(){
        HBox hBox0 = new HBox(50);
        ImageView arrow = new ImageView("red.png");
        Label label1 = new Label("CHANNEL LIST", arrow);
        hBox0.getChildren().addAll(label1);
        return hBox0;
    }
    //Hbox(button 1)
    private HBox createHbox(){
        HBox hBox1 = new HBox(50);
        Image hashtag = new Image("hashtag2.png");
        //hBox.setPadding(new Insets(15,15,15,15));
        ImageView iv2 = new ImageView();
        iv2.setImage(hashtag);
        iv2.setFitWidth(25);
        iv2.setPreserveRatio(true);
        iv2.setSmooth(true);
        iv2.setCache(true);
        Button button = new Button("Chat Channel", iv2);
        hBox1.getChildren().add(button);
        return hBox1;
    }
    //Hbox(button 2)
    private HBox createHbox2(){
        HBox hBox2 = new HBox(50);
        Image hashtag = new Image("hashtag2.png");
        //hBox.setPadding(new Insets(15,15,15,15));
        ImageView iv3 = new ImageView();
        iv3.setImage(hashtag);
        iv3.setFitWidth(25);
        iv3.setPreserveRatio(true);
        iv3.setSmooth(true);
        iv3.setCache(true);
        Button button = new Button("Voice Chat Channel", iv3);
        hBox2.getChildren().add(button);
        return hBox2;
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
