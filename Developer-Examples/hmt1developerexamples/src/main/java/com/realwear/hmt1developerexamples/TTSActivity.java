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
import android.widget.Toast;

/**
 * Activity that shows how use text to speech on a HMT-1 device
 */
public class TTSActivity extends Activity {

    //
    // Intent actions for starting TTS and for being notified it finishes
    //
    private String ACTION_TTS = "com.realwear.wearhf.intent.action.TTS";
    private String ACTION_TTS_FINISHED = "com.realwear.wearhf.intent.action.tts_finished";

    // Identifiers used when starting TTS commands
    private String EXTRA_TEXT = "text_to_speak";
    private String EXTRA_ID = "tts_id";
    private String EXTRA_PAUSE = "pause_speech_recognizer";

    // Request code identifying TTS events
    private static final int TTS_REQUEST_CODE = 34;

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
        setContentView(R.layout.tts_main);
    }

    /**
     * Called when activity is resumed - See Android docs
     */
    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(ttsReceiver, new IntentFilter(ACTION_TTS_FINISHED));

        String text = getString(R.string.tts_speech);

        Intent intent = new Intent(ACTION_TTS);
        intent.putExtra(EXTRA_TEXT, text);
        intent.putExtra(EXTRA_ID, TTS_REQUEST_CODE);
        intent.putExtra(EXTRA_PAUSE, false); // Don't pause speech recogniser while TTS is playing
        sendBroadcast(intent);
    }

    /**
     * Called when activity is paused - See Android docs
     */
    @Override
    protected void onPause() {
        super.onPause();

        if (ttsReceiver != null) {
            unregisterReceiver(ttsReceiver);
        }
    }

    /**
     * Broadcast receiver for being notified when TTS is complete
     */
    private BroadcastReceiver ttsReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_TTS_FINISHED)) {
                Toast.makeText(context, "Done speaking", Toast.LENGTH_SHORT).show();
            }
        }
    };
}

