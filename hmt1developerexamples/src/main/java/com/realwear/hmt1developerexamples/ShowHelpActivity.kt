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
import android.content.Intent
import com.realwear.hmt1developerexamples.ShowHelpActivity
import java.util.ArrayList

/**
 * Activity that shows how to add commands to the Show Help dialog on a HMT-1 device
 */
class ShowHelpActivity : Activity() {
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
        setContentView(R.layout.show_help_main)
    }

    /**
     * Called when activity is resumed - See Android docs
     */
    override fun onResume() {
        super.onResume()
        val helpCommands = arrayListOf("Sample 1", "Sample 2")
        val intent = Intent(ACTION_UPDATE_HELP)
        intent.putStringArrayListExtra(EXTRA_TEXT, helpCommands)
        intent.putExtra(EXTRA_SOURCE, packageName)
        sendBroadcast(intent)
    }

    companion object {
        // Update help commands intent action
        private const val ACTION_UPDATE_HELP =
            "com.realwear.wearhf.intent.action.UPDATE_HELP_COMMANDS"

        //
        // Identifiers for adding commands to the Show Help screen
        //
        private const val EXTRA_TEXT = "com.realwear.wearhf.intent.extra.HELP_COMMANDS"
        private const val EXTRA_SOURCE = "com.realwear.wearhf.intent.extra.SOURCE_PACKAGE"
    }
}