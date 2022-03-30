/**
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 *
 *
 * Contact info@realwear.com for further information about the use of this code.
 */
package com.realwear.hmt1developerexamples

import android.app.Activity
import android.widget.TextView
import android.os.Bundle
import android.view.WindowManager
import com.realwear.hmt1developerexamples.R
import android.content.IntentFilter
import com.realwear.hmt1developerexamples.SpeechRecognizerActivity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Activity that shows how to register voice commands with the ASR on a HMT-1 device.
 */
class SpeechRecognizerActivity : Activity() {
    private var mQuantityView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.speech_main)
        mQuantityView = findViewById<TextView?>(R.id.quantityView)
            .also {
                //
                // Set the voice commands for this screen.
                // We are adding the voice commands to the TextView in the layout, but they can be added
                // to any view.
                // The broadcast receiver will get the result when the voice command is spoken.
                //
                it.contentDescription = "hf_add_commands:Quantity 1|Quantity 2|Quantity 3"
            }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(asrBroadcastReceiver, IntentFilter(ACTION_SPEECH_EVENT))
    }

    override fun onPause() {
        super.onPause()
        asrBroadcastReceiver?.let { unregisterReceiver(it) }
    }

    /**
     * Broadcast receiver for being notified when voice commands are triggered.
     */
    private val asrBroadcastReceiver: BroadcastReceiver? = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == ACTION_SPEECH_EVENT) {
                val asrCommand = intent.getStringExtra(EXTRA_RESULT)
                mQuantityView!!.text = asrCommand
            }
        }
    }

    companion object {
        // The action that WearHF will use for broadcasting when a voice command is spoken.
        private const val ACTION_SPEECH_EVENT = "com.realwear.wearhf.intent.action.SPEECH_EVENT"

        // Identifier for the voice command that was is spoken.
        private const val EXTRA_RESULT = "command"
    }
}