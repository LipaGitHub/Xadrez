package pt.isec.tp.amov.Game.Pieces;

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

public class Queen extends Piece {
    public Queen(int type, int x, int y) {
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
        int total = 1; int ate = 0;
        for(int t = 0; t < Math.min(difDireita, difTop); t++){
            int index = (b.getToPlay().getToMove().getX()-total)*8+b.getToPlay().getToMove().getY()+total;
            if(b.getTiles().get(index).isOccupied() == true) {
                for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                    if (b.getTiles().get(index).getX() == b.getToPlay().getPieces().get(j).getX() &&
                            b.getTiles().get(index).getY() == b.getToPlay().getPieces().get(j).getY()) {
                        discartar.add(index);
                        ate = 1;
                        for(int k=1;k<=difDireita;k++) {
                            discartar.add(index-7*k);
                        }
                        t = difTop + 1;
                        break;
                    }
                }
                if(ate != 1) criticalMoves.add(index);
            }else{
                possibleMoves.add(index);
                total++;
            }
        }

        //CANTO INFERIOR DIREITO
        total = 1; ate = 0;
        for(int t=0; t < Math.min(difDireita, difBottom); t++){
            int index = (b.getToPlay().getToMove().getX()+total)*8+b.getToPlay().getToMove().getY()+total;
            if(b.getTiles().get(index).isOccupied() == true) {
                for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                    if (b.getTiles().get(index).getX() == b.getToPlay().getPieces().get(j).getX() &&
                            b.getTiles().get(index).getY() == b.getToPlay().getPieces().get(j).getY()) {
                        discartar.add(index);
                        ate = 1;
                        for(int k=1;k<=difDireita;k++) {
                            discartar.add(index+9*k);
                        }
                        t = difBottom + 1;
                        break;
                    }
                }
                if(ate != 1) criticalMoves.add(index);
            }else{
                possibleMoves.add(index);
                total++;
            }
        }

        //CANTO SUPERIOR ESQUERDO
        total = 1; ate = 0;
        for(int t=0; t < Math.min(difEsquerda, difTop); t++){
            int index = (b.getToPlay().getToMove().getX()-total)*8+b.getToPlay().getToMove().getY()-total;
            if(b.getTiles().get(index).isOccupied() == true) {
                for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                    if (b.getTiles().get(index).getX() == b.getToPlay().getPieces().get(j).getX() &&
                            b.getTiles().get(index).getY() == b.getToPlay().getPieces().get(j).getY()) {
                        discartar.add(index);
                        ate = 1;
                        for(int k=1;k<=difDireita;k++) {
                            discartar.add(index-9*k);
                        }
                        t = difTop + 1;
                        break;
                    }
                }
                if(ate != 1) criticalMoves.add(index);
            }else{
                possibleMoves.add(index);
                total++;
            }
        }

        //CANTO INFERIOR ESQUERDO
        total = 1; ate = 0;
        for(int t=0; t < Math.min(difEsquerda, difBottom); t++){
            int index = (b.getToPlay().getToMove().getX()+total)*8+b.getToPlay().getToMove().getY()-total;
            if(b.getTiles().get(index).isOccupied() == true) {
                for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                    if (b.getTiles().get(index).getX() == b.getToPlay().getPieces().get(j).getX() &&
                            b.getTiles().get(index).getY() == b.getToPlay().getPieces().get(j).getY()) {
                        discartar.add(index);
                        ate = 1;
                        for(int k=1;k<=difDireita;k++) {
                            discartar.add(index+7*k);
                        }
                        t = difBottom + 1;
                        break;
                    }
                }
                if(ate != 1) criticalMoves.add(index);
            }else{
                possibleMoves.add(index);
                total++;
            }
        }

        total = 1; ate = 0;
        for(int i=0; i < difDireita;i++){
            int index = b.getToPlay().getToMove().getX()*8+b.getToPlay().getToMove().getY()+total;
            if(b.getTiles().get(index).isOccupied() == true) {
                for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                    if (b.getTiles().get(index).getX() == b.getToPlay().getPieces().get(j).getX() &&
                            b.getTiles().get(index).getY() == b.getToPlay().getPieces().get(j).getY()) {
                        discartar.add(index);
                        ate = 1;
                        for(int k=index+1;k<=linhaEnd;k++)
                            discartar.add(k);
                        i = difDireita + 1; ///excluir tudo que esteja à frente
                        break;
                    }
                }
                if(ate != 1) criticalMoves.add(index);
            }else{
                possibleMoves.add(index);
                total++;
            }
        }

        total = 1; ate = 0;
        for(int i=0; i < difEsquerda;i++){
            int index = b.getToPlay().getToMove().getX()*8+b.getToPlay().getToMove().getY()-total;
            if(b.getTiles().get(index).isOccupied() == true) {
                for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                    if (b.getTiles().get(index).getX() == b.getToPlay().getPieces().get(j).getX() &&
                            b.getTiles().get(index).getY() == b.getToPlay().getPieces().get(j).getY()) {
                        discartar.add(index);
                        ate = 1;
                        for(int k=index-1;k<=linhaEnd;k++)
                            discartar.add(k);
                        i = difEsquerda + 1; ///excluir tudo que esteja à frente
                        break;
                    }
                }
                if(ate != 1) criticalMoves.add(index);
            }else{
                possibleMoves.add(index);
                total++;
            }
        }

        total = 1; ate = 0;
        for(int i=0; i < difTop;i++){
            int index = (b.getToPlay().getToMove().getX()-total)*8+b.getToPlay().getToMove().getY();
            if(b.getTiles().get(posAtualPeca-(total*8)).isOccupied() == true) {
                for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                    if (b.getTiles().get(posAtualPeca-(total*8)).getX() == b.getToPlay().getPieces().get(j).getX() &&
                            b.getTiles().get(posAtualPeca-(total*8)).getY() == b.getToPlay().getPieces().get(j).getY()) {
                        //discartar.add(posAtualPeca-(total*8));
                        ate = 1;
                        for(int k=1;k<difTop;k++) {
                            discartar.add(k-8*k);
                            ++total;
                        }
                        i = difTop + 1; ///excluir tudo que esteja à frente
                        break;
                    }
                }
                if(ate != 1) criticalMoves.add(posAtualPeca-(total*8));
            }else{
                possibleMoves.add(posAtualPeca-(total*8));
                total++;
            }
        }

        total = 1; ate = 0;
        for(int i=0; i < difBottom;i++){
            int index = (b.getToPlay().getToMove().getX()+total)*8+b.getToPlay().getToMove().getY();
            if(b.getTiles().get(index).isOccupied() == true) {
                for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                    if (b.getTiles().get(index).getX() == b.getToPlay().getPieces().get(j).getX() &&
                            b.getTiles().get(index).getY() == b.getToPlay().getPieces().get(j).getY()) {
                        //discartar.add(posAtualPeca+(total*8));
                        ate = 1;
                        for(int k=1;k<=difBottom;k++) {
                            discartar.add(k+8*k);
                            ++total;
                        }
                        i = difBottom + 1; ///excluir tudo que esteja à frente
                        break;
                    }
                }
                if(ate != 1) criticalMoves.add(index);
            }else{
                possibleMoves.add(index);
                total++;
            }
        }
    }

    @Override
    public int ataca(Player p, Squares s){
        if(s.isOccupied() == false) {
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
