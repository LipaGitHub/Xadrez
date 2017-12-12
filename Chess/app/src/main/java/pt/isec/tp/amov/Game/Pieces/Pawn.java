package pt.isec.tp.amov.Game.Pieces;

import java.io.Serializable;

import pt.isec.tp.amov.Game.Player;
import pt.isec.tp.amov.Game.Squares;

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
        possibilidades.clear();
        /*for(int i=0; i < possibilidades.size();i++){
            possibilidades.remove(i);
        }*/
        if(p.getID() == 1) {

            if (p.isFirstMove() == true) {

                x = getX();
                y = getY();

                if (x >= 0 && x < 8 && y >= 0 && y < 8) {

                }//dentro do tabuleiro

                possibilidades.add(x - 1);
                possibilidades.add(y);
                possibilidades.add(x - 2);
                possibilidades.add(y);
                //p.setFirstMove(false);
            } else {
                x = getX();
                y = getY();

                possibilidades.add(x - 1);
                possibilidades.add(y);
            }
        }else{//JOGADOR 2
            if (p.isFirstMove() == true) {

                x = getX();
                y = getY();

                if (x >= 0 && x < 8 && y >= 0 && y < 8) {

                }//dentro do tabuleiro

                possibilidades.add(x + 1);
                possibilidades.add(y);
                possibilidades.add(x + 2);
                possibilidades.add(y);
                //p.setFirstMove(false);
            } else {
                x = getX();
                y = getY();

                possibilidades.add(x + 1);
                possibilidades.add(y);
            }
        }
        p.setMode(1);
    }

    @Override
    public void ataca(int position, Squares s){
        //TODO: QUANDO O SQUARE E VERMELHO NAO DEVE MUDAR, OU SEJA PODE CONTINUAR O JOGO (O MESMO JOGADOR)
        //Faz a movimentacao da peca

        if(s.isOccupied() == false) {
            int index = 0;
            for(int i=0; i < possibilidades.size()/2; i++){
                if(getPossibilidades().get(index) == s.getX() &&
                        getPossibilidades().get(index+1) == s.getY()){
                    s.setPiece(this);
                    s.setOccupied(true);
                    //for (int j = 0; j < possibilidades.size(); j++) {
                    //    possibilidades.remove(j);
                    //}
                    break;
                }
                index = index + 2;
            }
        }
    }
}