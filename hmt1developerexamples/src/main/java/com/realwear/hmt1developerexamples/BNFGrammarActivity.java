/*
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 * <p>
 * Contact info@realwear.com for further information about the use of this code.
 */

package com.realwear.hmt1developerexamples;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Activity that shows how to register BNF grammar on a HMT-1 device
 */
public class BNFGrammarActivity extends Activity {
    // Identifier for speech events
    private static final String ACTION_SPEECH_EVENT =
            "com.realwear.wearhf.intent.action.SPEECH_EVENT";

    // Intent actions for taking over voice commands
    private static final String ACTION_OVERRIDE_COMMANDS =
            "com.realwear.wearhf.intent.action.OVERRIDE_COMMANDS";
    private static final String EXTRA_COMMANDS =
            "com.realwear.wearhf.intent.extra.COMMANDS";

    // Intent actions for restoring voice commands when we no longer want to capture
    private static final String ACTION_RESTORE_COMMANDS =
            "com.realwear.wearhf.intent.action.RESTORE_COMMANDS";

    // Identifier for the package
    private static final String EXTRA_SOURCE_PACKAGE =
            "com.realwear.wearhf.intent.extra.SOURCE_PACKAGE";

    private final String TIME_BNF = "#BNF+EM V2.0;" +
            "!grammar Commands;\n" +
            "!start <Commands>;\n" +
            "<Commands>:<global_commands>|<Hour> !optional(<Minute>);\n" +
            "<Minute>:1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|55|56|57|58|59;\n" +
            "<Hour>:1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24;";

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.bnf_activity);

        mHandler = new Handler();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (asrBroadcastReceiver != null) {
            unregisterReceiver(asrBroadcastReceiver);
        }

        Intent intent = new Intent(ACTION_RESTORE_COMMANDS);
        intent.putExtra(EXTRA_SOURCE_PACKAGE, getPackageName());
        sendBroadcast(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(asrBroadcastReceiver, new IntentFilter(ACTION_SPEECH_EVENT));

        // send the commands delayed to make sure the app is opened on the screen before
        // registering the commands
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendCommands();
            }
        }, 300);
    }

    /**
     * Broadcast receiver for being notified when speech events occur
     */
    private BroadcastReceiver asrBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action != null && action.equals(ACTION_SPEECH_EVENT)) {
                String asrCommand = intent.getStringExtra("command");
                Log.d("asrCommand", "onReceive: " + asrCommand);
                Toast.makeText(getBaseContext(), asrCommand, Toast.LENGTH_LONG).show();
            }

            //Always reload commands.
            sendCommands();
        }
    };

    /**
     * Register voice commands
     */
    private void sendCommands() {
        Intent intent = new Intent(ACTION_OVERRIDE_COMMANDS);
        intent.putExtra(EXTRA_SOURCE_PACKAGE, getPackageName());
        intent.putExtra(EXTRA_COMMANDS, TIME_BNF);
        sendBroadcast(intent);
    }
}
