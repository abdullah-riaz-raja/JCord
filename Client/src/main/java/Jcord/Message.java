package Jcord;

import java.io.Serializable;
import java.util.Date;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * This class implements {@link Serializable} and stores the message information
 *
 * @see Serializable
 * @see Date
 */
public class Message implements Serializable {
    public User user;
    private Date timeSent;
    private String message;
    int messageId;

    /**
     * This constructor takes in user, the time the message was sent, and message and initializes this class
     *
     * @param user User of the client user
     * @param timeSent Date of when the message was sent
     * @param message String value of the message
     */
    public Message(User user,Date timeSent,String message){
        this.user = user;
        this.timeSent = timeSent;
        this.message = message;
    }

    /**
     * This access method returns the client user
     *
     * @return {@link #user}
     */
    public User getUser(){
        return this.user;
    }

    /**
     * This access method returns the time the message was sent
     *
     * @return {@link #timeSent}
     */
    public Date getDate(){
        return this.timeSent;
    }

    /**
     * This accessor method returns the message
     *
     * @return {@link #message}
     */
    public String getMessage(){
        return this.message;
    }

    /**
     * This accessor method returns the message id
     *
     * @return {@link #messageId}
     */
    public int getMessageId(){
        return this.messageId;
    }

    /**
     * This mutator method sets the message ID
     *
     * @param num int value of the id
     */
    public void setMessageId(int num){
        this.messageId = num;
    }

    /**
     * This method generates and returns the node of the message
     *
     * @return Node of the message
     */
    Node generateMessageViewNode() {
        // Setting up the node
        HBox pane = new HBox();
        pane.setSpacing(10.0);

        // Setting up the profile picture
        ImageView profileView = new ImageView(this.user.getProfilePicture());
        profileView.setFitHeight(50);
        profileView.setFitWidth(50);
        profileView.setPreserveRatio(true);

        // Setting up the frame of the picture
        Circle clip = new Circle(25, 25, 20);
        profileView.setClip(clip);
        pane.getChildren().add(profileView);
        

        // Setting up the message
        HBox top = new HBox();
        int info_height = 20;

        // need a constant extra amount of spaces for some reason (do not remove spaces)
        Text size = new Text(this.user.getUsername() + "     ");
        size.setFont(new Font("Witney", 12));

        // Setting up the username of the message
        TextArea userName = new TextArea(this.user.getUsername());
        userName.getLayoutBounds().getWidth();
        userName.setMaxHeight(info_height);
        userName.setMinHeight(info_height);
        userName.getStyleClass().add("name");
        userName.setEditable(false);
        userName.setPrefWidth(size.getBoundsInLocal().getWidth());

        top.getChildren().add(userName);

        // Setting up the time of the message
        HBox timeHolder = new HBox();
        TextArea time = new TextArea(this.timeSent.toString());
        time.getStyleClass().add("time");
        time.setMaxHeight(info_height*11/12-1);
        time.setMinHeight(info_height*11/12-1);
        time.setPrefWidth(175);
        time.setEditable(false);
        timeHolder.getChildren().add(time);
        timeHolder.setAlignment(Pos.BOTTOM_LEFT);

        top.getChildren().add(timeHolder);

        // Setting up the message text of the message
        TextArea msgText = new TextArea(this.message);
        msgText.setWrapText(true);
        msgText.getStyleClass().addAll("body");
        msgText.setPrefHeight(20*(this.message.length()/86));  //sets height of the TextArea to 400 pixels 
        msgText.setPrefWidth(600);
        msgText.setEditable(false);

        // Adding all components together
        VBox messageInfo = new VBox();
        messageInfo.getChildren().addAll(top,msgText);    
        pane.getStyleClass().add("messageView");
        pane.getChildren().addAll(messageInfo);
        
        return pane;
    }
}