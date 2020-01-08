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
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Activity that shows how to register voice commands with the ASR on a HMT-1 device.
 */
public class SpeechRecognizerActivity extends Activity {
    // The action that WearHF will use for broadcasting when a voice command is spoken.
    private static final String ACTION_SPEECH_EVENT =
            "com.realwear.wearhf.intent.action.SPEECH_EVENT";

    // Identifier for the voice command that was is spoken.
    private static final String EXTRA_RESULT = "command";

    private TextView mQuantityView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.speech_main);

        mQuantityView = findViewById(R.id.quantityView);

        //
        // Set the voice commands for this screen.
        // We are adding the voice commands to the TextView in the layout, but they can be added
        // to any view.
        // The broadcast receiver will get the result when the voice command is spoken.
        //
        mQuantityView.setContentDescription("hf_add_commands:Quantity 1|Quantity 2|Quantity 3");
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(asrBroadcastReceiver, new IntentFilter(ACTION_SPEECH_EVENT));
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (asrBroadcastReceiver != null) {
            unregisterReceiver(asrBroadcastReceiver);
        }
    }

    /**
     * Broadcast receiver for being notified when voice commands are triggered.
     */
    private BroadcastReceiver asrBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(ACTION_SPEECH_EVENT)) {
                String asrCommand = intent.getStringExtra(EXTRA_RESULT);
                mQuantityView.setText(asrCommand);
            }
        }
    };
}