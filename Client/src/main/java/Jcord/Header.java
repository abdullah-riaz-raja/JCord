package Jcord;

/*References

 * Benben. (1966, December 1). How to set an image in a circle. 
 * Retrieved from https://stackoverflow.com/questions/42116313/how-to-set-an-image-in-a-circle
 * 
 * CSS styling:
 * https://www.youtube.com/watch?v=UD_SJ07mQlM
 * https://www.youtube.com/watch?v=MAiKpkQqb6Q
 * https://www.youtube.com/watch?v=lVdtE2BNd88
 
 * */

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Header {

    static User user_copy;

    /**
     * This Node method creates the header for our application which contains
     * name, profile pic and online status of the user
     * It extracts the user info from the User variable passed into it
     * This node gets called in the launcher class
     * 
     * @param user info of the current signed in user to be displayed at header
     * @param primaryStage used when logging out for the log in window
     *
     * @return {@link#topBar}
     */
    public static Node header(User user, Stage primaryStage) throws FileNotFoundException {
         user_copy = user;

        //initializing Panes
        GridPane topBar = new GridPane();
        
        HBox name = new HBox();
        name.setSpacing(10);
        name.setPadding(new Insets(25));

        HBox actions = new HBox();
        actions.setSpacing(20);
        actions.setPadding(new Insets(25));

        //Column Constraints
        //Divides the gridpane into two sections of size 80% and 20%
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(80);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(20);
        topBar.getColumnConstraints().addAll(col1,col2);


        //profile image
        Circle profileCircle = new Circle(10);
        Image profileImg = user.getProfilePicture();
        profileCircle.setFill(new ImagePattern(profileImg));

        //profile name
        Text profileName = new Text(user.getUsername());
        profileName.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        profileName.setFill(Color.WHITESMOKE);

        //online status
        ImageView status = new ImageView();
        if (user.getisOnline()) {
            Image statusImg = new Image("Images/online.png");
            status.setImage(statusImg);
        } else {
            Image statusImg = new Image("Images/offline.png");
            status.setImage(statusImg);
        }
        status.setFitWidth(20);
        status.setFitHeight(20);

        //Buttons
        //Exit and Log Out Button
        Button exit = new Button("Exit");
        Button logout = new Button("Logout");

        exit.setId("logOutButton");
        logout.setId("logOutButton");
        
        //Exit Button. When pressed, exits the app
        exit.setOnAction(e -> {
            System.exit(0);
        });

        //Log out button. When pressed, calls the method from Launcher class to
        //display Log In screen again
        logout.setOnAction( e -> {
            Stage createAccStage = new Stage();

            Login login = new Login();
            login.deleteUserFile();

            primaryStage.close();
            Scene createAccountScene = login.createAccount(createAccStage);
            createAccStage.setScene(createAccountScene);
            createAccStage.showAndWait();

            if(login.getUser() == null){
                System.exit(0);
            }else{
                Launcher launch = new Launcher();
                try {
                    Scene scene = launch.genMainScene(primaryStage);
                     
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        //adds all ui elments on the gridpane
        name.getChildren().addAll(profileCircle, profileName, status);
        actions.getChildren().addAll(logout, exit);
        topBar.add(name, 0, 1);
        topBar.add(actions, 1, 1);

        return topBar;
    }


}