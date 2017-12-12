package pt.isec.tp.amov.Game.Pieces;

import java.util.ArrayList;

import pt.isec.tp.amov.Game.Board;
import pt.isec.tp.amov.Game.Player;
import pt.isec.tp.amov.Game.Squares;

/**
 * Created by Fajardo on 12/12/2017.
 */

public class Rook extends Piece {
    public Rook(int type, int x, int y) {
        super(type);
        this.setX(x);
        this.setY(y);
    }

    @Override
    public void move(Player p){
        p.setToMove(this);
        possibilidades.clear();
        Piece peca = p.getToMove();
        int posP = peca.getX()*8+peca.getY();

        //guarda todas as possibilidades no array --> VERTICAL E HORIZONTAL
        for(int i=0; i < 8;i++){
            possibilidades.add(peca.getX() * 8 + i);
        }
        for(int i=0; i < 8;i++){
            possibilidades.add(i * 8 + peca.getY());
        }

        //elimina as possibilidades que ultrapassa o tabuleiro (que vai além das fronteiras do tabuleiro)
        for(int i=0; i < possibilidades.size();i++){
            if(possibilidades.get(i) < 0 || possibilidades.get(i) > 63){
                possibilidades.remove(i);
            }

            //eliminado assim a possibilidade de pintar a celula a amarelo naquela onde o jogador carregou
            if(getPossibilidades().get(i) == posP) {
                possibilidades.remove(i);
            }
        }
        p.setMode(1);
    }

    @Override
    public void limitar(Board b){
        getPossibilidades();
        ArrayList<Integer> amarelos = new ArrayList<>();
        ArrayList<Integer> discartar = new ArrayList<>();
        ArrayList<Integer> vermelhos = new ArrayList<>();
        int posAtualPeca = b.getToPlay().getToMove().getX()*8+b.getToPlay().getToMove().getY();

        int linhaBegin = b.getToPlay().getToMove().getX()*8;
        int linhaEnd = ((b.getToPlay().getToMove().getX()+1)*8)-1;
        int colunaBegin = b.getToPlay().getToMove().getY();
        int colunaEnd = b.getToPlay().getToMove().getX();

        int difDireita = linhaEnd - posAtualPeca;
        int difEsquerda = posAtualPeca - linhaBegin;
        int difTop = Math.abs(posAtualPeca - linhaEnd);
        int difBottom = Math.abs(difTop-colunaEnd);

        int total = 1;
        for(int i=0; i < difDireita;i++){
            if(b.getTiles().get(posAtualPeca+total).isOccupied() == true) {
                for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                    if (b.getTiles().get(posAtualPeca + total).getX() == b.getToPlay().getPieces().get(j).getX() &&
                            b.getTiles().get(posAtualPeca + total).getY() == b.getToPlay().getPieces().get(j).getY()) {
                        discartar.add(posAtualPeca + total);
                        for(int k=posAtualPeca+total+1;k<=linhaEnd;k++)
                            discartar.add(k);
                        i = difDireita + 1; ///excluir tudo que esteja à frente
                        break;
                    }else{
                        vermelhos.add(posAtualPeca+total);
                    }
                }
            }else{
                amarelos.add(posAtualPeca+total);
                total++;
            }
        }

        total = 1;
        for(int i=0; i < difEsquerda;i++){
            if(b.getTiles().get(posAtualPeca+total).isOccupied() == true) {
                for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                    if (b.getTiles().get(posAtualPeca + total).getX() == b.getToPlay().getPieces().get(j).getX() &&
                            b.getTiles().get(posAtualPeca + total).getY() == b.getToPlay().getPieces().get(j).getY()) {
                        discartar.add(posAtualPeca + total);
                        for(int k=posAtualPeca+total+1;k<=linhaEnd;k++)
                            discartar.add(k);
                        i = difEsquerda + 1; ///excluir tudo que esteja à frente
                        break;
                    }else{
                        vermelhos.add(posAtualPeca+total);
                    }
                }
            }else{
                amarelos.add(posAtualPeca+total);
                total++;
            }
        }

        total = 1;
        for(int i=0; i < difTop;i++){
            if(b.getTiles().get(posAtualPeca-(total*8)).isOccupied() == true) {
                for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                    if (b.getTiles().get(posAtualPeca-(total*8)).getX() == b.getToPlay().getPieces().get(j).getX() &&
                            b.getTiles().get(posAtualPeca-(total*8)).getY() == b.getToPlay().getPieces().get(j).getY()) {
                        //discartar.add(posAtualPeca-(total*8));
                        for(int k=posAtualPeca-(total*8);k>=colunaBegin;k=posAtualPeca-(total*8)) {
                            discartar.add(k);
                            ++total;
                        }
                        i = difTop + 1; ///excluir tudo que esteja à frente
                        break;
                    }else{
                        vermelhos.add(posAtualPeca-(total*8));
                    }
                }
            }else{
                amarelos.add(posAtualPeca-(total*8));
                total++;
            }
        }

        total = 1;
        for(int i=0; i < difBottom;i++){
            if(b.getTiles().get(posAtualPeca+(total*8)).isOccupied() == true) {
                for (int j = 0; j < b.getToPlay().getPieces().size(); j++) {
                    if (b.getTiles().get(posAtualPeca+(total*8)).getX() == b.getToPlay().getPieces().get(j).getX() &&
                            b.getTiles().get(posAtualPeca+(total*8)).getY() == b.getToPlay().getPieces().get(j).getY()) {
                        //discartar.add(posAtualPeca+(total*8));
                        for(int k=posAtualPeca+(total*8);k>=colunaEnd;k=posAtualPeca+(total*8)) {
                            discartar.add(k);
                            ++total;
                        }
                        i = difBottom + 1; ///excluir tudo que esteja à frente
                        break;
                    }else{
                        vermelhos.add(posAtualPeca+(total*8));
                    }
                }
            }else{
                amarelos.add(posAtualPeca+(total*8));
                total++;
            }
        }

        for(int i=0; i < possibilidades.size();i++){
            for(int j=0; j < discartar.size();j++){
                if(possibilidades.get(i) == discartar.get(j)) {
                    possibilidades.remove(i);
                }
            }
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