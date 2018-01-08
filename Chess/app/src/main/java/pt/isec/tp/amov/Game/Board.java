package pt.isec.tp.amov.Game;

import java.io.Serializable;
import java.util.ArrayList;

import pt.isec.tp.amov.Constants;
import pt.isec.tp.amov.Game.Pieces.Piece;
import pt.isec.tp.amov.Game.Pieces.Queen;

import static pt.isec.tp.amov.Constants.ATTACK;
import static pt.isec.tp.amov.Constants.BISHOP_1;
import static pt.isec.tp.amov.Constants.BISHOP_2;
import static pt.isec.tp.amov.Constants.CRITICAL_MOVE;
import static pt.isec.tp.amov.Constants.ERRO;
import static pt.isec.tp.amov.Constants.KING_1;
import static pt.isec.tp.amov.Constants.KING_2;
import static pt.isec.tp.amov.Constants.KNIGHT_1;
import static pt.isec.tp.amov.Constants.KNIGHT_2;
import static pt.isec.tp.amov.Constants.MOVE;
import static pt.isec.tp.amov.Constants.PAWN_1;
import static pt.isec.tp.amov.Constants.PAWN_2;
import static pt.isec.tp.amov.Constants.POSSIBLE_MOVE;
import static pt.isec.tp.amov.Constants.QUEEN_1;
import static pt.isec.tp.amov.Constants.QUEEN_2;
import static pt.isec.tp.amov.Constants.ROOK_1;
import static pt.isec.tp.amov.Constants.ROOK_2;

/**
 * Created by Fajardo on 12/12/2017.
 */

public class Board implements Serializable {

    static int contador = 0;
    private int ID;
    private String name;
    //Squares[][] board;
    private ArrayList<Squares> tiles;
    private Player player1, player2;
    private Player toPlay;
    private int timer;
    private int winner;

    public Board(Player p1, Player p2) {
        this.ID = contador++;
        this.name = "Game #" + contador;
        this.player1 = p1;
        this.player2 = p2;
        this.toPlay = p1;
        this.tiles = new ArrayList<>();
    }

    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Player getPlayer1() { return player1; }
    public void setPlayer1(Player player1) { this.player1 = player1; }
    public Player getPlayer2() { return player2; }
    public void setPlayer2(Player player2) { this.player2 = player2; }
    public Player getToPlay() { return toPlay; }
    public void setToPlay(Player toPlay) { this.toPlay = toPlay; }
    public ArrayList<Squares> getTiles() { return tiles; }
    public void setTiles(ArrayList<Squares> tiles) { this.tiles = tiles; }
    public int getTimer() { return timer; }
    public void setTimer(int timer) { this.timer = timer; }
    public int getWinner() { return winner; }
    public void setWinner(int winner) { this.winner = winner; }

    public void createBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int id = i*8+j;
                Squares s = new Squares(id, i, j);
                tiles.add(s);
            }
        }
    }

    public void paintBoard() {
        for (int i = 0; i < 8; i++) { //LINHAS
            for (int j = 0; j < 8; j++) { //COLUNAS
                if (j % 2 == 0) {
                    if (i % 2 == 0) {
                        //board[i][j].setColor(Constants.CREAM_TILE);
                        tiles.get(i * 8 + j).setColor(Constants.CREAM_TILE);
                    } else {
                        //board[i][j].setColor(Constants.BROWN_TILE);
                        tiles.get(i * 8 + j).setColor(Constants.BROWN_TILE);
                    }
                } else {
                    if (i % 2 == 0) {
                        //board[i][j].setColor(Constants.BROWN_TILE);
                        tiles.get(i * 8 + j).setColor(Constants.BROWN_TILE);
                    } else {
                        //board[i][j].setColor(Constants.CREAM_TILE);
                        tiles.get(i * 8 + j).setColor(Constants.CREAM_TILE);
                    }
                }
            }
        }
    }

    public void rePaintBoard(Board b) {
        int total = 0;
        for (int i = 0; i < 8; i++) { //LINHAS
            for (int j = 0; j < 8; j++) { //COLUNAS
                //if(b.getTiles().get(total).isOccupied() == false) {
                if ((i + j) % 2 == 0) {
                    getTiles().get(total).setColor(Constants.CREAM_TILE);
                } else {
                    getTiles().get(total).setColor(Constants.BROWN_TILE);
                }
                //}
                total++;
            }
        }
    }

    public void showPiecesOnBoard(Player p1, Player p2) {
        //JOGADOR 1
        for (int i = 0; i < p1.getPieces().size(); i++) {
            this.getTiles().get(48+i).setOccupied(true);
            this.getTiles().get(48+i).setPiece(p1.getPieces().get(i));
        }

        //JOGADOR 2
        for (int i = 0; i < p2.getPieces().size(); i++) {
            this.getTiles().get(i).setOccupied(true);
            this.getTiles().get(i).setPiece(p2.getPieces().get(i));
        }
    }

    public void verifyMove(int position) {
        Squares s = this.getTiles().get(position);

        int idJogador = whichPlayer(s);

        if (idJogador == getToPlay().getID()) {
            Piece peca = s.getPiece();
            switch (peca.getType()) {
                case PAWN_1:
                case PAWN_2:
                    rePaintBoard(this);
                    peca.move(getToPlay());
                    peca.possibleMoves(this);
                    paintPossibleMoves(peca);
                    break;
                case ROOK_1:
                case ROOK_2:
                    rePaintBoard(this);
                    peca.move(getToPlay());
                    peca.possibleMoves(this);
                    paintPossibleMoves(peca);
                    break;
                case KNIGHT_1:
                case KNIGHT_2:
                    rePaintBoard(this);
                    peca.move(getToPlay());
                    peca.possibleMoves(this);
                    paintPossibleMoves(peca);
                    break;
                case KING_1:
                case KING_2:
                    rePaintBoard(this);
                    peca.move(getToPlay());
                    peca.possibleMoves(this);
                    paintPossibleMoves(peca);
                    break;
                case BISHOP_1:
                case BISHOP_2:
                    rePaintBoard(this);
                    peca.move(getToPlay());
                    peca.possibleMoves(this);
                    paintPossibleMoves(peca);
                    break;
                case QUEEN_1:
                case QUEEN_2:
                    rePaintBoard(this);
                    peca.move(getToPlay());
                    peca.possibleMoves(this);
                    paintPossibleMoves(peca);
                    break;
            }
        } else if (idJogador == MOVE || idJogador == ATTACK) {
            Player p = getToPlay();
            int res = this.getToPlay().getToMove().ataca(p, s);

            if (res == ATTACK) {
                p.setFirstMove(false);
            }
            if(res == MOVE || res == ATTACK){
                //REMOVE A PECA DO SITIO ONDE ESTAVA (ANTES DE ATACAR)
                Piece pecaAMover = this.getToPlay().getToMove();
                this.getTiles().get(pecaAMover.getX() * 8 + pecaAMover.getY()).setPiece(null);
                this.getTiles().get(pecaAMover.getX() * 8 + pecaAMover.getY()).setOccupied(false);

                //Atualizar coordenadas da peça que foi introduzida na celula xy
                pecaAMover.setX(s.getX());
                pecaAMover.setY(s.getY());
                p.setFirstMove(false);
                p.setMode(2);

                //verifica se o peao se encontra do lado oposto, caso contrario altera p/o tipo escolhido
                pecaAMover = changePawnToOtherPiece(p, pecaAMover);
                if(pecaAMover != null) {
                    this.getTiles().get(pecaAMover.getX() * 8 + pecaAMover.getY()).setPiece(pecaAMover);
                }
                rePaintBoard(this);

            }
        } else if(idJogador == ERRO) {
            toPlay.setMode(1);
        }
    }

    public int whichPlayer(Squares s) {
        //Apos clicar na peça, ver se realmente a peça que foi acionada pertence ao grupo
        //de peças do jogador em questao
        if (s.getPiece() == null && s.getColor() != CRITICAL_MOVE && s.getColor() != POSSIBLE_MOVE) return ERRO;
        if(s.getPiece() == null && s.getColor() == POSSIBLE_MOVE) return MOVE;
        if(s.getPiece() != null && s.getColor() == CRITICAL_MOVE) return ATTACK;
        for (int i = 0; i < getToPlay().getPieces().size(); i++) {
            if (toPlay.getPieces().get(i).getX() == s.getPiece().getX() &&
                    toPlay.getPieces().get(i).getY() == s.getPiece().getY()) {
                return toPlay.getID();
            }
        }
        return ERRO; //erro, ou seja a peça onde clicou nao lhe pertence
    }

    public void changePlayer(Player p1, Player p2) {
        //if (getToPlay().equals(p1)) {
        if (getToPlay().getID() == p1.getID()) {
            if (this.getToPlay().getMode() == 2) {
                this.setToPlay(p2);
            } else {
                this.setToPlay(p1);
            }
        } else {
            if (this.getToPlay().getMode() == 2) {
                this.setToPlay(p1);
            } else {
                this.setToPlay(p2);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getToPlay().getClass() != obj.getClass()) {
            return false;
        }

        final Board other = (Board) obj;
        return this.getName().equals(other.getName());
    }

    public void paintPossibleMoves(Piece p) {
        for (int i = 0; i < p.getPossibleMoves().size(); i++) {
            this.getTiles().get(p.getPossibleMoves().get(i)).setColor(POSSIBLE_MOVE);
        }

        for (int i = 0; i < p.getCriticalMoves().size(); i++) {
            this.getTiles().get(p.getCriticalMoves().get(i)).setColor(CRITICAL_MOVE);
        }
    }

    public boolean kingdefeated(){
        for(int i=0; i < this.getPlayer1().getEatenPieces().size(); i++){
            if(this.getPlayer1().getEatenPieces().get(i).getType() == KING_2){
                this.setWinner(this.getPlayer1().getID());
                return true;
            }
        }

        for(int i=0; i < this.getPlayer2().getEatenPieces().size(); i++){
            if(this.getPlayer1().getEatenPieces().get(i).getType() == KING_1){
                this.setWinner(this.getPlayer2().getID());
                return true;
            }
        }
        return false;
    }

    public Piece changePawnToOtherPiece(Player play, Piece p){
        if(play.getID() == 1){
            //jogador 1
            if(p.getX() == 0 && p.getType() == Constants.PAWN_1){
                for(int i=0; i < play.getPieces().size(); i++){
                    if(play.getPieces().get(i).getX() == p.getX() &&
                            play.getPieces().get(i).getY() == p.getY()){
                        play.getPieces().remove(i);
                    }
                }
                //p.setType(Constants.QUEEN_1);
                p = new Queen(Constants.QUEEN_1, p.getX(), p.getY());
                play.getPieces().add(p);
                //player1.setPieces(play.getPieces());
                return p;
            }
        }else{
            //jogador 2
            if(p.getX() == 7 && p.getType() == Constants.PAWN_2){
                for(int i=0; i < play.getPieces().size(); i++){
                    if(play.getPieces().get(i).getX() == p.getX() &&
                            play.getPieces().get(i).getY() == p.getY()){
                        play.getPieces().remove(i);
                    }
                }
                p = new Queen(Constants.QUEEN_2, p.getX(), p.getY());
                play.getPieces().add(p);
                //player2.setPieces(play.getPieces());
                return p;
            }
        }
        return null;
    }
}