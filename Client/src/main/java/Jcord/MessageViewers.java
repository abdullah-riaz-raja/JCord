package Jcord;

import java.util.Date;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class MessageViewers {

    public User user;
    private Date timeSent;
    private String message;

    public MessageViewers(User user,Date timeSent,String msg){
        this.user = user;
        this.timeSent = timeSent;
        this.message = msg;
    }


    Node generateMessageViewNode() {
        HBox pane = new HBox();
        pane.setSpacing(10.0);
        // this is for testing pre database
        

        ImageView profileView = new ImageView(this.user.getProfilePicture());
        profileView.setFitHeight(50);
        profileView.setFitWidth(50);
        profileView.setPreserveRatio(true);
        
        Circle clip = new Circle(25, 25, 20);
        profileView.setClip(clip);
        pane.getChildren().add(profileView);
        pane.setId("messaveViewBackround");

        VBox messageInfo = new VBox();
        messageInfo.setSpacing(5);

        HBox top = new HBox();

        StackPane useName = new StackPane();
        
        // for displaying the username on a message
        TextField userName = new TextField(this.user.getUsername());
        useName.getChildren().add(userName);
        userName.setFont(new Font("Whitney", 12));
        userName.setId("timeFormat");
        userName.setEditable(false);
        userName.setPrefWidth(9*user.getUsername().length());
        top.getChildren().add(useName);


        // for the time around the username
        TextField time = new TextField(this.timeSent.toString());
        time.setFont(new Font("Whitney", 9));
        time.setId("timeFormat");
        time.setPrefWidth(175);
        time.setEditable(false);
        top.getChildren().add(time);


        // for the actual message on a mesage
        TextArea msgText = new TextArea(this.message);
        msgText.setWrapText(true);
        msgText.setPrefHeight(20*(this.message.length()/86));  //sets height of the TextArea to 400 pixels 
        msgText.setPrefWidth(600);
        msgText.setEditable(false);
    
        messageInfo.getChildren().addAll(top,msgText);    
        pane.getChildren().addAll(messageInfo);
        
        return pane;
    }

}