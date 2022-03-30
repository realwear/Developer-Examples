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
 * Activity that shows how open a document on a HMT-1 device
 */
class DocumentActivity : Activity() {
    /**
     * Called when the activity is created
     *
     * @param savedInstanceState See Android docs
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.document_main)
        try {
            sampleFile = Utils.copyFromAssetsToExternal(this, SAMPLE_FILE_NAME, SAMPLE_FOLDER_NAME)
        } catch (ex: IOException) {
            Toast.makeText(this, "Failed to copy sample file", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Listener for when a the launch document viewer button is clicked
     *
     * @param view The launch launch document viewer button
     */
    fun onLaunchDocument(view: View?) {
        val contentUri = FileProvider.getUriForFile(
            applicationContext,
            applicationContext.packageName + ".fileprovider",
            sampleFile!!
        )
        if (sampleFile == null) {
            Toast.makeText(applicationContext, "Failed to find sample file", Toast.LENGTH_LONG)
                .show()
            return
        }
        val viewIntent = Intent(Intent.ACTION_VIEW)
        viewIntent.addCategory(Intent.CATEGORY_DEFAULT)
        viewIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        viewIntent.setDataAndType(contentUri, SAMPLE_MIME_TYPE)
        viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        //
        // Optionally can control visual appearance
        //
        viewIntent.putExtra("page", "1") // Open a specific page
        viewIntent.putExtra("zoom", "2") // Open at a specific zoom level
        startActivity(viewIntent)
    }

    companion object {
        private const val SAMPLE_FILE_NAME = "sample.pdf"
        private const val SAMPLE_FOLDER_NAME = "Documents"
        private const val SAMPLE_MIME_TYPE = "application/pdf"
        private var sampleFile: File? = null
    }
}