package pt.isec.tp.amov;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

import pt.isec.tp.amov.Game.Board;

/**
 * Created by Fajardo on 05/01/2018.
 */

public class Profile implements Serializable{
    private String name;
    private String img;
    private int victories;
    private ArrayList<Board> profilegames;

    Profile(String n, String img){
        this.name = n;
        this.img = img;
        this.victories = 0;
        this.profilegames = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getVictories() {
        return victories;
    }

    public void setVictories(int victories) {
        this.victories = victories;
    }

    public ArrayList<Board> getProfilegames() { return profilegames; }

    public void setProfilegames(ArrayList<Board> profilegames) { this.profilegames = profilegames; }
}
