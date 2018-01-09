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

public class Pawn extends Piece implements Serializable {
    public Pawn(int type, int x, int y) {
        super(type);
        this.setX(x);
        this.setY(y);
    }

    @Override
    public void move(Player p){
        p.setToMove(this);
        p.setMode(1);
    }

    @Override
    public void possibleMoves(Board b){
        possibleMoves.clear();
        criticalMoves.clear();

        Player p = b.getToPlay();
        int actualPos = this.getX() * 8 + this.getY();

        if(p.getID() == 1) {
            if (p.isFirstMove() == true) {
                for(int i=1; i <= 2; i++){
                    int advance = 8*i;
                    if(b.getTiles().get(actualPos-advance).isOccupied() != true){
                        possibleMoves.add(actualPos-advance);
                    }
                }//guarda as posições pra onde o pião pode mover
            } else {
                if(b.getTiles().get(actualPos-8).isOccupied() != true){
                    possibleMoves.add(actualPos-8);
                }
            }
            int advance = 7;
            for(int i=1; i <=2; i++){
                if((b.getTiles().get(actualPos).getX() - b.getTiles().get(actualPos-advance).getX()) == 1) {
                    if (b.getTiles().get(actualPos - advance).isOccupied() == true) {
                        for (int j = 0; j < b.getPlayer2().getPieces().size(); j++) {//vê se naquela posição se encontra 1 inimigo
                            if (b.getTiles().get(actualPos - advance).getX() == b.getPlayer2().getPieces().get(j).getX() &&
                                    b.getTiles().get(actualPos - advance).getY() == b.getPlayer2().getPieces().get(j).getY()) {
                                criticalMoves.add(actualPos - advance);
                            }//guarda essa posição como possível "comida"
                        }
                    }
                }
                advance = 9;
            }
        }else{//JOGADOR 2
            if (p.isFirstMove() == true) {
                for(int i=1; i <= 2; i++){
                    int advance = 8*i;
                    if(b.getTiles().get(actualPos+advance).isOccupied() != true){
                        possibleMoves.add(actualPos+advance);
                    }
                }//guarda as posições pra onde o pião pode mover
            } else {
                if(b.getTiles().get(actualPos+8).isOccupied() != true){
                    possibleMoves.add(actualPos+8);
                }
            }
            int advance = 7;
            for(int i=1; i <=2; i++){
                if((b.getTiles().get(actualPos+advance).getX() - b.getTiles().get(actualPos).getX()) == 1) {
                    if (b.getTiles().get(actualPos + advance).isOccupied() == true) {
                        for (int j = 0; j < b.getPlayer1().getPieces().size(); j++) {//vê se naquela posição se encontra 1 inimigo
                            if (b.getTiles().get(actualPos + advance).getX() == b.getPlayer1().getPieces().get(j).getX() &&
                                    b.getTiles().get(actualPos + advance).getY() == b.getPlayer1().getPieces().get(j).getY()) {
                                criticalMoves.add(actualPos + advance);
                            }//guarda essa posição como possível "comida"
                        }
                    }
                }
                advance = 9;
            }
        }
    }

    @Override
    public int ataca(Player p, Squares s){
        if(!s.isOccupied()){
            for(int i=0; i < getPossibleMoves().size(); i++){
                if(getPossibleMoves().get(i) == s.getID()){
                    s.setPiece(this);
                    s.setOccupied(true);
                    return MOVE;
                }
            }
        }else{
            for(int i=0; i < getCriticalMoves().size(); i++){
                if(getCriticalMoves().get(i) == s.getID()){
                    p.getEatenPieces().add(s.getPiece());
                    s.setPiece(this);
                    s.setOccupied(true);
                    return ATTACK;
                }
            }
        }
        return ERRO;
    }
}