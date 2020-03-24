package Jcord;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Login extends Application {
    Stage stage;
    Image profilePicture;
    GridPane pane = new GridPane();
    User user;

    boolean isSignedIn() {
        return new File("/resources/profile.user").exists();
    }

    User getUser() {
        return this.user;
    }

    User getUserFromFile() throws IOException {
        File newUserFile = new File("src/main/resources/UserInfo/profile.user");
        FileInputStream fileInStream = new FileInputStream(newUserFile);
        ObjectInputStream objectStream = new ObjectInputStream(fileInStream);


        try {
            user = (User) objectStream.readObject();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return user;
    }

    void saveUser() {
        FileOutputStream fileStream;
		try {
            File newUserFile = new File("src/main/resources/UserInfo/profile.user");
            newUserFile.createNewFile();

            fileStream = new FileOutputStream(newUserFile);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(this.user);
            
            
            objectStream.close();
            fileStream.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e){
            e.printStackTrace();

        }
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
        userNameField.setPromptText("Username");

  


        Text userName = new Text("Username: ");
        userName.setFont(new Font("Whitney", 12));
        userName.setFill(javafx.scene.paint.Color.WHITE);

        //pane.add(userName, 1, 0);
        pane.add(userNameField, 1, 0);

        this.profilePicture = new Image("Images/default_pp.jpg");

        pane.add(makeImageCircle(profilePicture), 0,0);

        pane.getStyleClass().add("login");

        Button create = new Button("Create Account");
        
        create.setOnAction( e->{
            user = new User(userNameField.getText(), this.profilePicture);
            saveUser();
        });


        pane.add(create,1,1);
        pane.getStyleClass().add("login");

        Scene scene = new Scene(pane);
        scene.getStylesheets().add("customCss.css");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                create.requestFocus();
            }
        });
        

        return scene;
    }

    class ClickableButton extends ImageView {

        public ClickableButton(Image graphic) {

            setImage(graphic);
            setFitWidth(24);
            setFitHeight(24);

            setOnMouseClicked(e -> {
                FileChooser newPfp = new FileChooser();
                File pfp = newPfp.showOpenDialog(stage);
                try {
                    profilePicture = new Image(pfp.toURI().toURL().toString());
                    pane.add(makeImageCircle(profilePicture), 0, 0);

                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });
        }
    }
}


