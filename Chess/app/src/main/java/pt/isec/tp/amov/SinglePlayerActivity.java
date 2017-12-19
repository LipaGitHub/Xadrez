package pt.isec.tp.amov;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import pt.isec.tp.amov.Game.Board;
import pt.isec.tp.amov.Game.Player;

import static pt.isec.tp.amov.Constants.CRITICAL_MOVE;

/**
 * Created by Fajardo on 12/12/2017.
 */

public class SinglePlayerActivity extends AppCompatActivity{
    GridView boardGame, eatenPieces;
    Board board;
    Player player1, player2;
    TextView txtPlayer1, txtPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleplayer);

        if(savedInstanceState != null
                && (savedInstanceState.getSerializable("dados") != null)){
            player1 = (Player) savedInstanceState.getSerializable("jog1");
            player2 = (Player) savedInstanceState.getSerializable("jog2");
            board = (Board) savedInstanceState.getSerializable("dados");
            //boardGame.setAdapter(new GridViewAdapterSingle(this, board));
            //txtPlayer2.setText(board.getToPlay().getNome());
            //txtPlayer1.setText("OKAY");
        }else {
            txtPlayer1 = findViewById(R.id.txtPlayer1);
            txtPlayer2 = findViewById(R.id.txtPlayer2);

            player1 = new Player(1);
            player2 = new Player(2, "PC");
            //TODO: podemos ainda randomizar para ver quem comeca o jogo p.ex.
            board = new Board(player1, player2);

            player1.initializePieces();
            player2.initializePieces();

            board.createBoard();
            board.paintBoard();
            board.showPiecesOnBoard(player1, player2);

            boardGame = findViewById(R.id.grdvBoard);
            boardGame.setNumColumns(8);

            txtPlayer2.setText(player2.getNome());

            boardGame.setAdapter(new GridViewAdapterSingle(this, board));

            boardGame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    board.verifyMove(position);
                    board.changePlayer(player1, player2);
                    ((GridViewAdapterSingle) boardGame.getAdapter()).notifyDataSetChanged(); //atualiza gridview
                    if (board.getToPlay().getID() == 1) {
                        txtPlayer1.setBackgroundColor(Color.rgb(99, 00, 00));
                        txtPlayer2.setBackgroundColor(Color.rgb(24, 14, 00));
                    } else {
                        txtPlayer2.setBackgroundColor(Color.rgb(99, 00, 00));
                        txtPlayer1.setBackgroundColor(Color.rgb(24, 14, 00));
                    }
                    //boardGame.setAdapter(new GridViewAdapterSingle(getApplicationContext(), board)); //atualiza gridview
                    //Toast.makeText(SinglePlayerActivity.this, "X: " + s.getX() + "\tY: " + s.getY(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("jog1", player1);
        savedInstanceState.putSerializable("jog2", player2);
        savedInstanceState.putSerializable("dados", board);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        player1 = (Player) savedInstanceState.getSerializable("jog1");
        player2 = (Player) savedInstanceState.getSerializable("jog2");
        board = (Board) savedInstanceState.getSerializable("dados");
    }

    public void onEatenPieces(View v){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SinglePlayerActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_eatenpieces, null);
        int p1 = R.id.txtPlayer1;
        int p2 = R.id.txtPlayer2;
        eatenPieces = mView.findViewById(R.id.grdvEatenPieces);
        TextView txt = (TextView) v;
        if(txt.getId() == p1){
            eatenPieces.setAdapter(new GridViewAdapterEatenPieces(this, player1));
        }
        if(txt.getId() == p2){
            eatenPieces.setAdapter(new GridViewAdapterEatenPieces(this, player2));
        }
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private class GridViewAdapterEatenPieces extends BaseAdapter {
        Context mContext;
        Player id;

        private GridViewAdapterEatenPieces(Context c, Player p) { mContext = c; id = p; }

        @Override
        public int getCount() { return id.getEatenPieces().size(); }

        @Override
        public Object getItem(int i) { return null; }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            ImageView img = new ImageView(mContext);
            img.setImageDrawable(getDrawable(id.getEatenPieces().get(position).getType()));
            img.setAdjustViewBounds(true);
            return img;
        }
    }

    private class GridViewAdapterSingle extends BaseAdapter {
        Context mContext;
        Board mBoard;

        private GridViewAdapterSingle(Context c, Board b) { mContext = c; mBoard = b; }

        @Override
        public int getCount() { return mBoard.getTiles().size(); }

        @Override
        public Object getItem(int i) { return null; }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            ImageView img = new ImageView(mContext);

            if(mBoard.getTiles().get(position).isOccupied() == false)
                img.setImageResource(mBoard.getTiles().get(position).getColor());
            else {
                if(mBoard.getTiles().get(position).getColor() == CRITICAL_MOVE)
                    img.setImageResource(mBoard.getTiles().get(position).getColor());
                else
                    img.setImageDrawable(getDrawable(mBoard.getTiles().get(position).getPiece().getType()));
            }
            img.setAdjustViewBounds(true);
            return img;
        }
    }
}
