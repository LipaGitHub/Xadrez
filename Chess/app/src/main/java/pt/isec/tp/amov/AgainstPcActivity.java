package pt.isec.tp.amov;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import pt.isec.tp.amov.Game.Board;
import pt.isec.tp.amov.Game.Player;

import static pt.isec.tp.amov.Constants.CRITICAL_MOVE;

/**
 * Created by Fajardo on 20/12/2017.
 */

public class AgainstPcActivity extends AppCompatActivity{
    GridView boardGame, eatenPieces;
    Board board;
    Player player1, player2;
    TextView txtPlayer1, txtPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_container);

        txtPlayer1 = findViewById(R.id.txtPlayer1);
        txtPlayer2 = findViewById(R.id.txtPlayer2);
        boardGame = findViewById(R.id.grdvBoard);
        boardGame.setNumColumns(8);

        if(savedInstanceState != null
                && (savedInstanceState.getSerializable("dados") != null)){
            player1 = (Player) savedInstanceState.getSerializable("jog1");
            player2 = (Player) savedInstanceState.getSerializable("jog2");
            board = (Board) savedInstanceState.getSerializable("dados");
            reCreateGame(board);
        }else {
            createGame();

            boardGame.setAdapter(new AgainstPcActivity.GridViewAdapterSingle(this, board));

            boardGame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    board.verifyMove(position);
                    board.changePlayer(player1, player2);
                    ((AgainstPcActivity.GridViewAdapterSingle) boardGame.getAdapter()).notifyDataSetChanged(); //atualiza gridview
                    if (board.getToPlay().getID() == 1) {
                        txtPlayer1.setBackgroundColor(Color.rgb(99, 00, 00));
                        txtPlayer2.setBackgroundColor(Color.rgb(24, 14, 00));
                    } else {
                        txtPlayer2.setBackgroundColor(Color.rgb(99, 00, 00));
                        txtPlayer1.setBackgroundColor(Color.rgb(24, 14, 00));
                    }
                    while(board.getToPlay().getID() == 2){
                        int randomNum = ThreadLocalRandom.current().nextInt(0, 63 + 1);
                        board.verifyMove(randomNum);
                        board.changePlayer(player1,player2);
                        ((AgainstPcActivity.GridViewAdapterSingle) boardGame.getAdapter()).notifyDataSetChanged(); //atualiza gridview
                        if (board.getToPlay().getID() == 1) {
                            txtPlayer1.setBackgroundColor(Color.rgb(99, 00, 00));
                            txtPlayer2.setBackgroundColor(Color.rgb(24, 14, 00));
                        } else {
                            txtPlayer2.setBackgroundColor(Color.rgb(99, 00, 00));
                            txtPlayer1.setBackgroundColor(Color.rgb(24, 14, 00));
                        }
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
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AgainstPcActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_eatenpieces, null);
        int p1 = R.id.txtPlayer1;
        int p2 = R.id.txtPlayer2;
        eatenPieces = mView.findViewById(R.id.grdvEatenPieces);
        TextView txt = (TextView) v;
        if(txt.getId() == p1){
            eatenPieces.setAdapter(new AgainstPcActivity.GridViewAdapterEatenPieces(this, player1));
        }
        if(txt.getId() == p2){
            eatenPieces.setAdapter(new AgainstPcActivity.GridViewAdapterEatenPieces(this, player2));
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

            if(!mBoard.getTiles().get(position).isOccupied())
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

    public void createGame(){
        player1 = new Player(1, "Dany");
        player2 = new Player(2, "PC");
        //TODO: podemos ainda randomizar para ver quem comeca o jogo p.ex.
        board = new Board(player1, player2);

        player1.initializePieces();
        player2.initializePieces();

        board.createBoard();
        board.paintBoard();
        board.showPiecesOnBoard(player1, player2);

        txtPlayer1.setText(player1.getNome());
        txtPlayer2.setText(player2.getNome());
    }

    public void reCreateGame(Board b){
        boardGame.setAdapter(new AgainstPcActivity.GridViewAdapterSingle(this, b));

        boardGame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                board.verifyMove(position);
                board.changePlayer(player1, player2);
                ((AgainstPcActivity.GridViewAdapterSingle) boardGame.getAdapter()).notifyDataSetChanged(); //atualiza gridview
                if (board.getToPlay().getID() == 1) {
                    txtPlayer1.setBackgroundColor(Color.rgb(99, 00, 00));
                    txtPlayer2.setBackgroundColor(Color.rgb(24, 14, 00));
                } else {
                    txtPlayer2.setBackgroundColor(Color.rgb(99, 00, 00));
                    txtPlayer1.setBackgroundColor(Color.rgb(24, 14, 00));
                }
                while(board.getToPlay().getID() == 2){
                    int randomNum = ThreadLocalRandom.current().nextInt(0, 63 + 1);
                    board.verifyMove(randomNum);
                    board.changePlayer(player1,player2);
                    ((AgainstPcActivity.GridViewAdapterSingle) boardGame.getAdapter()).notifyDataSetChanged(); //atualiza gridview
                    if (board.getToPlay().getID() == 1) {
                        txtPlayer1.setBackgroundColor(Color.rgb(99, 00, 00));
                        txtPlayer2.setBackgroundColor(Color.rgb(24, 14, 00));
                    } else {
                        txtPlayer2.setBackgroundColor(Color.rgb(99, 00, 00));
                        txtPlayer1.setBackgroundColor(Color.rgb(24, 14, 00));
                    }
                }

                //boardGame.setAdapter(new GridViewAdapterSingle(getApplicationContext(), board)); //atualiza gridview
                //Toast.makeText(SinglePlayerActivity.this, "X: " + s.getX() + "\tY: " + s.getY(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.activity_keep_changes, null);
        ImageButton imgbtnYes = mView.findViewById(R.id.imgbtnYes);
        ImageButton imgbtnNo = mView.findViewById(R.id.imgbtnNo);
        imgbtnYes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(AgainstPcActivity.this, "U pressed YES", Toast.LENGTH_SHORT).show();
                /*// Create a file in the Internal <a href="#">Storage</a>
                String fileName = "MyFile";
                String content = "hello world";

                FileOutputStream outputStream = null;
                try {
                    outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                    outputStream.write(content.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                finish();
            }
        });
        imgbtnNo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
}
