package pt.isec.tp.amov;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RemoteMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_menu);
    }

    public void onServer(View v){
        Intent intent=new Intent(this, RemoteActivity.class);
        intent.putExtra("mode", Constants.SERVER);
        startActivity(intent);
    }

    public void onClient(View v){
        Intent intent=new Intent(this, RemoteActivity.class);
        intent.putExtra("mode", Constants.CLIENT);
        startActivity(intent);
    }
}
