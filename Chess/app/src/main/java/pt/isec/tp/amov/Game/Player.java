package pt.isec.tp.amov.Game;

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

public class Player {
    static int contador = 1;
    int ID;
    String nome;
    boolean firstMove;
    Piece toMove;                 //peça a ser movida
    int mode;   //0: REST; 1: MOVE; 2: ATTACK
    ArrayList<Piece> pieces;
    ArrayList<Integer> possibilidadesMover;

    public Player(){
        this.ID = contador++;
        this.firstMove = true;
        this.mode = 0;
        this.pieces = new ArrayList<>();
        this.possibilidadesMover = new ArrayList<>();
    }

    public Player(String nome) {
        this.ID = contador++;
        this.nome = nome;
        this.firstMove = true;
        this.mode = 0;
        this.pieces = new ArrayList<>();
        this.possibilidadesMover = new ArrayList<>();
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

    public ArrayList<Integer> getPossibilidadesMover() {
        return possibilidadesMover;
    }

    public void setPossibilidadesMover(ArrayList<Integer> possibilidadesMover) {
        this.possibilidadesMover = possibilidadesMover;
    }

    public void guardaPossibilidades(int[] p){
        for(int i=0; i < p.length;i++)
            this.possibilidadesMover.add(p[i]);
    }

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
            for (int i = 0; i < 8; i++) {
                this.pieces.add(new Pawn(Constants.PAWN_2, 1, i));
            }
            this.pieces.add(new Rook(Constants.ROOK_2, 0, 0));
            this.pieces.add(new Knight(Constants.KNIGHT_2, 0, 1));
            this.pieces.add(new Bishop(Constants.BISHOP_2, 0, 2));
            this.pieces.add(new Queen(Constants.QUEEN_2, 0, 4));
            this.pieces.add(new King(Constants.KING_2, 0, 3));
            this.pieces.add(new Bishop(Constants.BISHOP_2, 0, 5));
            this.pieces.add(new Knight(Constants.KNIGHT_2, 0, 6));
            this.pieces.add(new Rook(Constants.ROOK_2, 0, 7));
        }
    }
}