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
import android.util.Log;
import android.view.WindowManager;

/**
 * Activity that shows how use text to speech on a HMT-1 device
 */
public class TTSActivity extends Activity {
    //
    // Intent actions for starting TTS and for being notified it finishes
    //
    private String ACTION_TTS = "com.realwear.ttsservice.intent.action.TTS";
    private String ACTION_TTS_FINISHED = "com.realwear.ttsservice.intent.action.TTS_FINISHED";

    //
    // Identifiers used when starting TTS commands
    //
    private String EXTRA_TEXT = "text_to_speak";
    private String EXTRA_ID = "tts_id";
    private String EXTRA_PAUSE = "pause_speech_recognizer";
    private static final int TTS_REQUEST_CODE = 34;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.tts_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_TTS_FINISHED);
        registerReceiver(ttsReceiver, filter);

        speakNow();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ttsReceiver != null) {
            unregisterReceiver(ttsReceiver);
        }
    }

    /**
     * Start TTS
     */
    public void speakNow() {
        String value = (String) getText(R.string.tts_speech);

        Intent intent = new Intent(ACTION_TTS);
        intent.putExtra(EXTRA_TEXT, value);
        intent.putExtra(EXTRA_ID, TTS_REQUEST_CODE);
        intent.putExtra(EXTRA_PAUSE, false); // Don't pause speech recogniser while TTS is playing
        sendBroadcast(intent);
    }

    /**
     * Broadcast receiver for being notified when TTS is complete
     */
    private BroadcastReceiver ttsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(ACTION_TTS_FINISHED)) {
                int ttsID = intent.getIntExtra(EXTRA_ID, 0);
                Log.d("TTSExample", "Done Speaking, TTS_ID=" + ttsID);
            }
        }
    };
}

