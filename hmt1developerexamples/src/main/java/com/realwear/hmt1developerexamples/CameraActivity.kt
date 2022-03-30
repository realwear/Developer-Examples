/*
 * RealWear Development Software, Source Code and Object Code.
 * (c) RealWear, Inc. All rights reserved.
 *
 * Contact info@realwear.com for further information about the use of this code.
 */
package com.realwear.hmt1developerexamples

import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import com.realwear.hmt1developerexamples.R
import android.content.Intent
import android.provider.MediaStore
import com.realwear.hmt1developerexamples.CameraActivity
import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import java.util.*

/**
 * Activity that shows how to use the camera to take a pictures and record videos on a HMT-1 device.
 */
class CameraActivity : Activity() {
    private val imageView: ImageView by lazy { findViewById(R.id.camera_image_view) }

    /**
     * Called when the activity is created
     *
     * @param savedInstanceState See Android docs
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_main)
    }

    /**
     * Listener for when the bitmap photo button is clicked.
     *
     * @param view The button.
     */
    fun onLaunchBitmapPhoto(view: View?) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, BITMAP_PHOTO_REQUEST_CODE)
    }

    /**
     * Listener for when the file provider photo button is clicked.
     *
     * @param view The button.
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    fun onLaunchFileProviderPhoto(view: View?) {
        val fileName = "devexamples-" + UUID.randomUUID() + ".jpg"
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, DEFAULT_IMAGE_LOCATION)
        val contentUri = baseContext.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
        )
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
        startActivityForResult(captureIntent, FILE_PROVIDER_PHOTO_REQUEST_CODE)
    }

    /**
     * Listener for when the bitmap photo button is clicked.
     *
     * @param view The button.
     */
    fun onLaunchBasicVideo(view: View?) {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        startActivityForResult(intent, BASIC_VIDEO_REQUEST_CODE)
    }

    /**
     * Listener for when the file provider video button is clicked.
     *
     * @param view The button.
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    fun onLaunchFileProviderVideo(view: View?) {
        val fileName = "devexamples-" + UUID.randomUUID() + ".mp4"
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, DEFAULT_VIDEO_LOCATION)
        val contentUri = baseContext.contentResolver.insert(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues
        )
        val captureIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
        startActivityForResult(captureIntent, FILE_PROVIDER_VIDEO_REQUEST_CODE)
    }

    /**
     * Listener for result from external activities. Receives image data from camera.
     *
     * @param requestCode See Android docs.
     * @param resultCode  See Android docs.
     * @param data        See Android docs.
     */
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                BITMAP_PHOTO_REQUEST_CODE -> {
                    val photo = data.extras!!.getParcelable<Bitmap>(EXTRA_RESULT)
                    imageView.setImageBitmap(photo)
                }
                FILE_PROVIDER_PHOTO_REQUEST_CODE -> {
                    val photoUri = data.data
                    imageView.setImageURI(photoUri)
                }
                BASIC_VIDEO_REQUEST_CODE, FILE_PROVIDER_VIDEO_REQUEST_CODE -> {
                    val videoUri = data.data
                    val basicVideoPlayIntent = Intent()
                    basicVideoPlayIntent.action = Intent.ACTION_VIEW
                    basicVideoPlayIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    basicVideoPlayIntent.setDataAndType(videoUri, "video/*")
                    startActivityForResult(basicVideoPlayIntent, FILE_PLAYBACK_REQUEST_CODE)
                }
                else -> Log.e(TAG, "Unknown request code: $requestCode")
            }
        }
    }

    companion object {
        private const val TAG = "CameraActivity"

        // Request code for playing back videos.
        private const val FILE_PLAYBACK_REQUEST_CODE = 5

        //
        // Request codes for identifying camera events.
        //
        private const val BITMAP_PHOTO_REQUEST_CODE = 1
        private const val FILE_PROVIDER_PHOTO_REQUEST_CODE = 2
        private const val BASIC_VIDEO_REQUEST_CODE = 3
        private const val FILE_PROVIDER_VIDEO_REQUEST_CODE = 4

        //
        // Locations for store content provided images and videos.
        //
        private val DEFAULT_IMAGE_LOCATION = Environment.DIRECTORY_DCIM + "/Camera"
        private val DEFAULT_VIDEO_LOCATION = Environment.DIRECTORY_MOVIES + "/Camera"

        // Identifier for the image returned by the camera
        private const val EXTRA_RESULT = "data"
    }
}