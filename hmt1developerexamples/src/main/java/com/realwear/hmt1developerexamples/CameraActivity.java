/*
 * RealWear Development Software, Source Code and Object Code.
 * (c) RealWear, Inc. All rights reserved.
 *
 * Contact info@realwear.com for further information about the use of this code.
 */

package com.realwear.hmt1developerexamples;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.UUID;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;

/**
 * Activity that shows how to use the camera to take a pictures and record videos on a HMT-1 device.
 */
public class CameraActivity extends Activity {
    private static final String TAG = "CameraActivity";

    // Request code for playing back videos.
    private static final int FILE_PLAYBACK_REQUEST_CODE = 5;

    //
    // Request codes for identifying camera events.
    //
    private static final int BITMAP_PHOTO_REQUEST_CODE = 1;
    private static final int FILE_PROVIDER_PHOTO_REQUEST_CODE = 2;
    private static final int BASIC_VIDEO_REQUEST_CODE = 3;
    private static final int FILE_PROVIDER_VIDEO_REQUEST_CODE = 4;

    //
    // Locations for store content provided images and videos.
    //
    private static final String DEFAULT_IMAGE_LOCATION = Environment.DIRECTORY_DCIM + "/Camera";
    private static final String DEFAULT_VIDEO_LOCATION = Environment.DIRECTORY_MOVIES + "/Camera";

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
        setContentView(R.layout.camera_main);

        mImageView = findViewById(R.id.camera_image_view);
    }

    /**
     * Listener for when the bitmap photo button is clicked.
     *
     * @param view The button.
     */
    public void onLaunchBitmapPhoto(View view) {
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, BITMAP_PHOTO_REQUEST_CODE);
    }

    /**
     * Listener for when the file provider photo button is clicked.
     *
     * @param view The button.
     */
    public void onLaunchFileProviderPhoto(View view) {
        final String fileName = "devexamples-" + UUID.randomUUID() + ".jpg";

        final ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, DEFAULT_IMAGE_LOCATION);

        final Uri contentUri = getBaseContext().getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

        startActivityForResult(captureIntent, FILE_PROVIDER_PHOTO_REQUEST_CODE);
    }

    /**
     * Listener for when the bitmap photo button is clicked.
     *
     * @param view The button.
     */
    public void onLaunchBasicVideo(View view) {
        final Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, BASIC_VIDEO_REQUEST_CODE);
    }

    /**
     * Listener for when the file provider video button is clicked.
     *
     * @param view The button.
     */
    public void onLaunchFileProviderVideo(View view) {
        final String fileName = "devexamples-" + UUID.randomUUID() + ".mp4";

        final ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, DEFAULT_VIDEO_LOCATION);

        final Uri contentUri = getBaseContext().getContentResolver().insert(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);

        final Intent captureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

        startActivityForResult(captureIntent, FILE_PROVIDER_VIDEO_REQUEST_CODE);
    }

    /**
     * Listener for result from external activities. Receives image data from camera.
     *
     * @param requestCode See Android docs.
     * @param resultCode  See Android docs.
     * @param data        See Android docs.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case BITMAP_PHOTO_REQUEST_CODE:
                    final Bitmap photo = data.getExtras().getParcelable(EXTRA_RESULT);
                    mImageView.setImageBitmap(photo);
                    break;

                case FILE_PROVIDER_PHOTO_REQUEST_CODE:
                    final Uri photoUri = data.getData();
                    mImageView.setImageURI(photoUri);
                    break;

                case BASIC_VIDEO_REQUEST_CODE:
                case FILE_PROVIDER_VIDEO_REQUEST_CODE:
                    final Uri videoUri = data.getData();

                    final Intent basicVideoPlayIntent = new Intent();
                    basicVideoPlayIntent.setAction(Intent.ACTION_VIEW);
                    basicVideoPlayIntent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
                    basicVideoPlayIntent.setDataAndType(videoUri, "video/*");
                    startActivityForResult(basicVideoPlayIntent, FILE_PLAYBACK_REQUEST_CODE);
                    break;
                default:
                    Log.e(TAG, "Unknown request code: " + requestCode);
                    break;
            }
        }
    }
}
