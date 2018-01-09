package pt.isec.tp.amov;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Locale;

import pt.isec.tp.amov.Game.Board;

public class MainActivity extends AppCompatActivity {

    // these two variables will be used by SharedPreferences
    private static final String FILE_NAME = "file_lang"; // preference file name
    private static final String KEY_LANG = "key_lang"; // preference key
    private String fileName = "actualProfile.dat";
    String fileNameProfiles = "Profiles.dat";

    Profile p;

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

        if((p = (Profile) getIntent().getSerializableExtra("THISPROFILE")) != null){
            writeProfile(p);
            txtActualProfile.setText(getString(R.string.profileAtual) + " " + p.getName());
        }else{
            p = readProfile(fileNameProfiles);
            if(p != null)
                txtActualProfile.setText(getString(R.string.profileAtual) + " " + p.getName());
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
        if(p != null) {
            Intent i = new Intent(getApplicationContext(), ExistingGames.class);
            i.putExtra("PROFILECHOSEN", p);
            startActivity(i);
        }else{
            Intent i = new Intent(getApplicationContext(), ExistingProfile.class);
            startActivity(i);
        }
    }

    public void onMultiPlayerMenu(View v){
        if(p != null) {
            Intent i = new Intent(getApplicationContext(), MultiPlayerMenuActivity.class);
            i.putExtra("PROFILECHOSEN", p);
            startActivity(i);
        }else {
            Intent i = new Intent(getApplicationContext(), ExistingProfile.class);
            startActivity(i);
        }
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

    public Profile readProfile(String fileName){
        File file = new File(getFilesDir(), fileName);
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
            aux = ois.readObject();
            if(aux != null){
                p = (Profile) aux;
            }
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return p;
    }

    public void writeProfile(Profile p){
        File file = new File(getFilesDir(), fileNameProfiles);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Profile aux = null;
        try {
            while((aux = (Profile) ois.readObject()) != null){
                if(aux.getName().equals(p.getName())){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //reescrever o perfil atual
        file = new File(getFilesDir(), fileName);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oos.writeObject(aux);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}