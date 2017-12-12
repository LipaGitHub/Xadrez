package pt.isec.tp.amov.Game.Pieces;

import pt.isec.tp.amov.Game.Player;
import pt.isec.tp.amov.Game.Squares;

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
    public void move(Player p){
        p.setToMove(this);
        possibilidades.clear();
        Piece peca = p.getToMove();

        //guarda todas as possibilidades no array
        if(peca.getY()-1 >= 0 && peca.getY()-1 <= 7) possibilidades.add((peca.getX()-1)*8+peca.getY()-1);
        if(peca.getY() >= 0 && peca.getY() <= 7) possibilidades.add((peca.getX()-1)*8+peca.getY());
        if(peca.getY()+1 >= 0 && peca.getY()+1 <= 7) possibilidades.add((peca.getX()-1)*8+peca.getY()+1);
        if(peca.getY()-1 >= 0 && peca.getY()-1 <= 7) possibilidades.add((peca.getX())*8+peca.getY()-1);
        if(peca.getY()+1 >= 0 && peca.getY()+1 <= 7) possibilidades.add((peca.getX())*8+peca.getY()+1);
        if(peca.getY()-1 >= 0 && peca.getY()-1 <= 7) possibilidades.add((peca.getX()+1)*8+peca.getY()-1);
        if(peca.getY() >= 0 && peca.getY() <= 7) possibilidades.add((peca.getX()+1)*8+peca.getY());
        if(peca.getY()+1 >= 0 && peca.getY()+1 <= 7) possibilidades.add((peca.getX()+1)*8+peca.getY()+1);

        //elimina as possibilidades que ultrapassa o tabuleiro (que vai além das fronteiras do tabuleiro)
        for(int i=0; i < possibilidades.size();i++){
            if(possibilidades.get(i) < 0 || possibilidades.get(i) > 63){
                possibilidades.remove(i);
                i--;
            }
        }
        p.setMode(1);
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