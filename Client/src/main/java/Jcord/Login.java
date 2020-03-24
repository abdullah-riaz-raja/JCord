package Jcord;

import java.awt.Color;
import java.io.File;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Login extends Application {
    Stage stage;
    Image profilePicture;
    GridPane pane = new GridPane();

    boolean signedIn() {
        return false;
    }

    ClickableButton makeImageCircle(Image image) {
        ClickableButton profileView = new ClickableButton(image);
        profileView.setFitHeight(50);
        profileView.setFitWidth(50);
        profileView.setPreserveRatio(true);
        Circle clip = new Circle(25, 25, 20);
        profileView.setClip(clip);

        return profileView;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(createAccount(primaryStage));
        primaryStage.show();
    }

    public Scene createAccount(Stage stage) {
        this.stage = stage;

        TextField userNameField = new TextField();
        userNameField.getStyleClass().add("login");

        Text userName = new Text("Username: ");
        userName.setFont(new Font("Whitney", 12));
        userName.setFill(javafx.scene.paint.Color.WHITE);

        pane.add(userName, 0, 0);
        pane.add(userNameField, 3, 0);

        this.profilePicture = new Image("Images/default_pp.jpg");

        pane.add(makeImageCircle(profilePicture), 2, 1);

        pane.getStyleClass().add("login");

        Scene scene = new Scene(pane);
        scene.getStylesheets().add("customCss.css");
        return scene;
    }

    class ClickableButton extends ImageView {

        public ClickableButton(Image graphic) {

            // Set parameters for the image
            setImage(graphic);
            setFitWidth(24);
            setFitHeight(24);

            // When this button is click, set its visibility to false.
            setOnMouseClicked(e -> {
                FileChooser newPfp = new FileChooser();
                File pfp = newPfp.showOpenDialog(stage);
                try {
                    profilePicture = new Image(pfp.toURI().toURL().toString());
                    pane.add(makeImageCircle(profilePicture), 2, 1);

                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });
        }
    }
}


