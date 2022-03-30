/*
 * RealWear Development Software, Source Code and Object Code.
 * (c) RealWear, Inc. All rights reserved.
 *
 * Contact info@realwear.com for further information about the use of this code.
 */
package com.realwear.hmt1developerexamples

import android.app.Activity
import android.os.Bundle
import android.widget.GridView
import android.widget.TextView
import android.content.pm.PackageManager
import android.util.Log
import android.view.View

/**
 * Main activity which displays a list of examples to the user.
 */
class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tileList = arrayOf<View>(
            createMenuTile(
                R.string.action_button_command,
                R.drawable.action_button,
                "ActionButtonActivity"
            ),
            createMenuTile(R.string.camera_command, R.drawable.camera, "CameraActivity"),
            createMenuTile(R.string.document_command, R.drawable.document, "DocumentActivity"),
            createMenuTile(R.string.movie_command, R.drawable.movie, "MovieActivity"),
            createMenuTile(R.string.barcode_command, R.drawable.barcode, "BarcodeActivity"),
            createMenuTile(R.string.asr_command, R.drawable.asr, "SpeechRecognizerActivity"),
            createMenuTile(R.string.dictation_command, R.drawable.dictation, "DictationActivity"),
            createMenuTile(R.string.tts_command, R.drawable.tts, "TTSActivity"),
            createMenuTile(
                R.string.mute_command,
                R.drawable.microphone_off,
                "MicrophoneReleaseActivity"
            ),
            createMenuTile(R.string.audio_command, R.drawable.asr, "AudioCaptureActivity"),
            createMenuTile(R.string.showhelp_command, R.drawable.show_help, "ShowHelpActivity"),
            createMenuTile(
                R.string.bnf_command,
                R.drawable.ic_format_quote_black_24dp,
                "BNFGrammarActivity"
            )
        )
        val mainMenuTileAdaptor = MainMenuTileAdaptor(tileList)
        val gridView = findViewById<GridView>(R.id.gridView)
        gridView.adapter = mainMenuTileAdaptor
        showAppVersion()
    }

    /**
     * Create a menu tile button.
     *
     * @param voiceCommand The voice command for the tile.
     * @param icon         The icon for the tile.
     * @param activity     The activity to launch when the tile is selected.
     * @return The created tile.
     */
    private fun createMenuTile(voiceCommand: Int, icon: Int, activity: String): MainMenuTile {
        return MainMenuTile(this, voiceCommand, icon, activity)
    }

    /**
     * Show the current version number.
     */
    private fun showAppVersion() {
        val versionTextView = findViewById<TextView>(R.id.versionTextField)
        try {
            val packageInfo = baseContext.packageManager.getPackageInfo(
                baseContext.packageName, 0
            )
            versionTextView.text = "version: " + packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "Failed to display version number", e)
        }
    }

    companion object {
        private const val TAG = "DevEx"
    }
}