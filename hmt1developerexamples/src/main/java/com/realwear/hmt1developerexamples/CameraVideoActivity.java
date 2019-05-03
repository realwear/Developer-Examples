package com.realwear.hmt1developerexamples;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import android.widget.Button;

/**
 * Activity that shows how to use the camera to record a video on an HMT-1 device
 */
public class CameraVideoActivity extends Activity {
    private final static String TAG = "CameraVideoActivity";

    // Request code identifying camera events
    private final static int REQUEST_ID = 123;

    /**
     * Called when the activity is created
     *
     * @param savedInstanceState See Android docs
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera_video);

    }

    /**
     * Listener to attempt recording video when the 'Launch Camera (specified URI)' button is
     * clicked.  On Android 8 and above the file provider is used to save and to get files
     *
     * @param view The 'Launch Camera (specified URI)' {@link Button}
     */
    public void onLaunchCameraFileProvider(View view) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                final Uri videoUri = FileProvider.getUriForFile(
                        getApplicationContext(),
                        getApplicationContext().getPackageName() + ".fileprovider",
                        generateFile());

                final Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                videoIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                videoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
                startActivityForResult(videoIntent, REQUEST_ID);
            } catch (IOException e) {
                Log.e(TAG, "Error creating video file.", e);
            }
        } else {
            Log.e(TAG, "Android version must be >= 8");
        }
    }

    /**
     * Listener to attempt recording video when the 'Launch Camera (no URI)' button is clicked
     *
     * @param view The 'Launch Camera (no URI)' {@link Button}
     */
    public void onLaunchCameraBasic(View view) {
        final Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(videoIntent, REQUEST_ID);
    }

    /**
     * Generate a new {@link File} for recording video
     *
     * @return A {@link File} object where a video can be recorded
     */
    public File generateFile() throws IOException {
        final String fileName = UUID.randomUUID().toString();

        final File mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(
                fileName,
                ".mp4",
                mediaStorageDir
        );
    }
}
