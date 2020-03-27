package Jcord;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;


//a class made for the purpose of displaying users who are online
public class PeopleOnlineViewer {

    //arrayList for the are of the online users
    public ArrayList<User> userList;

    public int length = 0;

    public PeopleOnlineViewer() {
        userList = new ArrayList<User>();
    }

    public PeopleOnlineViewer(ArrayList<User> list1) {
        userList = list1;
        length = length + list1.size();
    }

    //adding a user to the arraylist of people online
    void add(User user1){

        userList.add(user1);
        length = length + 1;

    }

    //displaing a newly added online user
    Pane addPeople(Pane pane, int num){

        Label user1 = new Label();
        Label attachment1 = new Label();

        user1.setAlignment(Pos.TOP_RIGHT);
        user1.setPrefHeight(50);
        user1.setMaxHeight(50);
        user1.setPrefWidth(130);
        user1.setMaxWidth(130);
        user1.setText(userList.get(num).getUsername());
        user1.setAlignment(Pos.CENTER);

        user1.setFont(new Font("Whitney", 12));
        user1.setId("PeopleOnline");
        user1.setTextFill(Color.RED);

        Border c = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        user1.setBorder(c);
        ImageView addIcon1 = new ImageView(userList.get(num).getProfilePicture());
        addIcon1.setFitHeight(50);
        addIcon1.setFitWidth(50);
        addIcon1.setPreserveRatio(true);

        Circle clip = new Circle(25, 25, 20);
        addIcon1.setClip(clip);
        attachment1.setGraphic(addIcon1);
        attachment1.setId("1");
        attachment1.setAlignment(Pos.CENTER);

        pane.getChildren().addAll(attachment1, user1);
        return pane;
    }

    Pane generate(){
        FlowPane pane = new FlowPane();
        pane.setMinWidth(200);
        pane.setMaxWidth(200);
        pane.setMinHeight(800);


        int num = 100;
        Label users = new Label();
        users.setPrefHeight(50);
        users.setMaxHeight(50);
        users.setPrefWidth(180);
        users.setMaxWidth(180);
        users.setText("Online - " + Integer.toString(length));
        users.setAlignment(Pos.CENTER);
        users.setTextFill(Color.WHITE);
        pane.getChildren().addAll(users);


        return pane;

    }

    //Return a scrollpane displaying all the people online
    Node generatePeopleOnline(Pane pane) {

        for (int i = 0; i < length; i++) {
            pane = addPeople(pane, i);
        }

        ScrollPane scrollPane = new ScrollPane(pane);
        scrollPane.setMinWidth(200);
        scrollPane.setMaxWidth(200);
        scrollPane.setMinHeight(800);
        scrollPane.setTranslateX(0);
        scrollPane.setTranslateY(0);


        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);


        pane.setId("PeopleOnlineViewer");

        Scene scene = new Scene(scrollPane, 1500, 800);

        return scrollPane;
    }
}