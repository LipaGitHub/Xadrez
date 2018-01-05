package pt.isec.tp.amov.Game;

import java.io.Serializable;

import pt.isec.tp.amov.Game.Pieces.Piece;

/**
 * Created by Fajardo on 12/12/2017.
 */

public class Squares implements Serializable {
    private int ID = 0;
    private int x;          // valor da coluna
    private int y;          // valor da linha
    private int color;      // cor
    private boolean occupied; // ocupado
    private Piece piece;

    public Squares(int id, int x, int y){
        this.ID = id;
        this.x = x;
        this.y = y;
        this.occupied = false;
        this.piece = null;
    }

    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getColor() { return color; }
    public void setColor(int color) { this.color = color; }
    public boolean isOccupied() { return occupied; }
    public void setOccupied(boolean occupied) { this.occupied = occupied; }
    public Piece getPiece() { return piece; }
    public void setPiece(Piece piece) { this.piece = piece; }
}