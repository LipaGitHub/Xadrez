package pt.isec.tp.amov;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import pt.isec.tp.amov.Game.Board;
import pt.isec.tp.amov.Game.Player;

/**
 * Created by Fajardo on 12/12/2017.
 */

public class SinglePlayerActivity extends AppCompatActivity{
    GridView boardGame;
    Board board;
    Player player1, player2;
    TextView txtPlayer1, txtPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleplayer);


        txtPlayer1 = findViewById(R.id.txtPlayer1);
        txtPlayer2 = findViewById(R.id.txtPlayer2);

        player1 = new Player(1);
        player2 = new Player(2, "PC");
        //TODO: podemos ainda randomizar para ver quem comeca o jogo p.ex.
        board = new Board(player1);

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
                if(board.getToPlay().getID() == 1){
                    txtPlayer1.setBackgroundColor(Color.rgb(99,00, 00));
                    txtPlayer2.setBackgroundColor(Color.rgb(24,14, 00));
                }else{
                    txtPlayer2.setBackgroundColor(Color.rgb(99,00, 00));
                    txtPlayer1.setBackgroundColor(Color.rgb(24,14, 00));
                }
                //boardGame.setAdapter(new GridViewAdapterSingle(getApplicationContext(), board)); //atualiza gridview
                //Toast.makeText(SinglePlayerActivity.this, "X: " + s.getX() + "\tY: " + s.getY(), Toast.LENGTH_SHORT).show();

            }
        });

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
                //img.setImageResource(mBoard.getTiles().get(position).getPiece().getType());
                //img.setBackgroundColor(mBoard.getTiles().get(position).getColor());
                //img.setBackgroundColor(mBoard.getTiles().get(position).getColor());
                img.setImageDrawable(getDrawable(mBoard.getTiles().get(position).getPiece().getType()));
            }
            img.setAdjustViewBounds(true);
            return img;
        }
    }
}
