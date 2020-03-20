package Jcord;

import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class image_size extends Application {
    @Override public void start(final Stage stage) throws Exception {
        final Label response = new Label();
        final ImageView imageView = new ImageView(
                new Image("http://s3.amazonaws.com/spoonflower/public/design_thumbnails/0377/1634/rrblack_hashtag_on_white_shop_thumb.png")
        );
        final Button button = new Button("I love you", imageView);
        button.setStyle("-fx-base: coral;");
        button.setContentDisplay(ContentDisplay.TOP);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                response.setText("I love you too!");
            }
        });

        final VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(button, response);
        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10; -fx-font-size: 20;");
        stage.setScene(new Scene(layout));
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
