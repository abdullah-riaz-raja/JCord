package Jcord;

import java.io.Serializable;
import java.util.Date;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;

/**
 * This class implements {@link Serializable} and stores the user information
 *
 * @see Serializable
 * @see Date
 */
public class User implements Serializable {
    private static final long serialVersionUID = 3610722771006373724L;
    private String userName;
    private byte[]  profilePicture;
    public Date lastActivity;
    private boolean isOnline;
    public int width;
    public int height;

    /**
     * This constructor takes in {link #name} and {link #profilePic} and initializes
     * the object
     *
     * @param name the string of the name
     * @param profilePic the Image of the user
     * @see Image
     * @see Date
     * @see PixelFormat
     * @see #getWidth()
     * @see #getHeight()
     */
    public User(String name, Image profilePic){
        this.userName = name;
        //this.profilePicture = profilePic;
        this.width = (int) profilePic.getWidth();
        this.height = (int) profilePic.getHeight();
        this.profilePicture =  new byte[this.width * this.height * 4];

        this.lastActivity = new Date(System.currentTimeMillis());
       
        profilePic.getPixelReader().getPixels(0, 0, this.width, this.height, PixelFormat.getByteBgraInstance(),
        this.profilePicture, 0, this.width * 4);
    }

    /**
     * This mutator sets the username
     *
     * @param userName string of username
     */
    public void setUsernmane(String userName){
        this.userName = userName;
    }

    /**
     * This mutator sets the profile picture
     *
     * @param picture Image of profile picture
     * @see #getWidth()
     * @see #getHeight()
     * @see PixelFormat
     */
    public void setProfilePicture(Image picture){
        this.width = (int) picture.getWidth();
        this.height = (int) picture.getHeight();
        this.profilePicture =  new byte[this.width * this.height * 4];
        
        picture.getPixelReader().getPixels(0, 0, this.width, this.height, PixelFormat.getByteBgraInstance(),
        this.profilePicture, 0, this.width * 4);
    }

    /**
     * This mutator sets the online status
     *
     * @param active boolean value of being online
     */
    public void setIsOnline(boolean active){
        this.isOnline = active;
    }

    /**
     * This mutator sets the date the user was last seen
     *
     * @param lastActivity Date last seen
     * @see Date
     */
    public void setLastActivity(Date lastActivity){
        this.lastActivity = lastActivity;
    }

    /**
     * This accesor returns the date the user was last seen
     *
     * @return {@link #lastActivity}
     */
    public Date getLastActivity(){
        return lastActivity;
    }

    /**
     * This accessor returns the username of the user
     *
     * @return {@link #userName}
     */
    public String getUsername(){
        return this.userName;
    }

    /**
     * This accessor converts the {@link #profilePicture} into an Image and returns it
     *
     * @return Image of the profile picture
     * @see PixelFormat
     * @see WritableImage
     * @see ImageView
     * @see #profilePicture
     */
    public Image getProfilePicture(){
        // going from byte[] to Image
        WritableImage image3 = new WritableImage(width, height);
        image3.getPixelWriter().setPixels(0, 0, width, height,
        PixelFormat.getByteBgraInstance(),
        this.profilePicture, 0, width * 4);
        

        // need to convert from byte[] to Image
        ImageView image = new ImageView(image3);

        return image.getImage();
    }

    /**
     * This accessor returns the status of the user being online
     *
     * @return {@link #isOnline}
     */
    public boolean getisOnline(){

        return this.isOnline;
    }



}