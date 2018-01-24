/**
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 * <p>
 * Contact info@realwear.com for further information about the use of this code.
 */

package com.realwear.hmt1developerexamples;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

/**
 * Activity that shows how to release the microphones on a HMT-1 device
 */
public class MicrophoneReleaseActivity extends Activity {
    //
    // Intent actions to request and release the microphone
    //
    private static final String ACTION_RELEASE_MIC =
            "com.realwear.wearhf.intent.action.RELEASE_MIC";
    private static final String ACTION_MIC_RELEASED =
            "com.realwear.wearhf.intent.action.MIC_RELEASED";

    //
    // Identifiers for the settings which allow modification of the on screen message shown when
    // the microphone has been released
    //
    private static final String EXTRA_MUTE_TEXT = "com.realwear.wearhf.intent.extra.MUTE_TEXT";
    private static final String EXTRA_HIDE_TEXT = "com.realwear.wearhf.intent.extra.HIDE_TEXT";
    private static final String EXTRA_SOURCE_PACKAGE =
            "com.realwear.wearhf.intent.extra.SOURCE_PACKAGE";

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
        setContentView(R.layout.microphone_release_main);
    }

    /**
     * Called when activity is resumed - See Android docs
     */
    @Override
    protected void onResume() {
        super.onResume();

        //
        // Register to receive microphone events
        //
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_MIC_RELEASED);
        filter.addAction(ACTION_RELEASE_MIC);
        registerReceiver(micBroadcastReceiver, filter);
    }

    /**
     * Called when activity is paused - See Android docs
     */
    @Override
    protected void onPause() {
        super.onPause();

        //
        // Make sure we release the microphone on pause so we don't go into the background
        // with it still blocking ASR
        //
        Intent intent = new Intent(ACTION_MIC_RELEASED);
        intent.putExtra(EXTRA_SOURCE_PACKAGE, getPackageName());
        sendBroadcast(intent);

        if (micBroadcastReceiver != null) {
            unregisterReceiver(micBroadcastReceiver);
        }
    }

    /**
     * Listener for when a the release microphone button is clicked
     *
     * @param view The release microphone button
     */
    public void onReleaseMicrophone(View view) {
        Intent intent = new Intent(ACTION_RELEASE_MIC);
        intent.putExtra(EXTRA_SOURCE_PACKAGE, getPackageName());

        // By default system will put up big red display to say that
        // 'speech is off, press Action button to resume'
        // If we don't want any text on screen, set the HIDE_TEXT extra to true
        intent.putExtra(EXTRA_HIDE_TEXT, false);

        //
        // If we want to show text but change the message, use the MUTE_TEXT extra
        //
        intent.putExtra(EXTRA_MUTE_TEXT, "Mic is Off. Press Action Button to Resume");

        sendBroadcast(intent);
    }

    /**
     * Broadcast Receiver to receive microphone release actions
     */
    private BroadcastReceiver micBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String sourcePackage = intent.getStringExtra(EXTRA_SOURCE_PACKAGE);
            if (sourcePackage != null) {
                if (sourcePackage.equals(getApplicationContext().getPackageName())) {
                    return;
                }
            }

            if (intent.getAction().equals(ACTION_MIC_RELEASED)) {
                //
                // The system has now released the microphones
                //
            } else if (intent.getAction().equals(ACTION_RELEASE_MIC)) {
                //
                // The system wants to access the microphone so release and inform the system
                //
                Intent newIntent = new Intent(ACTION_MIC_RELEASED);
                intent.putExtra(EXTRA_SOURCE_PACKAGE, getApplicationContext().getPackageName());
                sendBroadcast(newIntent);
            }
        }
    };
}
