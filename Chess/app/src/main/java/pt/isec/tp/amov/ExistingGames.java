package pt.isec.tp.amov;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import pt.isec.tp.amov.Game.Board;

public class ExistingGames extends AppCompatActivity {

    GridView grdvExistingGames;
    ArrayList<Board> games;
    String fileName = "OneVsPC.dat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_games);
        //this.setFinishOnTouchOutside(false);

        grdvExistingGames = findViewById(R.id.grdvExistingGames);

        games = readData(fileName);
        if(games.size() == 0){
            Intent i = new Intent(this, AgainstPcActivity.class);
            i.putExtra("newGame", 100);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }else {

            //Intent i = getIntent();
            //Bundle args = i.getBundleExtra("EXISTING_GAMES");
            //games = (ArrayList<Board>) args.getSerializable("ARRAYLIST");

            grdvExistingGames.setAdapter(new ExistingGames.GridViewAdapterExistingGames(this, games));
            grdvExistingGames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Board b = games.get(position);
                    Intent i = new Intent(getApplicationContext(), AgainstPcActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("BOARD", b);
                    i.putExtra("EXISTING_BOARD", args);
                    startActivity(i);
                    //setResult(1, i);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_newgame, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_newGame:
                Intent i = new Intent(this, AgainstPcActivity.class);
                i.putExtra("newGame", 100);
                startActivity(i);
                finish();
                //setResult(2, i);
                break;
        }
        return true;
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

    private class GridViewAdapterExistingGames extends BaseAdapter {
        Context mContext;
        ArrayList<Board> mGames;

        private GridViewAdapterExistingGames(Context c, ArrayList<Board> g) { mContext = c; mGames = g; }

        @Override
        public int getCount() { return mGames.size(); }

        @Override
        public Object getItem(int i) { return null; }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View gridView = inflater.inflate(R.layout.existing_games, null);

            TextView txtGameID = gridView.findViewById(R.id.txtID);
            TextView txtGameLabel = gridView.findViewById(R.id.txtGame);

            int id = position+1;
            txtGameID.setText(""+id);
            txtGameLabel.setText(mGames.get(position).getName());

            return gridView;
        }
    }
}
