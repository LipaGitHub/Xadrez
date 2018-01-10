package pt.isec.tp.amov;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by Fajardo on 10/01/2018.
 */

public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback {

    private Camera ca;
    private SurfaceHolder holdMe;

    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;

    Camera.PictureCallback rawCallback;
    Camera.ShutterCallback shutterCallback;
    Camera.PictureCallback jpegCallback;

    public ShowCamera(Context context, Camera mycam) {
        super(context);
        this.camera = mycam;
        holdMe.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera = Camera.open();
        } catch (RuntimeException e) {
            return;
        }

        Camera.Parameters param;
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
        //refreshCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }
}
