/*
 * RealWear Development Software, Source Code and Object Code.
 * (c) RealWear, Inc. All rights reserved.
 *
 * Contact info@realwear.com for further information about the use of this code.
 */
package com.realwear.hmt1developerexamples

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.realwear.hmt1developerexamples.databinding.ActivityMainBinding

/**
 * Main activity which displays a list of examples to the user.
 */
class MainActivity : Activity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setMainMenuTileAdapter()
        showAppVersion()
    }

    /**
     * Return a new menu tile with an [icon] and [voiceCommand], and which [activity] to launch
     * when selected.
     */
    private fun createMenuTile(voiceCommand: Int, icon: Int, activity: String): MainMenuTile {
        return MainMenuTile(this, voiceCommand, icon, activity)
    }

    /**
     * Setup the main menu GridView's adapter
     */
    private fun setMainMenuTileAdapter() {
        val mainMenuTiles = createMainMenuTiles()
        binding.gridView.adapter = MainMenuTileAdaptor(mainMenuTiles)
    }

    /**
     * Return an array of tile views for the main menu.
     */
    private fun createMainMenuTiles(): Array<View> {
        return arrayOf(
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
    }

    /**
     * Determine and render the current version number on screen.
     */
    @SuppressLint("SetTextI18n")
    private fun showAppVersion() {
        val versionName = runCatching {
            baseContext.packageManager.getPackageInfo(
                baseContext.packageName, 0
            )?.versionName
        }.onFailure {
            Log.e(TAG, "Failed to display version number", it)
        }.getOrNull()

        binding.versionTextField.text = "version: $versionName"
    }

    companion object {
        private const val TAG = "DevEx"
    }
}