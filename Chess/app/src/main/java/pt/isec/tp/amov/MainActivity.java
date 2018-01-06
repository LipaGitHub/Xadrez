package pt.isec.tp.amov;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // these two variables will be used by SharedPreferences
    private static final String FILE_NAME = "file_lang"; // preference file name
    private static final String KEY_LANG = "key_lang"; // preference key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Locale locale = new Locale(getLangCode());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        setContentView(R.layout.activity_main);
        TextView txtActualProfile = findViewById(R.id.txtActualProfile);

        Profile p;
        if((p = (Profile) getIntent().getSerializableExtra("THISPROFILE")) != null){
            txtActualProfile.setText("Profile: " + p.getName());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_language:
                onLanguageOption(findViewById(R.id.action_language));
                break;
            case R.id.action_profile:
                onExistingProfile(findViewById(R.id.action_profile));
                break;
        }
        return true;
    }

    public void onAgainstPC(View v){
        Intent i = new Intent(getApplicationContext(), ExistingGames.class);
        startActivity(i);
    }

    public void onMultiPlayerMenu(View v){
        Intent i = new Intent(getApplicationContext(), MultiPlayerMenuActivity.class);
        startActivity(i);
    }

    public void onLanguageOption(View v){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_languageoption, null);
        ImageButton imgbtnPT = mView.findViewById(R.id.imgbtnPortuguese);
        ImageButton imgbtnUK = mView.findViewById(R.id.imgbtnEnglish);
        ImageButton imgbtnFR = mView.findViewById(R.id.imgbtnFrench);
        imgbtnPT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                saveLanguage("pt");
            }
        });
        imgbtnUK.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                saveLanguage("en");
            }
        });
        imgbtnFR.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                saveLanguage("fr");
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
        //Intent i = new Intent(getApplicationContext(), LanguageOptionActivity.class);
        //startActivity(i);
    }

    public void onExistingProfile(View v){
        Intent i = new Intent(getApplicationContext(), ExistingProfile.class);
        startActivity(i);
    }

    private void saveLanguage(String lang) {
        // we can use this method to save language
        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_LANG, lang);
        editor.apply();
        // we have saved
        // recreate activity after saving to load the new language, this is the same
        // as refreshing activity to load new language
        recreate();
    }

    private String getLangCode() {
        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String langCode = preferences.getString(KEY_LANG, "en");
        // save english 'en' as the default language
        return langCode;
    }

}
