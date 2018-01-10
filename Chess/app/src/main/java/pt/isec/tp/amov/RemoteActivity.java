package pt.isec.tp.amov;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import pt.isec.tp.amov.Game.Board;
import pt.isec.tp.amov.Game.Player;

import static pt.isec.tp.amov.Constants.CRITICAL_MOVE;
import static pt.isec.tp.amov.Constants.PORT;
import static pt.isec.tp.amov.Constants.SERVER;

public class RemoteActivity extends AppCompatActivity {

    ServerSocket serverSocket=null;
    Socket socketGame = null;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    BufferedReader input;
    PrintWriter output;
    int mode = SERVER;

    TextView txtPlayer1, txtPlayer2;
    ImageView imgViewProfile1, imgViewProfile2;
    Player player1, player2;
    Board board;
    GridView boardGame;
    ProgressDialog pd = null;
    Handler procMsg = null;

    Profile p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_container);
        txtPlayer1 = findViewById(R.id.txtPlayer1);
        txtPlayer2 = findViewById(R.id.txtPlayer2);
        imgViewProfile1 = findViewById(R.id.imgViewProfile1);
        imgViewProfile2 = findViewById(R.id.imgViewProfile2);

        p = (Profile) getIntent().getSerializableExtra("PROFILECHOSEN");

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            Toast.makeText(this, "No network connection", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Intent intent = getIntent();
        if (intent != null)
            mode = intent.getIntExtra("mode", SERVER);

        procMsg = new Handler();
        //Fragment fragmentGameContainer = findR.id.fragmentGameContainer;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mode == SERVER){
            server();
            initgame();
        }else{
            clientDlg();
        }
    }

    void initgame() {
        createGame(p);
        beginGame();
    }

    void beginGame() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //oos = new ObjectOutputStream(socketGame.getOutputStream());
                    //oos.writeObject(board);
                    //oos.close();
                    //oos.flush();
                    //oos.close();
                    //output.println(moves[ME]);
                    //output.flush();
                } catch (Exception e) {
                    Log.d("RPS", "Error sending a move");
                }

                /*procMsg.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //initgame();
                    }
                }, 5000);*/
            }
        });
        t.start();
    }

    void clientDlg() {
        final EditText edtIP = new EditText(this);
        edtIP.setText("10.0.2.2"); // emulator's default ip
        AlertDialog ad = new AlertDialog.Builder(this).setTitle("RPS Client")
                .setMessage("Server IP").setView(edtIP)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        client(edtIP.getText().toString(), PORT); // to test with emulators: PORTaux);
                        Toast.makeText(getApplicationContext(), "" + edtIP.getText().toString(), Toast.LENGTH_LONG).show();
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(getApplicationContext(), "Cancela", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }).create();
        ad.show();
    }

    void client(final String strIP, final int Port) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("RPS", "Connecting to the server  " + strIP);
                    socketGame = new Socket(InetAddress.getByName(strIP), Port);
                } catch (Exception e) {
                    Log.d("RPS", "Erro ao entrar no");
                    socketGame = null;
                }
                if (socketGame == null) {
                    procMsg.post(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    });
                    return;
                }
                commThread.start();
            }
        });
        t.start();
    }

    protected void onPause() {
        super.onPause();
        try {
            commThread.interrupt();
            if (socketGame != null)
                socketGame.close();
            if (output != null)
                output.close();
            if (input != null)
                input.close();
        } catch (Exception e) {
        }
        input = null;
        output = null;
        socketGame = null;
    }

    void server(){
        String ip = getLocalIpAddress();
        pd = new ProgressDialog(this);
        pd.setMessage("Waiting for a client..." + "\n(IP: " + ip
                + ")");
        pd.setTitle("RPS Server");
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
                if (serverSocket!=null) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                    }
                    serverSocket=null;
                }
            }
        });
        pd.show();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(PORT);
                    socketGame = serverSocket.accept();
                    serverSocket.close();
                    serverSocket=null;
                    commThread.start();
                } catch (Exception e) {
                    e.printStackTrace();
                    socketGame = null;
                }
                procMsg.post(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        if (socketGame == null)
                            finish();
                    }
                });
            }
        });
        t.start();
    }

    Thread commThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                //input = new BufferedReader(new InputStreamReader(socketGame.getInputStream()));
                //output = new PrintWriter(socketGame.getOutputStream());
                ois = new ObjectInputStream(socketGame.getInputStream());
                oos = new ObjectOutputStream(socketGame.getOutputStream());
                //newGame(p);
                Board otherPlayer;
                while (!Thread.currentThread().isInterrupted()) {
                    //otherPlayer = (Board) ois.readObject();
                    //txtPlayer2.setText(otherPlayer.getPlayer2().getProfile().getName());
                    //board.setPlayer2(otherPlayer.getPlayer2());
                    //board.getPlayer2().setProfile(p);
                    //oos.writeObject(board);
                    /*String read = input.readLine();
                    final int move = Integer.parseInt(read);
                    Log.d("RPS", "Received: " + move);*/
                    procMsg.post(new Runnable() {
                        @Override
                        public void run() {

                            //moveOtherPlayer(move);
                        }
                    });
                }
            } catch (Exception e) {
                procMsg.post(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        Toast.makeText(getApplicationContext(),
                                "acabou", Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        }
    });


    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void newGame(Profile pro) {
        createGame(pro);
        boardGame.setAdapter(new RemoteActivity.GridViewAdapterSingle(this, board));
        boardGame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*board.verifyMove(position);
                board.changePlayer(player1, player2);
                ((RemoteActivity.GridViewAdapterSingle) boardGame.getAdapter()).notifyDataSetChanged(); //atualiza gridview
                if(board.kingdefeated()){
                    android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(RemoteActivity.this);
                    mBuilder.setCancelable(false);
                    View mView = getLayoutInflater().inflate(R.layout.activity_winner, null);
                    TextView txtWinnerName = mView.findViewById(R.id.txtWinner);
                    if(board.getWinner() == 1) txtWinnerName.append(" " + board.getPlayer1().getProfile().getName());
                    else txtWinnerName.append(" " + board.getPlayer2().getProfile().getName());
                    mBuilder.setView(mView);
                    android.support.v7.app.AlertDialog dialog = mBuilder.create();
                    dialog.show();
                }
                if (board.getToPlay().getID() == 1) {
                    txtPlayer1.setBackgroundColor(Color.rgb(99, 0, 0));
                    txtPlayer2.setBackgroundColor(Color.rgb(24, 14, 0));
                } else {
                    txtPlayer2.setBackgroundColor(Color.rgb(99, 0, 0));
                    txtPlayer1.setBackgroundColor(Color.rgb(24, 14, 0));
                }*/
            }
        });
    }

    public void createGame(Profile  pro) {
        player1 = new Player(1, pro);
        player2 = null;

        board = new Board(player1, player2);

        player1.initializePieces();
        //player2.initializePieces();

        board.createBoard();
        board.paintBoard();
        for (int i = 0; i < player1.getPieces().size(); i++) {
            board.getTiles().get(48+i).setOccupied(true);
            board.getTiles().get(48+i).setPiece(player1.getPieces().get(i));
        }

        txtPlayer1.setText(player1.getProfile().getName());
        //txtPlayer2.setText(player2.getProfile().getName());
        Bitmap bitmap;
        byte [] encodeByte= Base64.decode(player1.getProfile().getImg(), Base64.DEFAULT);
        bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        imgViewProfile1.setImageBitmap(bitmap);

        //encodeByte= Base64.decode(player2.getProfile().getImg(), Base64.DEFAULT);
        //bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        //imgViewProfile2.setImageBitmap(bitmap);
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
}
