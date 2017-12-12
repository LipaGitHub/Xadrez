package pt.isec.tp.amov.Game.Pieces;

import java.io.Serializable;
import java.util.ArrayList;

import pt.isec.tp.amov.Game.Board;
import pt.isec.tp.amov.Game.Player;
import pt.isec.tp.amov.Game.Squares;

/**
 * Created by Fajardo on 12/12/2017.
 */

public abstract class Piece implements Serializable {
    int x, y;
    int type;
    ArrayList<Integer> possibilidades;

    public Piece(int type){
        this.type = type; this.possibilidades = new ArrayList<>();
    }
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getType() { return type; }
    public void setType(int type) { this.type = type; }

    public ArrayList<Integer> getPossibilidades() {
        return possibilidades;
    }

    public void setPossibilidades(ArrayList<Integer> possibilidades) {
        this.possibilidades = possibilidades;
    }

    public void move(Player p){}
    public void limitar(Board b){}
    public void ataca(int position, Squares s){}
}