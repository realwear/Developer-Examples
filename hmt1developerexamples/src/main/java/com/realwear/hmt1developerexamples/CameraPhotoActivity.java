/**
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 * <p>
 * Contact info@realwear.com for further information about the use of this code.
 */

package com.realwear.hmt1developerexamples;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Activity that shows how to use the camera to take a picture on a HMT-1 device
 */
public class CameraPhotoActivity extends Activity {

    // Request code identifying camera events
    private static final int CAMERA_REQUEST_CODE = 1889;

    // Identifier for the image returned by the camera
    private static final String EXTRA_RESULT = "data";

    private ImageView mImageView;

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
        setContentView(R.layout.activity_camera_photo);

        mImageView = (ImageView) findViewById(R.id.camera_image_view);
    }

    /**
     * Listener for when a the launch camera button is clicked
     *
     * @param view The launch camera button
     */
    public void onLaunchCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    /**
     * Listener for result from external activities. Receives image data from camera.
     *
     * @param requestCode See Android docs
     * @param resultCode  See Android docs
     * @param data        See Android docs
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            Bitmap photo = data.getExtras().getParcelable(EXTRA_RESULT);
            mImageView.setImageBitmap(photo);
        }
    }
}
