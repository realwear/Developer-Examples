/*
 * RealWear Development Software, Source Code and Object Code.
 * (c) RealWear, Inc. All rights reserved.
 *
 * Contact info@realwear.com for further information about the use of this code.
 */

package com.realwear.hmt1developerexamples;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;

/**
 * Main activity which displays a list of examples to the user.
 */
public class MainActivity extends Activity {
    private static final String TAG = "DevEx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        final View[] tileList = new View[]{
                createMenuTile(R.string.action_button_command, R.drawable.action_button, "ActionButtonActivity"),
                createMenuTile(R.string.camera_command, R.drawable.camera, "CameraActivity"),
                createMenuTile(R.string.document_command, R.drawable.document, "DocumentActivity"),
                createMenuTile(R.string.movie_command, R.drawable.movie, "MovieActivity"),
                createMenuTile(R.string.barcode_command, R.drawable.barcode, "BarcodeActivity"),

                createMenuTile(R.string.asr_command, R.drawable.asr, "SpeechRecognizerActivity"),
                createMenuTile(R.string.dication_command, R.drawable.dictation, "DictationActivity"),
                createMenuTile(R.string.tts_command, R.drawable.tts, "TTSActivity"),
                createMenuTile(R.string.mute_command, R.drawable.microphone_off, "MicrophoneReleaseActivity"),
                createMenuTile(R.string.audio_command, R.drawable.asr, "AudioCaptureActivity"),

                createMenuTile(R.string.showhelp_command, R.drawable.show_help, "ShowHelpActivity"),
                createMenuTile(R.string.bnf_command, R.drawable.ic_format_quote_black_24dp, "BNFGrammarActivity")
        };

        final MainMenuTileAdaptor mainMenuTileAdaptor = new MainMenuTileAdaptor(tileList);
        final GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(mainMenuTileAdaptor);

        showAppVersion();
    }

    /**
     * Create a menu tile button.
     *
     * @param voiceCommand The voice command for the tile.
     * @param icon         The icon for the tile.
     * @param activity     The activity to launch when the tile is selected.
     * @return The created tile.
     */
    private MainMenuTile createMenuTile(int voiceCommand, int icon, String activity) {
        return new MainMenuTile(this, voiceCommand, icon, activity);
    }

    /**
     * Show the current version number.
     */
    private void showAppVersion() {
        final TextView versionTextView = findViewById(R.id.versionTextField);

        try {
            final PackageInfo packageInfo = getBaseContext().getPackageManager().getPackageInfo(
                    getBaseContext().getPackageName(), 0);
            versionTextView.setText("version: " + packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to display version number", e);
        }
    }
}
