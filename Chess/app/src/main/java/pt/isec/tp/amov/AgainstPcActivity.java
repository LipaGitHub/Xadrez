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

public class AgainstPcActivity extends AppCompatActivity{
    GridView boardGame, eatenPieces;
    Board board;
    Player player1, player2;
    TextView txtPlayer1, txtPlayer2;
    String fileName = "OneVsPC.dat";
    ArrayList<Board> games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_container);

        txtPlayer1 = findViewById(R.id.txtPlayer1);
        txtPlayer2 = findViewById(R.id.txtPlayer2);
        boardGame = findViewById(R.id.grdvBoard);
        boardGame.setNumColumns(8);
        games = new ArrayList<>();
        Profile pro;

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            pro = (Profile) extras.get("PROFILECHOSEN");
            board = (Board) extras.get("changeMode"); //em caso de ter alterado de 1vs1 (1 dispositivo) p/ 1 vs PC
            if(board != null) {
                reCreateGame(board); //recria jogo
            }else if((extras.getInt("newGame")) == 100){ //primeiro jogo ever
                newGame(pro);
            }else{//jogar jogo existente
                Bundle args =  extras.getBundle("EXISTING_BOARD");
                board = (Board) args.getSerializable("BOARD");
                if(board != null){
                    board.getPlayer1().setNome(pro.getName());
                    reCreateGame(board);
                }
            }
        }/*else {
            games = readData(fileName);

            if (games.size() == 0) {
                //Create same game if rotated
                if (savedInstanceState != null
                        && (savedInstanceState.getSerializable("dados") != null)) {
                    board = (Board) savedInstanceState.getSerializable("dados");
                    reCreateGame(board);
                } else {
                    //Create the first and new game
                    newGame();
                }
            } else {
                Intent i = new Intent(this, ExistingGames.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST", games);
                i.putExtra("EXISTING_GAMES", args);
                startActivityForResult(i, 1);
            }
        }*/
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if (resultCode == 1) {
                Bundle args = data.getBundleExtra("EXISTING_BOARD");
                board = (Board) args.getSerializable("BOARD");
                if (board != null) {
                    reCreateGame(board);
                }
            } else if (resultCode == 2) {
                newGame();
            }
        }
    }*/

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
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

    public void newGame(Profile pro){
        createGame(pro);
        boardGame.setAdapter(new AgainstPcActivity.GridViewAdapterSingle(this, board));
        boardGame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                board.verifyMove(position);
                board.changePlayer(player1, player2);
                ((AgainstPcActivity.GridViewAdapterSingle) boardGame.getAdapter()).notifyDataSetChanged(); //atualiza gridview
                if(board.kingdefeated()){
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(AgainstPcActivity.this);
                    mBuilder.setCancelable(false);
                    View mView = getLayoutInflater().inflate(R.layout.activity_winner, null);
                    TextView txtWinnerName = mView.findViewById(R.id.txtWinner);
                    if(board.getWinner() == 1) txtWinnerName.append(" " + board.getPlayer1().getNome());
                    else txtWinnerName.append(" " + board.getPlayer2().getNome());
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
                while (board.getToPlay().getID() == 2) {
                    int randomNum = ThreadLocalRandom.current().nextInt(0, 63 + 1);
                    board.verifyMove(randomNum);
                    board.changePlayer(player1, player2);
                    //((AgainstPcActivity.GridViewAdapterSingle) boardGame.getAdapter()).notifyDataSetChanged(); //atualiza gridview
                    if (board.getToPlay().getID() == 1) {
                        txtPlayer1.setBackgroundColor(Color.rgb(99, 0, 0));
                        txtPlayer2.setBackgroundColor(Color.rgb(24, 14, 0));
                    } else {
                        txtPlayer2.setBackgroundColor(Color.rgb(99, 0, 0));
                        txtPlayer1.setBackgroundColor(Color.rgb(24, 14, 0));
                    }
                }
                //boardGame.setAdapter(new GridViewAdapterSingle(getApplicationContext(), board)); //atualiza gridview
                //Toast.makeText(SinglePlayerActivity.this, "X: " + s.getX() + "\tY: " + s.getY(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createGame(Profile pro){
        player1 = new Player(1, pro.getName());
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
        player1 = b.getPlayer1();
        player2 = b.getPlayer2();
        txtPlayer1.setText(b.getPlayer1().getNome());
        txtPlayer2.setText(b.getPlayer2().getNome());
        boardGame.setAdapter(new AgainstPcActivity.GridViewAdapterSingle(this, b));

        boardGame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                board.verifyMove(position);
                board.changePlayer(player1, player2);
                ((AgainstPcActivity.GridViewAdapterSingle) boardGame.getAdapter()).notifyDataSetChanged(); //atualiza gridview
                if(board.kingdefeated()){
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(AgainstPcActivity.this);
                    mBuilder.setCancelable(false);
                    View mView = getLayoutInflater().inflate(R.layout.activity_winner, null);
                    TextView txtWinnerName = mView.findViewById(R.id.txtWinner);
                    if(board.getWinner() == 1) txtWinnerName.append(" " + board.getPlayer1().getNome());
                    else txtWinnerName.append(" " + board.getPlayer2().getNome());
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
                while(board.getToPlay().getID() == 2){
                    int randomNum = ThreadLocalRandom.current().nextInt(0, 63 + 1);
                    board.verifyMove(randomNum);
                    board.changePlayer(player1,player2);
                    ((AgainstPcActivity.GridViewAdapterSingle) boardGame.getAdapter()).notifyDataSetChanged(); //atualiza gridview
                    if (board.getToPlay().getID() == 1) {
                        txtPlayer1.setBackgroundColor(Color.rgb(99, 0, 0));
                        txtPlayer2.setBackgroundColor(Color.rgb(24, 14, 0));
                    } else {
                        txtPlayer2.setBackgroundColor(Color.rgb(99, 0, 0));
                        txtPlayer1.setBackgroundColor(Color.rgb(24, 14, 0));
                    }
                }

                //boardGame.setAdapter(new GridViewAdapterSingle(getApplicationContext(), board)); //atualiza gridview
                //Toast.makeText(SinglePlayerActivity.this, "X: " + s.getX() + "\tY: " + s.getY(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onProceedToResults(View v){
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.activity_keep_changes, null);
        final EditText edtNameGame = mView.findViewById(R.id.edtNameGame);
        ImageButton imgbtnYes = mView.findViewById(R.id.imgbtnYes);
        ImageButton imgbtnNo = mView.findViewById(R.id.imgbtnNo);
        edtNameGame.setText(board.getName());
        imgbtnYes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {

                    games = readData(fileName);
                    board.setName(edtNameGame.getText().toString());

                    int override = 0;
                    for(int i=0; i < games.size();i++)
                        if(games.get(i).getName().equals(board.getName())){
                            games.set(i, board);
                            override = 1;
                        }//override existing game

                    if(override != 1) { //if it's a new game then saves it
                        games.add(board);
                    }

                    overrideExistingGamesFile(fileName);

                    if(games.size() == 1){
                        FileOutputStream fos = openFileOutput(fileName, MODE_APPEND);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        for(int i=0; i < games.size(); i++){
                            oos.writeObject(games.get(i));
                            //oos.flush();
                        }
                        oos.close();
                        fos.close();
                    }else{
                        FileOutputStream fos = openFileOutput(fileName, MODE_APPEND);
                        ObjectOutputStream aoos = new ObjectOutputStream(fos);
                        for(int i=0; i < games.size(); i++){
                            aoos.writeObject(games.get(i));
                            //aoos.flush();
                            //oos.writeObject(games.get(i));
                            //oos.flush();
                        }
                        aoos.close();
                        //oos.flush();
                        //oos.close();
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    public void overrideExistingGamesFile(String f){
        deleteFile(f);
        File file = new File(getApplicationContext().getFilesDir(), f);
        try {
            if(file.createNewFile()){
                Log.i("1", "Ficheiro criado!");// if file already exists will do nothing
            }else Log.i("2", "sqn");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Board> readData(String fileName){
        games = new ArrayList<>();
        File file = new File(getApplicationContext().getFilesDir(), fileName);

        try {
            if(file.createNewFile()){
                Log.i("1", "Ficheiro criado!");// if file already exists will do nothing
            }else Log.i("2", "sqn");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Object aux;
            while((aux = ois.readObject()) != null) {
                if (aux instanceof Board) {
                    games.add((Board) aux);
                }
            }
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return games;
    }
}

/*
class AppendingObjectOutputStream extends ObjectOutputStream {

    public AppendingObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        // do not write a header, but reset:
        // this line added after another question
        // showed a problem with the original
        reset();
    }

}*/
