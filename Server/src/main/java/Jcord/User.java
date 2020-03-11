package Jcord;

import java.io.Serializable;

import javafx.scene.image.Image;

public class User implements Serializable{
    private String userName;
    private transient Image profilePicture;
    private boolean isOnline;

    public User(String name, Image profilePic){
        this.userName = name;
        this.profilePicture = profilePic;
    }

    public void setUsernmane(String userName){
        this.userName = userName;
    }

    public void setProfilePicture(Image picture){
        this.profilePicture = picture;
    }

    public void setIsOnline(boolean active){
        this.isOnline = active;
    }
    
    public String getUsername(){
        return this.userName;
    }

    public Image getProfilePicture(){
        return this.profilePicture;
    }

    public boolean getisOnline(){

        return this.isOnline;
    }



}