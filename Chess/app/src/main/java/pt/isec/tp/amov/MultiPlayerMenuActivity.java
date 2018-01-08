package pt.isec.tp.amov;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by Fajardo on 12/12/2017.
 */

public class MultiPlayerMenuActivity extends AppCompatActivity {

    CheckBox ckClock;
    LinearLayout layoutTimer;
    //EditText edtTimer;
    Profile pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menumultiplayer);

        pro = (Profile) getIntent().getExtras().get("PROFILECHOSEN");

        ckClock = findViewById(R.id.ckClock);
        layoutTimer = findViewById(R.id.linlayTimer);

        ckClock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!ckClock.isChecked()) {
                    /*FragmentManager fm = getFragmentManager();
                    ClockFragment c = (ClockFragment) fm.findFragmentByTag("Clock");
                    FragmentTransaction ft = fm.beginTransaction();
                    if(c != null){
                        ft.remove(c);
                        ft.commit();
                    }*/
                    layoutTimer.setVisibility(View.GONE);
                }else layoutTimer.setVisibility(View.VISIBLE);
            }
        });

        ckClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ClockFragment c = new ClockFragment();
                ft.add(R.id.frgClock, c, "Clock");
                ft.commit();*/
            }
        });
    }

    public void onOneVersusOne(View v){
        EditText edtTimer = findViewById(R.id.edtTimer);
        int time = Integer.parseInt(edtTimer.getText().toString());
        Intent i = new Intent(getApplicationContext(), SinglePlayerActivity.class);
        i.putExtra("PROFILECHOSEN", pro);
        i.putExtra("timer", time);
        startActivity(i);
    }
}
