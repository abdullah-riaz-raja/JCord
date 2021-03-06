package Jcord;
/*References
 * https://www.youtube.com/watch?v=EO62ud_q9s0
 *
 * Chuss, T. C. T. (1966, February 1). JavaFX Button with transparent background.
 * Retrieved from https://stackoverflow.com/questions/36566197/javafx-button-with-transparent-background
 *
 * https://www.youtube.com/watch?v=0FVXRp0vmVg&t=190s
 * https://www.youtube.com/watch?v=X_HS2hLneto
 * https://www.youtube.com/watch?v=Rf3QrfGA6nE
 *
 * Damm, S. (2015, February 24). JavaFX 2.0 Layout Panes - HBox and VBox: Java Code Geeks - 2020.
 * Retrieved from https://www.javacodegeeks.com/2012/07/javafx-20-layout-panes-hbox-and-vbox.html
 *
 * Potts, J., Potts, J., Bair, R., Potts, J., Bair, R., Potts, J., … Bair, R. (2011, December 20).
 * Styling FX Buttons with CSS. Retrieved from http://fxexperience.com/2011/12/styling-fx-buttons-with-css/
 *
 * JeremyJeremy. (1964, November 1). How do I resize an imageview image in javafx?
 * Retrieved from https://stackoverflow.com/questions/27894945/how-do-i-resize-an-imageview-image-in-javafx
 *
 * AdilAdil 3, & jewelseajewelsea 122k1010 gold badges304304 silver badges343343 bronze badges. (1962, August 1). Add image to a button at a specific position JavaFX.
 * Retrieved from https://stackoverflow.com/questions/12678197/add-image-to-a-button-at-a-specific-position-javafx
 * */
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;

public class ChannelsDisplay{

    /**
     * This Node method creates a VBox
     * Label(), ChatChannel(), VoiceChannel() hboxes are added to the VBox
     * This node gets called in the launcher class
     *
     * @return {@link#root}
     */
    Node node1() {
        VBox root = new VBox();
        root.getChildren().addAll(Label(), ChatChannel(), VoiceChannel());
        root.getChildren().addAll();
        return root;
    }

    /**
     * This HBox method creates a label as a heading
     * The image is getting accessed and displayed next to the label
     * Label getting added to the hbox
     *
     * @return {@link#hBox0}
     */
    // HBox(Label), channel list
    private HBox Label() {
        HBox hBox0 = new HBox(100);
        ImageView arrow = new ImageView("Images/bottom.png");
        Label label1 = new Label("CHANNEL LIST", arrow);
        hBox0.getChildren().addAll(label1);
        return hBox0;
    }

    /**
     * This HBox method creates a chat channel button below the label
     * The image is getting accessed and displayed next to the button
     * Button getting added to the hbox
     *
     * @return {@link#hBox1}
     */
    //Hbox(button 1)
    private HBox ChatChannel() {
        HBox hBox1 = new HBox(100);
        Image hashtag = new Image("Images/new_hash.png");
        //hBox.setPadding(new Insets(15,15,15,15));
        ImageView iv2 = new ImageView();
        iv2.setImage(hashtag);
        iv2.setFitWidth(25);
        iv2.setPreserveRatio(true);
        iv2.setSmooth(true);
        iv2.setCache(true);
        Button button = new Button("Chat Channel", iv2);
        hBox1.getChildren().add(button);
        return hBox1;
    }

    /**
     * This HBox method creates a voice channel button below the chat channel button
     * The image is getting accessed and displayed next to the button
     * Button getting added to the hbox
     *
     * @return {@link#hBox2}
     */
    //Hbox(button 2)
    private HBox VoiceChannel() {
        HBox hBox2 = new HBox(100);
        Image hashtag = new Image("Images/speaker.png");
        //hBox.setPadding(new Insets(15,15,15,15));
        ImageView iv3 = new ImageView();
        iv3.setImage(hashtag);
        iv3.setFitWidth(25);
        iv3.setPreserveRatio(true);
        iv3.setSmooth(true);
        iv3.setCache(true);
        Button button = new Button("Voice Chat Channel", iv3);
        hBox2.getChildren().add(button);
        return hBox2;
    }
}
