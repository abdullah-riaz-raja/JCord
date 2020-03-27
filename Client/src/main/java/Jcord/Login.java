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

/**
 * This class handles the login function of the application
 *
 * @see Application
 * @see Stage
 * @see Image
 * @see GridPane
 */
public class Login {
    Stage stage;
    Image profilePicture;
    GridPane pane = new GridPane();
    User user;

    /**
     * This method checks if the user was signed in before
     *
     * @return boolean of the user file existing
     */
    boolean isSignedIn() {
        return new File("src/main/resources/UserInfo/profile.user").exists();
    }

    /**
     * This accessor method returns the user
     *
     * @return User of the client
     */
    User getUser() {
        return this.user;
    }

    /**
     * This method returns the user from the file
     *
     * @return User of the client
     * @throws IOException
     * @see FileInputStream
     * @see ObjectInputStream
     * @see File
     */
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

    /**
     * This method saves the user data in a new file
     *
     * @see FileOutputStream
     * @see ObjectOutputStream
     * @see File
     */
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

    /**
     * This method deletes the currently stored user
     *
     * @see #saveUser()
     */
    void deleteUserFile(){
        File newUserFile = new File("src/main/resources/UserInfo/profile.user");
        newUserFile.delete();
    }

    /**
     * This method creates a {@link ClickableButton} for the user's image
     *
     * @param image Image of the user
     * @return ClickableButton of the user's image
     */
    ClickableButton makeImageCircle(Image image) {
        ClickableButton profileView = new ClickableButton(image);
        profileView.setFitHeight(50);
        profileView.setFitWidth(50);
        profileView.setPreserveRatio(true);
        Circle clip = new Circle(25, 25, 20);
        profileView.setClip(clip);

        return profileView;
    }

    /**
     * This method creates the account
     *
     * @param stage of the application
     * @return Scene of the account creation
     */
    public Scene createAccount(Stage stage) {
        this.stage = stage;

        // Gets the username information from the user
        TextField userNameField = new TextField();
        userNameField.getStyleClass().add("login");
        userNameField.setPromptText("Username");

        // Prompt User Information
        Text userName = new Text("Username: ");
        userName.setFont(new Font("Whitney", 12));
        userName.setFill(javafx.scene.paint.Color.WHITE);

        pane.add(userNameField, 1, 0);

        this.profilePicture = new Image("Images/default_pp.jpg");

        pane.add(makeImageCircle(profilePicture), 0,0);

        pane.getStyleClass().add("login");

        // Creates the account
        Button create = new Button("Create Account");
        create.setOnAction( e->{
            user = new User(userNameField.getText(), this.profilePicture);
            saveUser();
            stage.close();
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

    /**
     * This helper class extends {@link ImageView} and handles the view of the user image
     */
    class ClickableButton extends ImageView {

        /**
         * This constructor takes in the user's image and constucts the view
         *
         * @param graphic
         */
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


