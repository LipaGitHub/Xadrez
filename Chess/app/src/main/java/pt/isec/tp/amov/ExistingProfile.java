package pt.isec.tp.amov;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


public class ExistingProfile extends AppCompatActivity {

    String fileName = "Profiles.dat";
    ArrayList<Profile> existingProfiles;
    TabHost tbGeneral;
    ListView lvRanking;


    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, Profile> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_profile);

        tbGeneral = findViewById(R.id.thProfiles);
        tbGeneral.setup();

        //Tab 1
        TabHost.TabSpec spec = tbGeneral.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator(getString(R.string.profiles));
        tbGeneral.addTab(spec);

        //Tab 2
        spec = tbGeneral.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator(getString(R.string.ranking));
        tbGeneral.addTab(spec);

        lvRanking = findViewById(R.id.listRanking);
        expandableListView = findViewById(R.id.expandableProfiles);
        existingProfiles = new ArrayList<>();
        HashMap<String, Profile> expandableProfile = new HashMap<>();

        Toast.makeText(this, R.string.toast_profiel, Toast.LENGTH_LONG).show();

        //ler do ficheiro
        existingProfiles = readData(fileName);

        if(existingProfiles.size() > 0) {
            //se existir pôr na HashMap
            for (int i = 0; i < existingProfiles.size(); i++) {
                expandableProfile.put(existingProfiles.get(i).getName(), existingProfiles.get(i));
            }

            /*Arrays.sort(existingProfiles, new Comparator<Profile>() {
                @Override
                public int compare(Profile o1, Profile o2) {
                    return o1.getVictories().compareTo(o2.getVictories());
                }
            });*/

            //isto é para a lista do ranking
            CustomAdapter adapter = new CustomAdapter(existingProfiles,getApplicationContext());
            TextView tv = new TextView(this);
            tv.setText("ID:\t\t\tName:\t\t\tVictories:");
            lvRanking.addHeaderView(tv);
            lvRanking.setAdapter(adapter);

            expandableListDetail = expandableProfile;
            expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
            expandableListAdapter = new CustomExpandableList(this, expandableListTitle, expandableListDetail);
            expandableListView.setAdapter(expandableListAdapter);
            expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                @Override
                public void onGroupExpand(int groupPosition) {
                    Toast.makeText(getApplicationContext(),
                            expandableListTitle.get(groupPosition) + " List Expanded.",
                            Toast.LENGTH_SHORT).show();
                }
            });

            /*expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                    Toast.makeText(getApplicationContext(),
                            expandableListTitle.get(groupPosition) + " List Collapsed.",
                            Toast.LENGTH_SHORT).show();

                }
            });

            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    Toast.makeText(
                            getApplicationContext(),
                            expandableListTitle.get(groupPosition)
                                    + " -> "
                                    + expandableListDetail.get(
                                    expandableListTitle.get(groupPosition)), Toast.LENGTH_SHORT).show();
                    return false;
                }
            });*/
        }else {
            //caso contrario, cria novo perfil
            Intent i = new Intent(this, CreateProfile.class);
            startActivityForResult(i, 50);
        }
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
                Intent i = new Intent(this, CreateProfile.class);
                startActivityForResult(i, 50);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 50){
            if(resultCode == 50){
                Object aux;
                Bundle args = data.getExtras();
                if((aux = args.get("PROFILE")) != null){
                    existingProfiles.add((Profile) aux);
                    writeData(fileName);
                    finish();
                    startActivity(getIntent()); //faz com que "atualize" ou seja faz reload da activity
                }
            }
        }
    }

    public void sortProfilesPerVictories(ArrayList<Profile> array){
        ArrayList<Profile> sorted = new ArrayList<>();
        sorted.add(array.get(0));

        for(int i=1; i < array.size(); i++){
            if(sorted.get(0).getVictories() > array.get(i).getVictories()){
                sorted.add(array.get(i));
            }else{
                sorted.set(1, sorted.get(0));

            }
        }
    }

    public ArrayList<Profile> readData(String fileName){
        existingProfiles = new ArrayList<>();
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
            Object p;
            while((p = ois.readObject()) != null) {
                if (p instanceof Profile) {
                    existingProfiles.add((Profile) p);
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
        return existingProfiles;
    }

    public void writeData(String fileName){
        File file = new File(getApplicationContext().getFilesDir(), fileName);

        try {
            if(file.createNewFile()){
                Log.i("1", "Ficheiro criado!");// if file already exists will do nothing
            }else Log.i("2", "sqn");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i=0; i < existingProfiles.size(); i++){
            try {
                if (oos != null) {
                    oos.writeObject(existingProfiles.get(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public class CustomExpandableList extends BaseExpandableListAdapter {
        private Context context;
        private List<String> expandableListTitle;
        private HashMap<String, Profile> expandableListDetail;

        public CustomExpandableList(Context context, List<String> expandableListTitle,
                                    HashMap<String, Profile> expandableListDetail) {
            this.context = context;
            this.expandableListTitle = expandableListTitle;
            this.expandableListDetail = expandableListDetail;
        }

        @Override
        public Object getChild(int listPosition, int expandedListPosition) {
            return this.expandableListDetail.get(this.expandableListTitle.get(listPosition));
        }


        @Override
        public long getChildId(int listPosition, int expandedListPosition) {
            return expandedListPosition;
        }

        @Override
        public View getChildView(final int listPosition, final int expandedListPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            final Profile expandedListText = (Profile) getChild(listPosition, expandedListPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_item, null);
            }
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imgViewPictureList);
            //converter a string para bitmap
            Bitmap bitmap;
            byte [] encodeByte= Base64.decode(expandedListText.getImg(), Base64.DEFAULT);
            bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            Bitmap imageRounded = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
            Canvas canvas = new Canvas(imageRounded);
            Paint mpaint = new Paint();
            mpaint.setAntiAlias(true);
            mpaint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            canvas.drawRoundRect((new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight())), 100, 100, mpaint);// Round Image Corner 100 100 100 100
            imageView.setImageBitmap(imageRounded);

            TextView expandedListTextView = convertView.findViewById(R.id.expandedListItem);
            expandedListTextView.setText(getString(R.string.nome_li) + expandedListText.getName());

            TextView expandedListTextView1 = convertView.findViewById(R.id.expandedListItem1);
            expandedListTextView1.setText(getString(R.string.victories_li) + expandedListText.getVictories());

            ImageButton btnThisProfile = convertView.findViewById(R.id.btnThisProfile);
            btnThisProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("THISPROFILE", expandedListText);
                    startActivity(i);
                    finish();
                }
            });

            ImageButton btnDeleteProfile = convertView.findViewById(R.id.btnDeleteThisProfile);
            btnDeleteProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    existingProfiles.remove(listPosition);
                    overrideExistingGamesFile(fileName);
                    writeData(fileName);
                    finish();
                    if(existingProfiles.size() != 0)
                        startActivity(getIntent());
                    else{
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }
                }
            });

            return convertView;
        }

        @Override
        public int getChildrenCount(int listPosition) {
            //return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).hashCode();
            return 1;
        }

        @Override
        public Object getGroup(int listPosition) {
            return this.expandableListTitle.get(listPosition);
        }

        @Override
        public int getGroupCount() {
            return this.expandableListTitle.size();
        }

        @Override
        public long getGroupId(int listPosition) {
            return listPosition;
        }

        @Override
        public View getGroupView(int listPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String listTitle = (String) getGroup(listPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_group, null);
            }
            TextView listTitleTextView = (TextView) convertView
                    .findViewById(R.id.listTitle);
            //listTitleTextView.setTypeface(null, Typeface.BOLD);
            listTitleTextView.setText(listTitle);
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int listPosition, int expandedListPosition) {
            return true;
        }
    }

    public class CustomAdapter extends ArrayAdapter<Profile>{
        public ArrayList<Profile> dataSet;
        Context mContext;

        private CustomAdapter(ArrayList<Profile> data, Context context) {
            super(context, R.layout.adapter_list_ranking, data);
            this.dataSet = data;
            this.mContext=context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View res;
            Profile pro = dataSet.get(position);
            TextView txtID, txtName, txtVic;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.adapter_list_ranking, parent, false);
                res = convertView;
            }else{
                res = convertView;
            }
            txtID = convertView.findViewById(R.id.lrID);
            txtName = convertView.findViewById(R.id.lrName);
            txtVic = convertView.findViewById(R.id.lrVic);
            txtID.setText("" + (position+1));
            txtName.setText(pro.getName());
            txtVic.setText("" + pro.getVictories());
            return res;
        }

    }
}
