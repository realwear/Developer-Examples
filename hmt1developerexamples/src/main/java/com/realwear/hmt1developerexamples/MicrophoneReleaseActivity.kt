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
import com.realwear.hmt1developerexamples.MicrophoneReleaseActivity
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.view.View

/**
 * Activity that shows how to release the microphones on a HMT-1 device
 */
class MicrophoneReleaseActivity : Activity() {
    /**
     * Called when the activity is created
     *
     * @param savedInstanceState See Android docs
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.microphone_release_main)
    }

    /**
     * Called when activity is resumed - See Android docs
     */
    override fun onResume() {
        super.onResume()

        //
        // Register to receive microphone events
        //
        val filter = IntentFilter()
        filter.addAction(ACTION_MIC_RELEASED)
        filter.addAction(ACTION_RELEASE_MIC)
        registerReceiver(micBroadcastReceiver, filter)
    }

    /**
     * Called when activity is paused - See Android docs
     */
    override fun onPause() {
        super.onPause()

        //
        // Make sure we release the microphone on pause so we don't go into the background
        // with it still blocking ASR
        //
        val intent = Intent(ACTION_MIC_RELEASED)
        intent.putExtra(EXTRA_SOURCE_PACKAGE, packageName)
        sendBroadcast(intent)
        micBroadcastReceiver?.let { unregisterReceiver(it) }
    }

    /**
     * Listener for when a the release microphone button is clicked
     *
     * @param view The release microphone button
     */
    fun onReleaseMicrophone(view: View?) {
        val intent = Intent(ACTION_RELEASE_MIC)
        intent.putExtra(EXTRA_SOURCE_PACKAGE, packageName)

        // By default system will put up big red display to say that
        // 'speech is off, press Action button to resume'
        // If we don't want any text on screen, set the HIDE_TEXT extra to true
        intent.putExtra(EXTRA_HIDE_TEXT, false)

        //
        // If we want to show text but change the message, use the MUTE_TEXT extra
        //
        intent.putExtra(EXTRA_MUTE_TEXT, "Mic is Off. Press Action Button to Resume")
        sendBroadcast(intent)
    }

    /**
     * Broadcast Receiver to receive microphone release actions
     */
    private val micBroadcastReceiver: BroadcastReceiver? = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val sourcePackage = intent.getStringExtra(EXTRA_SOURCE_PACKAGE)
            if (sourcePackage != null) {
                if (sourcePackage == applicationContext.packageName) {
                    return
                }
            }
            if (intent.action == ACTION_MIC_RELEASED) {
                //
                // The system has now released the microphones
                //
            } else if (intent.action == ACTION_RELEASE_MIC) {
                //
                // The system wants to access the microphone so release and inform the system
                //
                val newIntent = Intent(ACTION_MIC_RELEASED)
                intent.putExtra(EXTRA_SOURCE_PACKAGE, applicationContext.packageName)
                sendBroadcast(newIntent)
            }
        }
    }

    companion object {
        //
        // Intent actions to request and release the microphone
        //
        private const val ACTION_RELEASE_MIC = "com.realwear.wearhf.intent.action.RELEASE_MIC"
        private const val ACTION_MIC_RELEASED = "com.realwear.wearhf.intent.action.MIC_RELEASED"

        //
        // Identifiers for the settings which allow modification of the on screen message shown when
        // the microphone has been released
        //
        private const val EXTRA_MUTE_TEXT = "com.realwear.wearhf.intent.extra.MUTE_TEXT"
        private const val EXTRA_HIDE_TEXT = "com.realwear.wearhf.intent.extra.HIDE_TEXT"
        private const val EXTRA_SOURCE_PACKAGE = "com.realwear.wearhf.intent.extra.SOURCE_PACKAGE"
    }
}