package pt.isec.tp.amov.Game;

import java.io.Serializable;
import java.util.ArrayList;

import pt.isec.tp.amov.Constants;
import pt.isec.tp.amov.Game.Pieces.Bishop;
import pt.isec.tp.amov.Game.Pieces.King;
import pt.isec.tp.amov.Game.Pieces.Knight;
import pt.isec.tp.amov.Game.Pieces.Pawn;
import pt.isec.tp.amov.Game.Pieces.Piece;
import pt.isec.tp.amov.Game.Pieces.Queen;
import pt.isec.tp.amov.Game.Pieces.Rook;

/**
 * Created by Fajardo on 12/12/2017.
 */

public class Player implements Serializable{
    private int ID;
    private String nome;
    boolean firstMove;
    private Piece toMove;                 //peça a ser movida
    private int mode;   //0: REST; 1: MOVE; 2: ATTACK
    private ArrayList<Piece> pieces;
    private ArrayList<Piece> eatenPieces;
    private Thread timer;

    public Player(int id){
        this.ID = id;
        this.firstMove = true;
        this.mode = 0;
        this.pieces = new ArrayList<>();
        this.eatenPieces = new ArrayList<>();
    }

    public Player(int id, String nome) {
        this.ID = id;
        this.nome = nome;
        this.firstMove = true;
        this.mode = 0;
        this.pieces = new ArrayList<>();
        this.eatenPieces = new ArrayList<>();
    }

    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }
    public boolean isFirstMove() { return firstMove; }
    public void setFirstMove(boolean firstMove) { this.firstMove = firstMove; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public Piece getToMove() {
        return toMove;
    }

    public void setToMove(Piece toMove) {
        this.toMove = toMove;
    }

    public ArrayList<Piece> getPieces() { return pieces; }
    public void setPieces(ArrayList<Piece> pieces) { this.pieces = pieces; }

    public ArrayList<Piece> getEatenPieces() { return eatenPieces; }
    public void setEatenPieces(ArrayList<Piece> eatenPieces) {
        this.eatenPieces = eatenPieces;
    }
    public Thread getTimer() { return timer; }
    public void setTimer(Thread timer) { this.timer = timer; }

    public void initializePieces() {
        if (ID == 1) {
            for (int i = 0; i < 8; i++) {
                this.pieces.add(new Pawn(Constants.PAWN_1, 6, i));
            }
            this.pieces.add(new Rook(Constants.ROOK_1, 7, 0));
            this.pieces.add(new Knight(Constants.KNIGHT_1, 7, 1));
            this.pieces.add(new Bishop(Constants.BISHOP_1, 7, 2));
            this.pieces.add(new Queen(Constants.QUEEN_1, 7, 3));
            this.pieces.add(new King(Constants.KING_1, 7, 4));
            this.pieces.add(new Bishop(Constants.BISHOP_1, 7, 5));
            this.pieces.add(new Knight(Constants.KNIGHT_1, 7, 6));
            this.pieces.add(new Rook(Constants.ROOK_1, 7, 7));
        }else{
            this.pieces.add(new Rook(Constants.ROOK_2, 0, 0));
            this.pieces.add(new Knight(Constants.KNIGHT_2, 0, 1));
            this.pieces.add(new Bishop(Constants.BISHOP_2, 0, 2));
            this.pieces.add(new Queen(Constants.QUEEN_2, 0, 3));
            this.pieces.add(new King(Constants.KING_2, 0, 4));
            this.pieces.add(new Bishop(Constants.BISHOP_2, 0, 5));
            this.pieces.add(new Knight(Constants.KNIGHT_2, 0, 6));
            this.pieces.add(new Rook(Constants.ROOK_2, 0, 7));
            for (int i = 0; i < 8; i++) {
                this.pieces.add(new Pawn(Constants.PAWN_2, 1, i));
            }
        }
    }
}