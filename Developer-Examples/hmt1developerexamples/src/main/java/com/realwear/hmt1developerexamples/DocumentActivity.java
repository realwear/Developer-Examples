/**
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 * <p>
 * Contact info@realwear.com for further information about the use of this code.
 */

package com.realwear.hmt1developerexamples;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Activity that shows how open a document on a HMT-1 device
 */
public class DocumentActivity extends Activity {

    private final String mSampleFileName = "forest.jpg";
    private final String mSampleFolderName = "Pictures";
    private final String mSampleMimeType = "image/jpeg";

    private File mSampleFile;

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
        setContentView(R.layout.document_main);

        try {
            mSampleFile = Utils.copyFromAssetsToExternal(this, mSampleFileName, mSampleFolderName);
        } catch (IOException ex) {
            Toast.makeText(this, "Failed to copy sample file", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Listener for when a the launch document viewer button is clicked
     *
     * @param view The launch launch document viewer button
     */
    public void onLaunchDocument(View view) {
        if (mSampleFile == null) {
            Toast.makeText(
                    getApplicationContext(),
                    "Failed to find sample file",
                    Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(Uri.fromFile(mSampleFile), mSampleMimeType);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //
        // Optionally can control visual appearance
        //
        intent.putExtra("page", "1"); // Open a specific page
        intent.putExtra("zoom", "3"); // Open at a specific zoom level

        startActivity(intent);
    }
}
