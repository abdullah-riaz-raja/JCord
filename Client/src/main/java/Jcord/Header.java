package Jcord;

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
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(70);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(30);
        topBar.getColumnConstraints().addAll(col1,col2);


        //profile image
        Circle profileCircle = new Circle(10);
        Image profileImg = user.getProfilePicture();
        profileCircle.setFill(new ImagePattern(profileImg));

        //channel/username/group name
        Text channel = new Text(user.getUsername());
        channel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        channel.setFill(Color.WHITESMOKE);

        //online status
        ImageView status = new ImageView();
        /*
        if (user.getisOnline()) {
            Image statusImg = new Image("Images/online.png");
            status.setImage(statusImg);
        } else {
            Image statusImg = new Image("Images/offline.png");
            status.setImage(statusImg);
        }*/

        Image statusImg = new Image("Images/online.png");
        status.setImage(statusImg);
    
        status.setFitWidth(20);
        status.setFitHeight(20);

        //Buttons and Search
        //voice
        Button voiceCall = new Button();
        Image voiceCallImg = new Image("Images/voiceCall.png");
        ImageView voice = new ImageView(voiceCallImg);
        voice.setFitWidth(20);
        voice.setFitHeight(20);
        voiceCall.setGraphic(voice);
        voiceCall.setId("headerButtons");

        //video
        Button videoCall = new Button();
        Image videoCallImg = new Image("Images/videoCall.png");
        ImageView video = new ImageView(videoCallImg);
        video.setFitWidth(20);
        video.setFitHeight(20);
        videoCall.setGraphic(video);
        videoCall.setId("headerButtons");

//        //pinned
//        Button pinnedMessages = new Button();
//        Image pinnedImg = new Image("Images/pushPin.png");
//        ImageView pinned = new ImageView(pinnedImg);
//        pinned.setFitWidth(20);
//        pinned.setFitHeight(20);
//        pinnedMessages.setGraphic(pinned);
//        pinnedMessages.setId("headerButtons");

        //search
        TextField searchConvo = new TextField("Search");
        searchConvo.setId("search");

        //Log Out
        Button exit = new Button("Exit");
        Button logout = new Button("Logout");

        exit.setId("logOutButton");
        logout.setId("logOutButton");
        
        exit.setOnAction(e -> {
            System.exit(0);
        });


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


        name.getChildren().addAll(profileCircle, channel, status);
        actions.getChildren().addAll(voiceCall, videoCall, searchConvo,logout, exit);
        topBar.add(name, 0, 1);
        topBar.add(actions, 1, 1);

        return topBar;
    }


}