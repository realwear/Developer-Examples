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
import android.content.IntentFilter
import android.content.Intent
import com.realwear.hmt1developerexamples.TTSActivity
import android.content.BroadcastReceiver
import android.content.Context
import android.util.Log
import android.view.WindowInsetsController

/**
 * Activity that shows how use text to speech on a HMT-1 device
 */
class TTSActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tts_main)
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter()
        filter.addAction(ACTION_TTS_FINISHED)
        registerReceiver(ttsReceiver, filter)
        speakNow()
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(ttsReceiver)
    }

    /**
     * Start TTS
     */
    private fun speakNow() {
        val value = getText(R.string.tts_speech) as String
        val intent = Intent(ACTION_TTS)
        intent.putExtra(EXTRA_TEXT, value)
        intent.putExtra(EXTRA_ID, TTS_REQUEST_CODE)
        intent.putExtra(EXTRA_PAUSE, false) // Don't pause speech recogniser while TTS is playing
        sendBroadcast(intent)
    }

    /**
     * Broadcast receiver for being notified when TTS is complete
     */
    private val ttsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == ACTION_TTS_FINISHED) {
                val ttsID = intent.getIntExtra(EXTRA_ID, 0)
                Log.d("TTSExample", "Done Speaking, TTS_ID=$ttsID")
            }
        }
    }

    companion object {
        private const val TTS_REQUEST_CODE = 34

        //
        // Intent actions for starting TTS and for being notified it finishes
        //
        private const val ACTION_TTS = "com.realwear.ttsservice.intent.action.TTS"
        private const val ACTION_TTS_FINISHED = "com.realwear.ttsservice.intent.action.TTS_FINISHED"

        //
        // Identifiers used when starting TTS commands
        //
        private const val EXTRA_TEXT = "text_to_speak"
        private const val EXTRA_ID = "tts_id"
        private const val EXTRA_PAUSE = "pause_speech_recognizer"

    }
}