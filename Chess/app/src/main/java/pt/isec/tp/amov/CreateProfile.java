package pt.isec.tp.amov;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class CreateProfile extends AppCompatActivity{

    Profile p;
    ImageView imgViewPicture;
    EditText edtName;
    Bitmap imageBitmap;
    Button btnCreateProfile;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        imgViewPicture = findViewById(R.id.imgViewPicture);
        edtName = findViewById(R.id.edtName);
        btnCreateProfile = findViewById(R.id.btnCreateProfile);
    }

    public void onCreateProfile(View v){
        String username = edtName.getText().toString();
        if(username.matches("")){
            edtName.setHint("Enter username!");
            edtName.setHintTextColor(getResources().getColor(R.color.colorAttack));
        }else if(imageBitmap == null) {
            Toast.makeText(this, "Take a nice picture of you to continue!", Toast.LENGTH_LONG).show();
        }else{
            //Converter o bitmap para string
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();

            p = new Profile(edtName.getText().toString(), Base64.encodeToString(b, Base64.DEFAULT));
            Intent i = new Intent(this, ExistingProfile.class);
            //startActivity(i);
            i.putExtra("PROFILE", p);
            setResult(50, i);
            finish();
        }
    }

    public void dispatchTakePictureIntent(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imgViewPicture.setImageBitmap(imageBitmap);
        }
    }
}