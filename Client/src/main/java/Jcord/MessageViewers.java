package Jcord;

import java.util.Date;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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

        GridPane messageInfo = new GridPane();

        Text userName = new Text(this.user.getUsername());
        messageInfo.add(userName, 0, 0);
        
        // setting user name style and font
        userName.setFont(new Font("Whitney", 12));
        userName.setStyle("-fx-fill: white;");
        

        Text time = new Text(this.timeSent.toString());
        
        // setting time font and color
        time.setFont(new Font("Whitney", 9));
        time.setStyle("-fx-fill: white;");

        messageInfo.add(time,1,0);
        messageInfo.setHgap(5);
        messageInfo.setVgap(10);

        Text msgText = new Text(this.message);
        
        msgText.setFont(new Font("Whitney", 14));
        msgText.setStyle("-fx-fill: white;");

        messageInfo.add(msgText,0,1);



        
        pane.getChildren().addAll(messageInfo);


        
        
        return pane;
    }

}