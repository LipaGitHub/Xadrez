package pt.isec.tp.amov;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import pt.isec.tp.amov.Game.Board;
import pt.isec.tp.amov.Game.Player;

import static pt.isec.tp.amov.Constants.CRITICAL_MOVE;

/**
 * Created by Fajardo on 20/12/2017.
 */

public class SinglePlayerActivity extends AppCompatActivity{
    GridView boardGame, eatenPieces;
    Board board;
    Player player1, player2;
    TextView txtPlayer1, txtPlayer2, txtTimerPlayer1, txtTimerPlayer2;
    //String fileName = "OneVsPC.dat";
    //ArrayList<Board> games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_container);

        txtPlayer1 = findViewById(R.id.txtPlayer1);
        txtPlayer2 = findViewById(R.id.txtPlayer2);
        txtTimerPlayer1 = findViewById(R.id.txtTimerPlayer1);
        txtTimerPlayer2 = findViewById(R.id.txtTimerPlayer2);
        boardGame = findViewById(R.id.grdvBoard);
        boardGame.setNumColumns(8);
        Profile pro1;

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            pro1 = (Profile) extras.get("PROFILECHOSEN");
            board = (Board) extras.getSerializable("changeMode");
            if(board != null){
                board.setTimer(0);
                reCreateGame(board);
            }else if(extras.getInt("timer") > 0){
                int time = extras.getInt("timer");
                txtTimerPlayer1.setText(""+ time);
                txtTimerPlayer2.setText(""+ time);
                newGame(time, pro1);
            }
        }

        //if(games.size() == 0){
        //Create same game if rotated
        /*if (savedInstanceState != null
                && (savedInstanceState.getSerializable("dados") != null)) {
            board = (Board) savedInstanceState.getSerializable("dados");
            reCreateGame(board);
        } else {
            //Create the first and new game
            newGame(time);
        }*/
        //}
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("dados", board);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        board = (Board) savedInstanceState.getSerializable("dados");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_changemode, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_changeMode:
                Intent i = new Intent(this, SinglePlayerActivity.class);
                i.putExtra("changeMode", board);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    /*public void run() {
        //for (int i = 10; i < 13; i++) {
            //System.out.println(Thread.currentThread().getName() + "  ");
            try {
                board.getPlayer1().getTimer().sleep(1000);
                Log.i("pausa", "" + board.getTimer());
            } catch (Exception e) {
                System.out.println(e);
            }
        //}
    }*/

    public void onEatenPieces(View v) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SinglePlayerActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_eatenpieces, null);
        int p1 = R.id.txtPlayer1;
        int p2 = R.id.txtPlayer2;
        eatenPieces = mView.findViewById(R.id.grdvEatenPieces);
        TextView txt = (TextView) v;
        if (txt.getId() == p1) {
            eatenPieces.setAdapter(new SinglePlayerActivity.GridViewAdapterEatenPieces(this, player1));
        }
        if (txt.getId() == p2) {
            eatenPieces.setAdapter(new SinglePlayerActivity.GridViewAdapterEatenPieces(this, player2));
        }
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private class GridViewAdapterEatenPieces extends BaseAdapter {
        Context mContext;
        Player id;

        private GridViewAdapterEatenPieces(Context c, Player p) {
            mContext = c;
            id = p;
        }

        @Override
        public int getCount() {
            return id.getEatenPieces().size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

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

        private GridViewAdapterSingle(Context c, Board b) {
            mContext = c;
            mBoard = b;
        }

        @Override
        public int getCount() {
            return mBoard.getTiles().size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            ImageView img = new ImageView(mContext);

            if (!mBoard.getTiles().get(position).isOccupied())
                img.setImageResource(mBoard.getTiles().get(position).getColor());
            else {
                if (mBoard.getTiles().get(position).getColor() == CRITICAL_MOVE)
                    img.setImageResource(mBoard.getTiles().get(position).getColor());
                else
                    img.setImageDrawable(getDrawable(mBoard.getTiles().get(position).getPiece().getType()));
            }
            img.setAdjustViewBounds(true);
            return img;
        }
    }

    public void newGame(int time, Profile pro) {
        createGame(time, pro);
        boardGame.setAdapter(new SinglePlayerActivity.GridViewAdapterSingle(this, board));
        boardGame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                board.verifyMove(position);
                board.changePlayer(player1, player2);
                ((SinglePlayerActivity.GridViewAdapterSingle) boardGame.getAdapter()).notifyDataSetChanged(); //atualiza gridview
                if(board.kingdefeated()){
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(SinglePlayerActivity.this);
                    mBuilder.setCancelable(false);
                    View mView = getLayoutInflater().inflate(R.layout.activity_winner, null);
                    TextView txtWinnerName = mView.findViewById(R.id.txtWinner);
                    if(board.getWinner() == 1) txtWinnerName.append(" " + board.getPlayer1().getProfile().getName());
                    else txtWinnerName.append(" " + board.getPlayer2().getProfile().getName());
                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();
                }
                if (board.getToPlay().getID() == 1) {
                    txtPlayer1.setBackgroundColor(Color.rgb(99, 0, 0));
                    txtPlayer2.setBackgroundColor(Color.rgb(24, 14, 0));
                } else {
                    txtPlayer2.setBackgroundColor(Color.rgb(99, 0, 0));
                    txtPlayer1.setBackgroundColor(Color.rgb(24, 14, 0));
                }
            }
        });
    }

    public void createGame(int time, Profile pro) {
        player1 = new Player(1, pro);
        player2 = new Player(2, new Profile("PC", "por_inserir"));
        //TODO: podemos ainda randomizar para ver quem comeca o jogo p.ex.
        board = new Board(player1, player2);
        board.setTimer(time);

        player1.initializePieces();
        player2.initializePieces();

        board.createBoard();
        board.paintBoard();
        board.showPiecesOnBoard(player1, player2);

        txtPlayer1.setText(player1.getProfile().getName());
        txtPlayer2.setText(player2.getProfile().getName());
        //imgViewProfile1.setImageBitmap(player1.getProfile().getImg()); //falta converter
        //imgViewProfile2.setImageBitmap(player2.getProfile().getImg());
    }

    public void reCreateGame(Board b) {
        player1 = b.getPlayer1();
        player2 = b.getPlayer2();
        txtPlayer1.setText(b.getPlayer1().getProfile().getName());
        txtPlayer2.setText("Hirto");
        boardGame.setAdapter(new SinglePlayerActivity.GridViewAdapterSingle(this, b));

        boardGame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //board.getPlayer1().getTimer().start();
                board.verifyMove(position);
                board.changePlayer(player1, player2);
                ((SinglePlayerActivity.GridViewAdapterSingle) boardGame.getAdapter()).notifyDataSetChanged(); //atualiza gridview
                if(board.kingdefeated()){
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(SinglePlayerActivity.this);
                    mBuilder.setCancelable(false);
                    View mView = getLayoutInflater().inflate(R.layout.activity_winner, null);
                    TextView txtWinnerName = mView.findViewById(R.id.txtWinner);
                    if(board.getWinner() == 1) txtWinnerName.append(" " + board.getPlayer1().getProfile().getName());
                    else txtWinnerName.append(" " + board.getPlayer2().getProfile().getName());
                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();
                }
                if (board.getToPlay().getID() == 1) {
                    txtPlayer1.setBackgroundColor(Color.rgb(99, 0, 0));
                    txtPlayer2.setBackgroundColor(Color.rgb(24, 14, 0));
                } else {
                    txtPlayer2.setBackgroundColor(Color.rgb(99, 0, 0));
                    txtPlayer1.setBackgroundColor(Color.rgb(24, 14, 0));
                }
                //boardGame.setAdapter(new GridViewAdapterSingle(getApplicationContext(), board)); //atualiza gridview
                //Toast.makeText(SinglePlayerActivity.this, "X: " + s.getX() + "\tY: " + s.getY(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //Player actualPlayer = board.getToPlay();
        Intent i = new Intent(this, MainActivity.class);
        //i.putExtra("changeMode", board);
        startActivity(i);
        //setResult(3, i);
        //startActivityForResult(i, 2);
        finish();
    }
}