package pt.isec.tp.amov.Game;

import java.io.Serializable;
import java.util.ArrayList;

import pt.isec.tp.amov.Constants;
import pt.isec.tp.amov.Game.Pieces.Piece;

import static pt.isec.tp.amov.Constants.ATTACK;
import static pt.isec.tp.amov.Constants.BISHOP_1;
import static pt.isec.tp.amov.Constants.BISHOP_2;
import static pt.isec.tp.amov.Constants.ERRO;
import static pt.isec.tp.amov.Constants.KING_1;
import static pt.isec.tp.amov.Constants.KING_2;
import static pt.isec.tp.amov.Constants.KNIGHT_1;
import static pt.isec.tp.amov.Constants.KNIGHT_2;
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
    private Player toPlay;

    public Board(Player p) {
        this.board = new Squares[8][8];
        this.toPlay = p;
        this.tiles = new ArrayList<>();
    }


    public Squares[][] getBoard() {
        return board;
    }

    public void setBoard(Squares[][] board) {
        this.board = board;
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
                    paintPossibleMovesPawn(peca);
                    break;
                case ROOK_1:
                case ROOK_2:
                    rePaintBoard(this);
                    peca.move(getToPlay());
                    peca.limitar(this);
                    paintPossibleMoves(peca);
                    break;
                case KNIGHT_1:
                case KNIGHT_2:
                    rePaintBoard(this);
                    peca.move(getToPlay());
                    paintPossibleMoves(peca);
                    break;
                case KING_1:
                case KING_2:
                    rePaintBoard(this);
                    peca.move(getToPlay());
                    paintPossibleMoves(peca);
                    break;
                case BISHOP_1:
                case BISHOP_2:
                    rePaintBoard(this);
                    peca.move(getToPlay());
                    peca.limitar(this);
                    paintPossibleMoves(peca);
                    break;
                case QUEEN_1:
                case QUEEN_2:
                    rePaintBoard(this);
                    peca.move(getToPlay());
                    peca.limitar(this);
                    paintPossibleMoves(peca);
                    break;
            }
        } else if (idJogador == ATTACK) {
            if (s.getColor() == POSSIBLE_MOVE) {
                Player p = getToPlay();
                p.getToMove().ataca(position, s);
                p.setFirstMove(false);

                //REMOVE A PECA DO SITIO ONDE ESTAVA (ANTES DE ATACAR)
                Piece pecaAMover = this.getToPlay().getToMove();
                this.getTiles().get(pecaAMover.getX() * 8 + pecaAMover.getY()).setPiece(null);
                this.getTiles().get(pecaAMover.getX() * 8 + pecaAMover.getY()).setOccupied(false);

                //Atualizar coordenadas da peça que foi introduzida na celula xy
                pecaAMover.setX(s.getX());
                pecaAMover.setY(s.getY());
                p.setMode(2);

                rePaintBoard(this);
            }
        } else if (idJogador == ERRO) {
            toPlay.setMode(1);
        }

        /*else{
            s.getPiece().setType(this.getToPlay().getToMove().getType());
            this.getToPlay().possibilidadesMover.clear();
            rePaintBoard(this);
            this.getToPlay().getToMove().ataca(position, s);
        }*/
    }

    public int whichPlayer(Squares s) {
        //Apos clicar na peça, ver se realmente a peça que foi acionada pertence ao grupo
        //de peças do jogador em questao
        if (s.getPiece() == null) return ATTACK;

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

    public void paintPossibleMovesPawn(Piece p) {
        int index = 0;
        for (int i = 0; i < p.getPossibilidades().size() / 2; i++) {
            int x = p.getPossibilidades().get(index);
            ++index;
            int y = p.getPossibilidades().get(index);
            this.getToPlay().setMode(1);
            this.getTiles().get(x * 8 + y).setColor(POSSIBLE_MOVE);
            ++index;
        }
    }

    public void paintPossibleMoves(Piece p) {
        for (int i = 0; i < p.getPossibilidades().size(); i++) {
            this.getTiles().get(p.getPossibilidades().get(i)).setColor(POSSIBLE_MOVE);
        }
    }
}

    /*public void pawnMove(Board b, Squares s){
        if(whichPlayer(s) != ERRO) {
            int x = s.getX();
            int y = s.getY();
            b.getToPlay().setToMove(s.getType());

            if(b.getToPlay().getID() == 1) {
                if (b.getToPlay().isFirstMove() == true) {
                    int[] c = {s.getID() - 16, s.getID() - 8};
                    b.getToPlay().guardaPossibilidades(c);
                    for (int i = 0; i < c.length; i++) {
                        b.getSquares().get(c[i]).setOccupied(false);
                        b.getSquares().get(c[i]).setColor(POSSIBLE_MOVE);
                        b.getSquares().get(c[i]).setType(POSSIBLE_MOVE);
                    }
                } else {
                    int[] c = {s.getID() - 8};
                    b.getToPlay().guardaPossibilidades(c);
                    b.getSquares().get(c[0]).setOccupied(false);
                    b.getSquares().get(c[0]).setColor(POSSIBLE_MOVE);
                    b.getSquares().get(c[0]).setType(POSSIBLE_MOVE);
                }
                int[] posAtual = {s.getID()};
                b.getToPlay().guardaPossibilidades(posAtual);
            }else{
                if (b.getToPlay().isFirstMove() == true) {
                    int[] c = {s.getID() + 16, s.getID() + 8};
                    b.getToPlay().guardaPossibilidades(c);
                    for (int i = 0; i < c.length; i++) {
                        b.getSquares().get(c[i]).setOccupied(false);
                        b.getSquares().get(c[i]).setColor(POSSIBLE_MOVE);
                        b.getSquares().get(c[i]).setType(POSSIBLE_MOVE);
                    }
                } else {
                    int[] c = {s.getID() + 8};
                    b.getToPlay().guardaPossibilidades(c);
                    b.getSquares().get(c[0]).setOccupied(false);
                    b.getSquares().get(c[0]).setColor(POSSIBLE_MOVE);
                    b.getSquares().get(c[0]).setType(POSSIBLE_MOVE);
                }
                int[] posAtual = {s.getID()};
                b.getToPlay().guardaPossibilidades(posAtual);


            }
        }else{
            //TODO: QUANDO A PECA NAO é DO TEU GRUPO DE PECAS
        }
    }

    public boolean ataca(Board b, Squares s){
        //TODO: QUANDO O SQUARE E VERMELHO NAO DEVE MUDAR, OU SEJA PODE CONTINUAR O JOGO (O MESMO JOGADOR)
        //Faz a movimentacao da peca
        b.getSquares().get(s.getID()).setType(b.getToPlay().getToMove());
        b.getSquares().get(s.getID()).setOccupied(true);
        b.getToPlay().setFirstMove(false);

        eliminaPossibilidadeMover(s.getID());

        //Desocupamos a posicao anterior + remetemos o tabuleiro sem as possibilidades
        for(int i=0; i < b.getToPlay().getPossibilidadesMover().size();i++){
            b.getSquares().get(b.getToPlay().getPossibilidadesMover().get(i)).setOccupied(false);
        }
        rePaintBoard(this);

        for(int i=0;i<b.getToPlay().getPossibilidadesMover().size();i++)
            b.getToPlay().getPossibilidadesMover().remove(i);
        return true;
    }

    public void eliminaPossibilidadeMover(int p){
        for(int i=0; i < this.getToPlay().getPossibilidadesMover().size();i++) {
            if (this.getToPlay().getPossibilidadesMover().get(i) == p) {
                this.getToPlay().getPossibilidadesMover().remove(i);
            }
        }
        }*/
