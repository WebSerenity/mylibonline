/*
 * Barebones implementation of displaying camera preview.
 * 
 * Created by lisah0 on 2012-02-24
 */
package ws.base.mylibonline;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;

import android.util.Log;

import android.view.View;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import android.content.Context;

import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;

/** A basic Camera preview class */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private PreviewCallback previewCallback;
    private AutoFocusCallback autoFocusCallback;

    public CameraPreview(Context context, Camera camera,PreviewCallback previewCallback, AutoFocusCallback autoFocusCallback) {
        super(context);
        this.camera = camera;
        this.previewCallback = previewCallback;
        this.autoFocusCallback = autoFocusCallback;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        // deprecated setting, but required on Android versions prior to 3.0
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            camera.setPreviewDisplay(holder);
        } catch (IOException e) {
            Log.d("DBG", "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // Camera preview released in activity
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        /*
         * If your preview can change or rotate, take care of those events here.
         * Make sure to stop the preview before resizing or reformatting it.
         */
        try {
        	if (surfaceHolder.getSurface() == null){
                // preview surface does not exist
                return;
        	}
        	camera.stopPreview();
            // Hard code camera surface rotation 90 degs to match Activity view in portrait
        	camera.setDisplayOrientation(90);
        	camera.setPreviewDisplay(surfaceHolder);
        	camera.setPreviewCallback(previewCallback);
        	camera.startPreview();
        	camera.autoFocus(autoFocusCallback);
        } catch (Exception e){
            Log.d("DBG", "Error starting camera preview: " + e.getMessage());
        }
    }
}
