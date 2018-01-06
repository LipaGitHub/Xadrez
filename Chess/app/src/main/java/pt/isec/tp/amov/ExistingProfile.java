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
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ExistingProfile extends AppCompatActivity {

    String fileName = "Profiles.dat";
    ArrayList<Profile> existingProfiles;


    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, Profile> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_profile);

        expandableListView = findViewById(R.id.expandableProfiles);
        existingProfiles = new ArrayList<>();
        HashMap<String, Profile> expandableProfile = new HashMap<>();

        //ler do ficheiro
        existingProfiles = readData(fileName);
        if(existingProfiles.size() > 0) {
            //se existir pôr na HashMap
            for (int i = 0; i < existingProfiles.size(); i++) {
                expandableProfile.put(existingProfiles.get(i).getName(), existingProfiles.get(i));
            }

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

            expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

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
            });
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
                    writeData(fileName, data);
                    finish();
                    startActivity(getIntent()); //faz com que "atualize" ou seja faz reload da activity
                }
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

    public void writeData(String fileName, Intent data){
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
        public View getChildView(int listPosition, final int expandedListPosition,
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

            TextView expandedListTextView = (TextView) convertView
                    .findViewById(R.id.expandedListItem);
            expandedListTextView.setText("Nome: " + expandedListText.getName());

            TextView expandedListTextView1 = (TextView) convertView
                    .findViewById(R.id.expandedListItem1);
            expandedListTextView1.setText("Victories: " + expandedListText.getVictories());

            Button btnThisProfile = (Button) convertView.findViewById(R.id.btnThisProfile);
            btnThisProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("THISPROFILE", expandedListText);
                    startActivity(i);
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
            listTitleTextView.setTypeface(null, Typeface.BOLD);
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
}
