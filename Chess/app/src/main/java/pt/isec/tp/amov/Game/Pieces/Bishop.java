package pt.isec.tp.amov.Game.Pieces;

import java.util.ArrayList;

import pt.isec.tp.amov.Game.Board;
import pt.isec.tp.amov.Game.Player;
import pt.isec.tp.amov.Game.Squares;
import pt.isec.tp.amov.MainActivity;

/**
 * Created by Fajardo on 12/12/2017.
 */

public class Bishop extends Piece {
    public Bishop(int type, int x, int y) {
        super(type);
        this.setX(x);
        this.setY(y);
    }

    @Override
    public void move(Player p){
        p.setToMove(this);
        possibilidades.clear();
        p.setMode(1);
    }

    @Override
    public void limitar(Board b){
        ArrayList<Integer> amarelos = new ArrayList<>();
        ArrayList<Integer> discartar = new ArrayList<>();
        ArrayList<Integer> vermelhos = new ArrayList<>();
        int posAtualPeca = b.getToPlay().getToMove().getX()*8+b.getToPlay().getToMove().getY();

        int linhaBegin = b.getToPlay().getToMove().getX()*8;
        int linhaEnd = ((b.getToPlay().getToMove().getX()+1)*8)-1;
        int colunaBegin = b.getToPlay().getToMove().getY();
        int colunaEnd = 7*8+b.getToPlay().getToMove().getY();

        int difDireita = linhaEnd - posAtualPeca;
        int difEsquerda = posAtualPeca - linhaBegin;
        int difTop = (posAtualPeca - b.getToPlay().getToMove().getY())/8;
        int difBottom = (colunaEnd - posAtualPeca)/8;

        //CANTO SUPERIOR DIREITO
        int total = 1;
            for(int t = 0; t < Math.min(difDireita, difTop); t++){
                int index = (b.getToPlay().getToMove().getX()-total)*8+b.getToPlay().getToMove().getY()+total;
                if(b.getTiles().get(index).isOccupied() == true) {
                    for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                        if (b.getTiles().get(index).getX() == b.getToPlay().getPieces().get(j).getX() &&
                                b.getTiles().get(index).getY() == b.getToPlay().getPieces().get(j).getY()) {
                            discartar.add(index);
                            for(int k=1;k<=difDireita;k++) {
                                discartar.add(index-7*k);
                            }
                            t = difTop + 1;
                            break;
                        }else{
                            vermelhos.add(index);
                        }
                    }
                }else{
                    amarelos.add(index);
                    total++;
                }
            }

        //CANTO INFERIOR DIREITO
        total = 1;
            for(int t=0; t < Math.min(difDireita, difBottom); t++){
                int index = (b.getToPlay().getToMove().getX()+total)*8+b.getToPlay().getToMove().getY()+total;
                if(b.getTiles().get(index).isOccupied() == true) {
                    for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                        if (b.getTiles().get(index).getX() == b.getToPlay().getPieces().get(j).getX() &&
                                b.getTiles().get(index).getY() == b.getToPlay().getPieces().get(j).getY()) {
                            discartar.add(index);
                            for(int k=1;k<=difDireita;k++) {
                                discartar.add(index+9*k);
                            }
                            t = difBottom + 1;
                            break;
                        }else{
                            vermelhos.add(index);
                        }
                    }
                }else{
                    amarelos.add(index);
                    total++;
                }
            }

        //CANTO SUPERIOR ESQUERDO
        total = 1;
            for(int t=0; t < Math.min(difEsquerda, difTop); t++){
                int index = (b.getToPlay().getToMove().getX()-total)*8+b.getToPlay().getToMove().getY()-total;
                if(b.getTiles().get(index).isOccupied() == true) {
                    for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                        if (b.getTiles().get(index).getX() == b.getToPlay().getPieces().get(j).getX() &&
                                b.getTiles().get(index).getY() == b.getToPlay().getPieces().get(j).getY()) {
                            discartar.add(index);
                            for(int k=1;k<=difDireita;k++) {
                                discartar.add(index-9*k);
                            }
                            t = difTop + 1;
                            break;
                        }else{
                            vermelhos.add(index);
                        }
                    }
                }else{
                    amarelos.add(index);
                    total++;
                }
            }

        //CANTO INFERIOR ESQUERDO
        total = 1;
            for(int t=0; t < Math.min(difEsquerda, difBottom); t++){
                int index = (b.getToPlay().getToMove().getX()+total)*8+b.getToPlay().getToMove().getY()-total;
                if(b.getTiles().get(index).isOccupied() == true) {
                    for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                        if (b.getTiles().get(index).getX() == b.getToPlay().getPieces().get(j).getX() &&
                                b.getTiles().get(index).getY() == b.getToPlay().getPieces().get(j).getY()) {
                            discartar.add(index);
                            for(int k=1;k<=difDireita;k++) {
                                discartar.add(index+7*k);
                            }
                            t = difBottom + 1;
                            break;
                        }else{
                            vermelhos.add(index);
                        }
                    }
                }else{
                    amarelos.add(index);
                    total++;
                }
            }

        possibilidades.clear();
        for(int i=0; i < amarelos.size();i++){
            possibilidades.add(amarelos.get(i));
        }

    }

    @Override
    public void ataca(int position, Squares s){
        if(s.isOccupied() == false) {
            for(int i=0; i < possibilidades.size(); i++){
                if(getPossibilidades().get(i) == position) {
                    s.setPiece(this);
                    s.setOccupied(true);
                    break;
                }
            }
        }
    }
}
