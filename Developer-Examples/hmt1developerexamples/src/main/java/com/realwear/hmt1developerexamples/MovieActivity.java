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
 * Activity that shows how open a video on a HMT-1 device
 */
public class MovieActivity extends Activity {

    private final String sampleFileName = "kick ass.mp4";
    private final String sampleFolderName = "Movies";
    private final String sampleMimeType = "video/mp4";

    private File mSampleFile;

    /**
     * Constructor
     *
     * @param savedInstanceState See Android docs
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.movie_main);

        try {
            mSampleFile = Utils.copyFromAssetsToExternal(this, sampleFileName, sampleFolderName);
        } catch (IOException ex) {
            Toast.makeText(this, "Failed to Copy Sample File", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Listener for when a the launch video button is clicked
     *
     * @param view The launch launch video button
     */
    public void onLaunchVideo(View view) {
        if (mSampleFile == null) {
            Toast.makeText(
                    getApplicationContext(),
                    "Failed to find sample file",
                    Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(Uri.fromFile(mSampleFile), sampleMimeType);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }
}
