package pt.isec.tp.amov.Game;

import java.io.Serializable;
import java.util.ArrayList;

import pt.isec.tp.amov.Constants;
import pt.isec.tp.amov.Game.Pieces.Piece;

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

    Squares[][] board;
    private ArrayList<Squares> tiles;
    private Player player1, player2;
    private Player toPlay;

    public Board(Player p1, Player p2) {
        this.board = new Squares[8][8];
        this.player1 = p1;
        this.player2 = p2;
        this.toPlay = p1;
        this.tiles = new ArrayList<>();
    }


    public Squares[][] getBoard() {
        return board;
    }

    public void setBoard(Squares[][] board) {
        this.board = board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getToPlay() {
        return toPlay;
    }

    public void setToPlay(Player toPlay) {
        this.toPlay = toPlay;
    }

    public ArrayList<Squares> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Squares> tiles) {
        this.tiles = tiles;
    }

    public void createBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Squares s = new Squares(i, j);
                board[i][j] = s;
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
            board[p1.getPieces().get(i).getX()][p1.getPieces().get(i).getY()].setOccupied(true);
            board[p1.getPieces().get(i).getX()][p1.getPieces().get(i).getY()].setPiece(p1.getPieces().get(i));
        }

        //JOGADOR 2
        for (int i = 0; i < p2.getPieces().size(); i++) {
            board[p2.getPieces().get(i).getX()][p2.getPieces().get(i).getY()].setOccupied(true);
            board[p2.getPieces().get(i).getX()][p2.getPieces().get(i).getY()].setPiece(p2.getPieces().get(i));
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
                //atualiza/remove peça que foi comida do inimigo
                /*if (p.getID() == 1) {
                    for(int j=0; j < p.getEatenPieces().size(); j++) {
                        for (int i = 0; i < player2.getPieces().size(); i++) {
                            if (player2.getPieces().get(i).getX() == p.getEatenPieces().get(j).getX() &&
                                    player2.getEatenPieces().get(i).getY() == p.getEatenPieces().get(j).getY()) {
                                player2.getPieces().remove(i);
                                break;
                            }
                        }
                    }
                } else {
                    for(int j=0; j < p.getEatenPieces().size(); j++) {
                        for (int i = 0; i < player1.getPieces().size(); i++) {
                            if (player1.getPieces().get(i).getX() == p.getEatenPieces().get(j).getX() &&
                                    player1.getEatenPieces().get(i).getY() == p.getEatenPieces().get(j).getY()) {
                                player1.getPieces().remove(i);
                                break;
                            }
                        }
                    }
                }*/
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
        if (getToPlay().equals(p1)) {
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
        if (this.getToPlay() == obj) {
            return true;
        }
        return false;
    }

    public void paintPossibleMoves(Piece p) {
        for (int i = 0; i < p.getPossibleMoves().size(); i++) {
            this.getTiles().get(p.getPossibleMoves().get(i)).setColor(POSSIBLE_MOVE);
        }

        for (int i = 0; i < p.getCriticalMoves().size(); i++) {
            this.getTiles().get(p.getCriticalMoves().get(i)).setColor(CRITICAL_MOVE);
        }
    }
}