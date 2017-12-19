package pt.isec.tp.amov.Game.Pieces;

import pt.isec.tp.amov.Game.Board;
import pt.isec.tp.amov.Game.Player;
import pt.isec.tp.amov.Game.Squares;

import static pt.isec.tp.amov.Constants.ATTACK;
import static pt.isec.tp.amov.Constants.ERRO;
import static pt.isec.tp.amov.Constants.MOVE;

/**
 * Created by Fajardo on 12/12/2017.
 */

public class King extends Piece {

    public King(int type, int x, int y) {
        super(type);
        this.setX(x);
        this.setY(y);
    }

    @Override
    public void move(Player p) {
        p.setToMove(this);
        p.setMode(1);
    }

    @Override
    public void possibleMoves(Board b){
        possibleMoves.clear();
        criticalMoves.clear();

        int x = b.getToPlay().getToMove().getX();
        int y = b.getToPlay().getToMove().getY();

        //CANTO SUPERIOR ESQUERDO
        int ate = 0;
        int index = (b.getToPlay().getToMove().getX()-1)*8+b.getToPlay().getToMove().getY()-1;
        if(x > 0 && y > 0) {
            if (b.getTiles().get(index).isOccupied()) {
                for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                    if (b.getTiles().get(index).getX() == b.getToPlay().getPieces().get(j).getX() &&
                            b.getTiles().get(index).getY() == b.getToPlay().getPieces().get(j).getY()) {
                        ate = 1;
                    }
                }
                if (ate != 1) criticalMoves.add(index);
            } else possibleMoves.add(index);
        }

        //CANTO SUPERIOR
        ate = 0;
        index = (b.getToPlay().getToMove().getX()-1)*8+b.getToPlay().getToMove().getY();
        if(x > 0) {
            if (b.getTiles().get(index).isOccupied()) {
                for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                    if (b.getTiles().get(index).getX() == b.getToPlay().getPieces().get(j).getX() &&
                            b.getTiles().get(index).getY() == b.getToPlay().getPieces().get(j).getY()) {
                        ate = 1;
                    }
                }
                if (ate != 1) criticalMoves.add(index);
            } else possibleMoves.add(index);
        }

        //CANTO SUPERIOR DIREITO
        ate = 0;
        index = (b.getToPlay().getToMove().getX()-1)*8+b.getToPlay().getToMove().getY()+1;
        if(x > 0 && y < 7) {
            if (b.getTiles().get(index).isOccupied()) {
                for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                    if (b.getTiles().get(index).getX() == b.getToPlay().getPieces().get(j).getX() &&
                            b.getTiles().get(index).getY() == b.getToPlay().getPieces().get(j).getY()) {
                        ate = 1;
                    }
                }
                if (ate != 1) criticalMoves.add(index);
            } else possibleMoves.add(index);
        }

        //CANTO MEDIO ESQUERDO
        ate = 0;
        index = (b.getToPlay().getToMove().getX())*8+b.getToPlay().getToMove().getY()-1;
        if(y > 0) {
            if (b.getTiles().get(index).isOccupied()) {
                for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                    if (b.getTiles().get(index).getX() == b.getToPlay().getPieces().get(j).getX() &&
                            b.getTiles().get(index).getY() == b.getToPlay().getPieces().get(j).getY()) {
                        ate = 1;
                    }
                }
                if (ate != 1) criticalMoves.add(index);
            } else possibleMoves.add(index);
        }

        //CANTO MEDIO DIREITO
        ate = 0;
        index = (b.getToPlay().getToMove().getX())*8+b.getToPlay().getToMove().getY()+1;
        if(y < 7) {
            if (b.getTiles().get(index).isOccupied()) {
                for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                    if (b.getTiles().get(index).getX() == b.getToPlay().getPieces().get(j).getX() &&
                            b.getTiles().get(index).getY() == b.getToPlay().getPieces().get(j).getY()) {
                        ate = 1;
                    }
                }
                if (ate != 1) criticalMoves.add(index);
            } else possibleMoves.add(index);
        }

        //CANTO INFERIOR ESQUERDO
        ate = 0;
        index = (b.getToPlay().getToMove().getX()+1)*8+b.getToPlay().getToMove().getY()-1;
        if(x < 7 && y > 0) {
            if (b.getTiles().get(index).isOccupied()) {
                for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                    if (b.getTiles().get(index).getX() == b.getToPlay().getPieces().get(j).getX() &&
                            b.getTiles().get(index).getY() == b.getToPlay().getPieces().get(j).getY()) {
                        ate = 1;
                    }
                }
                if (ate != 1) criticalMoves.add(index);
            } else possibleMoves.add(index);
        }

        //CANTO INFERIOR
        ate = 0;
        index = (b.getToPlay().getToMove().getX()+1)*8+b.getToPlay().getToMove().getY();
        if(x < 7) {
            if (b.getTiles().get(index).isOccupied()) {
                for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                    if (b.getTiles().get(index).getX() == b.getToPlay().getPieces().get(j).getX() &&
                            b.getTiles().get(index).getY() == b.getToPlay().getPieces().get(j).getY()) {
                        ate = 1;
                    }
                }
                if (ate != 1) criticalMoves.add(index);
            } else possibleMoves.add(index);
        }

        //CANTO INFERIOR DIREITO
        ate = 0;
        index = (b.getToPlay().getToMove().getX()+1)*8+b.getToPlay().getToMove().getY()+1;
        if(x < 7 && y < 7) {
            if (b.getTiles().get(index).isOccupied()) {
                for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                    if (b.getTiles().get(index).getX() == b.getToPlay().getPieces().get(j).getX() &&
                            b.getTiles().get(index).getY() == b.getToPlay().getPieces().get(j).getY()) {
                        ate = 1;
                    }
                }
                if (ate != 1) criticalMoves.add(index);
            } else possibleMoves.add(index);
        }

    }

    @Override
    public int ataca(Player p, Squares s){
        if(!s.isOccupied()) {
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