package com.samples.realwear.camera;


/*------------------- COPYRIGHT AND TRADEMARK INFORMATION -------------------+
  |
  |    RealWear Development Software, Source Code and Object Code
  |    (c) 2015 - 2017 RealWear, Inc. All rights reserved.
  |
  |    Contact info@realwear.com for further information about the use of
  |    this code.
  |
  +--------------------------------------------------------------------------*/



/*----------------------- SOURCE MODULE INFORMATION -------------------------+
 |
 | Source Name:  Camera Activity - launch Camera Applet to take a snapshot and return
 |
 |
 | Version: v1.0
 | Date: July 2017
 | Author: Chris Parkinson
 |
  +--------------------------------------------------------------------------*/

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Example activity for receiving an image back from the Camera application via byte[] and file
 */
public class MainActivity extends AppCompatActivity {
    private String TAG = "HMT1DevApp-Camera";
    private static final int CAMERA_REQUEST_URI = 1888;
    private static final int CAMERA_REQUEST = 1889;
    private Button mImageButton, mImageLocationBtn;
    private File photoFile;

    /////////////////////////////////////////////////////////////////////////////
    //
    // Constructor
    //
    /////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.camera_main);
    }

    /////////////////////////////////////////////////////////////////////////////
    //
    // onResume
    //
    /////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "On Resume called in Activity");

    }

    /////////////////////////////////////////////////////////////////////////////
    //
    // onPause
    //
    /////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onPause() {
        Log.d(TAG, "On Pause called in Activity");
        super.onPause();
    }

    /////////////////////////////////////////////////////////////////////////////
    //
    // Listen for Button Click
    //
    /////////////////////////////////////////////////////////////////////////////
    public void takePhoto(View view) {

        //Launch Camera Intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    /**
     *  Listen to button click for when a file is requested
     * @param view
     */
    public void takePhotoFile(View view) {

        // Create file on the sdcard
        photoFile = new File(Environment.getExternalStorageDirectory(), "snapshot.jpg");
        if (!photoFile.exists()) {
            try {
                photoFile.getParentFile().mkdirs();
                photoFile.createNewFile();
            } catch (IOException | SecurityException e) {
                Log.e(TAG, "takePhotoFile", e);
                Toast.makeText(this, "Could not create file " + photoFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                return;
            }
        }

        Uri fileUri = Uri.fromFile(photoFile);

        // Send the location for when the image must be saved to.
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_REQUEST_URI);
    }


    /////////////////////////////////////////////////////////////////////////////
    //
    // Listen for result from external Activity
    //
    /////////////////////////////////////////////////////////////////////////////
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "On Activtiy Result");
        if (resultCode == Activity.RESULT_OK && data != null && requestCode == CAMERA_REQUEST) {
            Bitmap photo = data.getExtras().getParcelable("data");
            ImageView imageView = (ImageView)findViewById(R.id.camera_image_view);
            if (imageView!=null)imageView.setImageBitmap(photo);
        }

        if (resultCode == Activity.RESULT_OK && data != null && requestCode == CAMERA_REQUEST_URI) {
            Toast.makeText(this, "Photo saved: " + photoFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
        }
    }

}
