/**
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 * <p>
 * Contact info@realwear.com for further information about the use of this code.
 */

package com.realwear.hmt1developerexamples;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;

/**
 * Main activity which displays a list of examples to the user
 */
public class MainActivity extends Activity {

    private MainMenuTileAdaptor mMainMenuTileAdaptor;
    private GridView mGridView;

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
        setContentView(R.layout.activity_main);

        View[] tileList = new View[13];
        tileList[0] = new MainMenuTile(this, R.string.action_button_command, R.drawable.action_button, "ActionButtonActivity");
        tileList[1] = new MainMenuTile(this, R.string.camera_command, R.drawable.camera, "CameraActivity");
        tileList[2] = new MainMenuTile(this, R.string.video_command, R.drawable.camera, "VideoRecordingActivity");
        tileList[3] = new MainMenuTile(this, R.string.document_command, R.drawable.document, "DocumentActivity");
        tileList[4] = new MainMenuTile(this, R.string.movie_command, R.drawable.movie, "MovieActivity");
        tileList[5] = new MainMenuTile(this, R.string.barcode_command, R.drawable.barcode, "BarcodeActivity");

        tileList[6] = new MainMenuTile(this, R.string.asr_command, R.drawable.asr, "SpeechRecognizerActivity");
        tileList[7] = new MainMenuTile(this, R.string.dication_command, R.drawable.dictation, "DictationActivity");
        tileList[8] = new MainMenuTile(this, R.string.tts_command, R.drawable.tts, "TTSActivity");
        tileList[9] = new MainMenuTile(this, R.string.mute_command, R.drawable.microphone_off, "MicrophoneReleaseActivity");
        tileList[10] = new MainMenuTile(this, R.string.audio_command, R.drawable.asr, "AudioCaptureActivity");

        tileList[11] = new MainMenuTile(this, R.string.showhelp_command, R.drawable.show_help, "ShowHelpActivity");
        tileList[12] = new MainMenuTile(this, R.string.bnf_command, R.drawable.ic_format_quote_black_24dp, "BNFGrammarActivity");

        mMainMenuTileAdaptor = new MainMenuTileAdaptor(tileList);
        mGridView = (GridView) findViewById(R.id.gridView);
        mGridView.setAdapter(mMainMenuTileAdaptor);
    }
}
