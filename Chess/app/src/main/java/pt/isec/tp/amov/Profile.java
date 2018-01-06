package pt.isec.tp.amov;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Fajardo on 05/01/2018.
 */

public class Profile implements Serializable{
    private String name;
    private String img;
    private int victories;

    Profile(String n, String img){
        this.name = n;
        this.img = img;
        this.victories = 0;
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
}
