package Jcord;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Header {

    public static Node header() throws FileNotFoundException {

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
        col1.setPercentWidth(75);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(25);
        topBar.getColumnConstraints().addAll(col1,col2);

        //group or @ image
        Image atImg = new Image("Images/at.png");
        ImageView at = new ImageView(atImg);
        at.setFitWidth(20);
        at.setFitHeight(20);

        //channel/username/group name
        Text channel = new Text("Bad Things");
        channel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        channel.setFill(Color.WHITESMOKE);

        //online status if one person chat
        Image statusImg = new Image("Images/online2.png");
        ImageView status = new ImageView(statusImg);
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

        //pinned
        Button pinnedMessages = new Button();
        Image pinnedImg = new Image("Images/pushPin.png");
        ImageView pinned = new ImageView(pinnedImg);
        pinned.setFitWidth(20);
        pinned.setFitHeight(20);
        pinnedMessages.setGraphic(pinned);
        pinnedMessages.setId("headerButtons");

        //search
        TextField searchConvo = new TextField("Search");
        searchConvo.setId("search");

        name.getChildren().addAll(at, channel, status);
        actions.getChildren().addAll(voiceCall, videoCall, pinnedMessages, searchConvo);
        topBar.add(name, 0, 1);
        topBar.add(actions, 1, 1);

        return topBar;
    }


}