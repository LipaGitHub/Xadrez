package pt.isec.tp.amov;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * Created by Fajardo on 12/12/2017.
 */

public class MultiPlayerMenuActivity extends AppCompatActivity {

    CheckBox ckClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menumultiplayer);

        ckClock = findViewById(R.id.ckClock);

        ckClock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!ckClock.isChecked()) {
                    FragmentManager fm = getFragmentManager();
                    ClockFragment c = (ClockFragment) fm.findFragmentByTag("Clock");
                    FragmentTransaction ft = fm.beginTransaction();
                    if(c != null){
                        ft.remove(c);
                        ft.commit();
                    }

                }
            }
        });

        ckClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ClockFragment c = new ClockFragment();
                ft.add(R.id.frgClock, c, "Clock");
                ft.commit();
            }
        });
    }
}
