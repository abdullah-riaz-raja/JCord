package Jcord;

import java.io.Serializable;
import java.util.Date;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Message implements Serializable {

    public User user;
    private Date timeSent;
    private String message;
    MessageType messageType;
    int messageId;

    public Message(User user,Date timeSent,String message){
        this.user = user;
        this.timeSent = timeSent;
        this.message = message;
        this.messageType = MessageType.MESSAGE;
    }

    public User getUser(){
        return this.user;
    }

    public Date getDate(){
        return this.timeSent;
    }

    public String getMessage(){
        return this.message;
    }

    public MessageType getMessageType(){
        return this.messageType;
    }

    public int getMessageId(){
        return this.messageId;
    }

    public void setMessageId(int num){
        this.messageId = num;
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
        

        VBox messageInfo = new VBox();

        HBox top = new HBox();

        int info_height = 20;

        // need a constant extra amount of spaces for some reason (do not remove spaces)
        Text size = new Text(this.user.getUsername() + "     ");
        size.setFont(new Font("Witney", 12));
        

        // for displaying the username on a message
        TextArea userName = new TextArea(this.user.getUsername());
        userName.getLayoutBounds().getWidth();
        userName.setMaxHeight(info_height);
        userName.setMinHeight(info_height);
        userName.getStyleClass().add("name");
        userName.setEditable(false);
        userName.setPrefWidth(size.getBoundsInLocal().getWidth());
        
        top.getChildren().add(userName);


        HBox timeHolder = new HBox();
        // for the time around the username
        TextArea time = new TextArea(this.timeSent.toString());
        time.getStyleClass().add("time");
        time.setMaxHeight(info_height*11/12-1);
        time.setMinHeight(info_height*11/12-1);
        time.setPrefWidth(175);
        time.setEditable(false);
        
        timeHolder.getChildren().add(time);
        timeHolder.setAlignment(Pos.BOTTOM_LEFT);
        top.getChildren().add(timeHolder);


        // for the actual message on a mesage
        TextArea msgText = new TextArea(this.message);
        msgText.setWrapText(true);
        msgText.getStyleClass().addAll("body");
        msgText.setPrefHeight(20*(this.message.length()/86));  //sets height of the TextArea to 400 pixels 
        msgText.setPrefWidth(600);
        msgText.setEditable(false);
    
        messageInfo.getChildren().addAll(top,msgText);    
        pane.getStyleClass().add("messageView");
        pane.getChildren().addAll(messageInfo);
        
        return pane;
    }
}