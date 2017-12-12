package pt.isec.tp.amov.Game;

import java.io.Serializable;

import pt.isec.tp.amov.Game.Pieces.Piece;

/**
 * Created by Fajardo on 12/12/2017.
 */

public class Squares implements Serializable {
    private int ID = 0;
    static int contador;
    private int x;          // valor da coluna
    private int y;          // valor da linha
    private int color;      // cor
    private boolean occupied; // ocupado
    private int ocupadoPorJogador; //0 nao ocupado; 1 pelo jogador 1; 2 pelo jogador 2;
    private Piece piece;

    public Squares(int x, int y){
        this.ID = contador++;
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
    //public int getType() { return type; }
    //public void setType(int type) { this.type = type; }
    public boolean isOccupied() { return occupied; }
    public void setOccupied(boolean ocupied) { this.occupied = ocupied; }

    public Piece getPiece() { return piece; }
    public void setPiece(Piece piece) { this.piece = piece; }
}