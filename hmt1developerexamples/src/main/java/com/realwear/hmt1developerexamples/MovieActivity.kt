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
import android.widget.Toast
import androidx.core.content.FileProvider
import android.content.Intent
import android.view.View
import com.realwear.hmt1developerexamples.databinding.MovieMainBinding
import java.io.File
import java.io.IOException

/**
 * Activity that shows how open a video on a HMT-1 device
 */
class MovieActivity : Activity() {
    private val binding: MovieMainBinding by lazy { MovieMainBinding.inflate(layoutInflater) }
    private var sampleFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        try {
            sampleFile = Utils.copyFromAssetsToExternal(
                this,
                SAMPLE_FILE_NAME,
                SAMPLE_FOLDER_NAME
            )
        } catch (ex: IOException) {
            Toast
                .makeText(this, "Failed to copy sample file", Toast.LENGTH_LONG)
                .show()
        }
    }

    /**
     * Listener for when a the launch video button [view] is clicked
     */
    @Suppress("UNUSED_PARAMETER")
    fun onLaunchVideo(view: View?) {
        if (sampleFile == null) {
            Toast
                .makeText(applicationContext, "Failed to find sample file", Toast.LENGTH_LONG)
                .show()
            return
        }

        val contentUri = FileProvider.getUriForFile(
            applicationContext,
            applicationContext.packageName + ".fileprovider",
            sampleFile!!
        )

        val viewIntent = Intent(Intent.ACTION_VIEW).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            setDataAndType(contentUri, SAMPLE_MIME_TYPE)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        startActivity(viewIntent)
    }

    companion object {
        private const val SAMPLE_FILE_NAME = "kick_ass.mp4"
        private const val SAMPLE_FOLDER_NAME = "Movies"
        private const val SAMPLE_MIME_TYPE = "video/mp4"
    }
}