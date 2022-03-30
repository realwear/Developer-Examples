/**
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 *
 *
 * Contact info@realwear.com for further information about the use of this code.
 */
package com.realwear.hmt1developerexamples

import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import com.realwear.hmt1developerexamples.R
import android.widget.Toast
import androidx.core.content.FileProvider
import android.content.Intent
import android.view.View
import java.io.File
import java.io.IOException

/**
 * Activity that shows how open a video on a HMT-1 device
 */
class MovieActivity : Activity() {
    private val sampleFileName = "kick ass.mp4"
    private val sampleFolderName = "Movies"
    private val sampleMimeType = "video/mp4"
    private var mSampleFile: File? = null

    /**
     * Called when the activity is created
     *
     * @param savedInstanceState See Android docs
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.movie_main)
        try {
            mSampleFile = Utils.copyFromAssetsToExternal(this, sampleFileName, sampleFolderName)
        } catch (ex: IOException) {
            Toast.makeText(this, "Failed to Copy Sample File", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Listener for when a the launch video button is clicked
     *
     * @param view The launch launch video button
     */
    fun onLaunchVideo(view: View?) {
        if (mSampleFile == null) {
            Toast.makeText(applicationContext, "Failed to find sample file", Toast.LENGTH_LONG)
                .show()
            return
        }
        val contentUri = FileProvider.getUriForFile(
            applicationContext,
            applicationContext.packageName + ".fileprovider",
            mSampleFile!!
        )
        val viewIntent = Intent(Intent.ACTION_VIEW)
        viewIntent.addCategory(Intent.CATEGORY_DEFAULT)
        viewIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        viewIntent.setDataAndType(contentUri, sampleMimeType)
        viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(viewIntent)
    }
}