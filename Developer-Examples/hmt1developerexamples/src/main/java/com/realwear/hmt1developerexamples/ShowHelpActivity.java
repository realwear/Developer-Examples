/**
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 * <p>
 * Contact info@realwear.com for further information about the use of this code.
 */

package com.realwear.hmt1developerexamples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.ArrayList;

/**
 * Activity that shows how to add commands to the Show Help dialog on a HMT-1 device
 */
public class ShowHelpActivity extends Activity {

    // Update help commands intent action
    private static final String ACTION_UPDATE_HELP =
            "com.realwear.wearhf.intent.action.UPDATE_HELP_COMMANDS";

    //
    // Identifiers for adding commands to the Show Help screen
    //
    private static final String EXTRA_TEXT = "com.realwear.wearhf.intent.extra.HELP_COMMANDS";
    private static final String EXTRA_SOURCE = "com.realwear.wearhf.intent.extra.SOURCE_PACKAGE";

    /**
     * Constructor
     *
     * @param savedInstanceState See Android docs
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.show_help_main);
    }

    /**
     * Called when activity is resumed - See Android docs
     */
    @Override
    protected void onResume() {
        super.onResume();

        final ArrayList<String> helpCommands = new ArrayList<>();
        helpCommands.add("Sample 1");
        helpCommands.add("Sample 2");

        final Intent intent = new Intent(ACTION_UPDATE_HELP);
        intent.putStringArrayListExtra(EXTRA_TEXT, helpCommands);
        intent.putExtra(EXTRA_SOURCE, getPackageName());
        sendBroadcast(intent);
    }
}
