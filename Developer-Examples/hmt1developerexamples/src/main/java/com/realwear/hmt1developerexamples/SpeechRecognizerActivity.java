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
 * Activity that shows how to register voice commands with the ASR on a HMT-1 device
 */
public class SpeechRecognizerActivity extends Activity {

    //
    // Intent actions for registering voice commands and for being notified when they are triggered
    //
    private static final String ACTION_OVERRIDE_COMMANDS =
            "com.realwear.wearhf.intent.action.OVERRIDE_COMMANDS";
    private static final String ACTION_SPEECH_EVENT =
            "com.realwear.wearhf.intent.action.SPEECH_EVENT";
    private static final String ACTION_RESTORE_COMMANDS =
            "com.realwear.wearhf.intent.action.RESTORE_COMMANDS";

    // Identifier for the package that is setting the voice commands
    private static final String EXTRA_SOURCE_PACKAGE =
            "com.realwear.wearhf.intent.extra.SOURCE_PACKAGE";

    // Identifier used when registering voice commands
    private static final String EXTRA_COMMANDS =
            "com.realwear.wearhf.intent.extra.COMMANDS";

    // Identifier for when a voice command is triggered
    private static final String EXTRA_RESULT = "command";

    private TextView mQuantityView;

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
        setContentView(R.layout.speech_main);

        mQuantityView = (TextView) findViewById(R.id.quantityView);
    }

    /**
     * Called when activity is resumed - See Android docs
     */
    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(asrBroadcastReceiver, new IntentFilter(ACTION_SPEECH_EVENT));
        sendCommands();
    }

    /**
     * Called when activity is paused - See Android docs
     */
    @Override
    protected void onPause() {
        super.onPause();

        if (asrBroadcastReceiver != null) {
            unregisterReceiver(asrBroadcastReceiver);
        }

        Intent intent = new Intent(ACTION_RESTORE_COMMANDS);
        intent.putExtra(EXTRA_SOURCE_PACKAGE, getPackageName());
        sendBroadcast(intent);
    }

    /**
     * Broadcast receiver for being notified when voice commands are triggered
     */
    private BroadcastReceiver asrBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(ACTION_SPEECH_EVENT)) {
                String asrCommand = intent.getStringExtra(EXTRA_RESULT);
                mQuantityView.setText(asrCommand);

                sendCommands();
            }
        }
    };

    /**
     * Register voice commands
     */
    private void sendCommands() {
        String commands[] = new String[20];
        for (int i = 0; i < commands.length; i++) {
            commands[i] = "Quantity " + (i + 1);
        }

        Intent intent = new Intent(ACTION_OVERRIDE_COMMANDS);
        intent.putExtra(EXTRA_SOURCE_PACKAGE, getPackageName());
        intent.putExtra(EXTRA_COMMANDS, commands);
        sendBroadcast(intent);
    }
}

