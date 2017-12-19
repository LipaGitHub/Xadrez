package pt.isec.tp.amov.Game.Pieces;

import java.io.Serializable;
import java.util.ArrayList;

import pt.isec.tp.amov.Game.Board;
import pt.isec.tp.amov.Game.Player;
import pt.isec.tp.amov.Game.Squares;

import static pt.isec.tp.amov.Constants.ATTACK;
import static pt.isec.tp.amov.Constants.ERRO;
import static pt.isec.tp.amov.Constants.MOVE;

/**
 * Created by Fajardo on 12/12/2017.
 */

public abstract class Piece implements Serializable {
    int x, y;
    int type;
    ArrayList<Integer> possibleMoves;
    ArrayList<Integer> criticalMoves;

    public Piece(int type){
        this.type = type;
        this.possibleMoves = new ArrayList<>();
        this.criticalMoves = new ArrayList<>();
    }
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getType() { return type; }
    public void setType(int type) { this.type = type; }
    public ArrayList<Integer> getPossibleMoves() { return possibleMoves; }
    public void setPossibleMoves(ArrayList<Integer> possibleMoves) { this.possibleMoves = possibleMoves; }
    public ArrayList<Integer> getCriticalMoves() { return criticalMoves; }
    public void setCriticalMoves(ArrayList<Integer> criticalMoves) { this.criticalMoves = criticalMoves; }

    public void move(Player p){}
    public void possibleMoves(Board b){}
    public void limitar(Board b){}
    public int ataca(Player p, Squares s){ return ATTACK;}
}