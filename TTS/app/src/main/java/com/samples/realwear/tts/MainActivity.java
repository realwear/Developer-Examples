package com.samples.realwear.tts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 */

/**
 * Example Activity for opening using the TTS engine
 */
public class MainActivity extends AppCompatActivity {

    String ACTION_TTS = "com.realwear.wearhf.intent.action.TTS";
    String ACTION_TTS_FINISHED = "com.realwear.wearhf.intent.action.tts_finished";

    String EXTRA_TEXT = "com.realwear.wearhf.intent.extra.text_to_speak";
    String EXTRA_ID = "com.realwear.wearhf.intent.extra.tts_id";
    String EXTRA_PAUSE = "com.realwear.wearhf.intent.extra.pause_speech_recognizer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = new Intent(ACTION_TTS);
        intent.putExtra(EXTRA_TEXT, "Hello World");
        intent.putExtra(EXTRA_ID, 34);
        sendBroadcast(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(ttsBroadcastReceiver, new IntentFilter(ACTION_TTS_FINISHED));
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(ttsBroadcastReceiver);
    }

    private BroadcastReceiver ttsBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //When the TTS is pressed we must release our microphone
            if (intent.getAction().equals(ACTION_TTS_FINISHED)) {
                //TTS completed
            }
        }
    };

}
