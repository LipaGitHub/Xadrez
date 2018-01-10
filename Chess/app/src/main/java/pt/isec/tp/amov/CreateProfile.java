package pt.isec.tp.amov;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.Policy;

public class CreateProfile extends AppCompatActivity{

    Profile p;
    EditText edtName;
    Bitmap imageBitmap;
    Button btnCreateProfile, btnTakePhoto;
    //android.hardware.Camera camera;
    //SurfaceHolder surfaceHolder;
    ImageView imgViewPicture;
    //ShowCamera showcamera;
    //FrameLayout myLayout;
    //android.hardware.Camera.PictureCallback jpegCallback;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        //imgViewPicture = findViewById(R.id.imgViewPicture);
        edtName = findViewById(R.id.edtName);
        btnCreateProfile = findViewById(R.id.btnCreateProfile);
        btnTakePhoto = findViewById(R.id.btnTakePhoto);
        //camera = switchOnCamera();

        imgViewPicture = findViewById(R.id.imgViewPicture);
        //surfaceHolder = imgViewPicture.getHolder();

        //surfaceHolder.addCallback(this);

        //surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        /*jpegCallback = new android.hardware.Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, android.hardware.Camera camera) {

                refreshCamera();
            }
        };*/
        //myLayout = findViewById(R.id.cameraPreview);
        //myLayout.addView(showcamera);

        /*if(!hasCamera()){
            btnTakePhoto.setEnabled(false);
        }*/
    }

    /*public void dispatchTakePictureIntent(View v){
        camera.takePicture(null, null, jpegCallback);
    }*/

    /*public void refreshCamera() {
        if(surfaceHolder.getSurface() == null) {
            return;
        }

        try {
            camera.stopPreview();
        } catch (Exception e) {
            // TODO: handle exception
        }

        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }*/

    /*public void dis(View v) {
        camera.takePicture(null, null, jpegCallback);
    }*/

    /*public void refreshCamera() {
        if(surfaceHolder.getSurface() == null) {
            return;
        }

        try {
            camera.stopPreview();
        } catch (Exception e) {
            // TODO: handle exception
        }

        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }*/

    /*public boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }*/

    /*public android.hardware.Camera switchOnCamera(){
        android.hardware.Camera cam_obj = null;

        cam_obj = camera.open();
        android.hardware.Camera.Parameters parameters = cam_obj.getParameters();

        if(this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE){
            parameters.set("orientation", "portrait");
            cam_obj.setDisplayOrientation(90);
            parameters.setRotation(90);
        }else{
            parameters.set("orientation", "landscape");
            cam_obj.setDisplayOrientation(0);
            parameters.setRotation(0);
        }

        return cam_obj;
    }*/

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

    /*
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera = android.hardware.Camera.open();
        } catch (RuntimeException e) {
            return;
        }

        android.hardware.Camera.Parameters param;
        param = camera.getParameters();

        param.setPreviewSize(352, 288);
        camera.setParameters(param);

        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {

        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        refreshCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }*/

    public void dispatchTakePictureIntent(View v) {
        /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }*/
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, REQUEST_CAPTURE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imgViewPicture.setBackground(null);
            imgViewPicture.setImageBitmap(imageBitmap);
        }
        /*if(requestCode == REQUEST_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            //imgViewPicture.setImageBitmap(photo);
        }*/
    }
}
