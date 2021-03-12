/**
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
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Activity that shows how to register BNF grammar on a HMT-1 device.
 */
public class BNFGrammarActivity extends Activity {
    // The action that WearHF will use for broadcasting when a voice command is spoken.
    private static final String ACTION_SPEECH_EVENT =
            "com.realwear.wearhf.intent.action.SPEECH_EVENT";

    // The BNF string to register with WearHF.
    private final String TIME_BNF = "#BNF+EM V2.0;" +
            "!grammar Commands;\n" +
            "!start <Commands>;\n" +
            "<Commands>:<global_commands>|<Hour> !optional(<Minute>);\n" +
            "<Minute>:1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|55|56|57|58|59;\n" +
            "<Hour>:1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24;";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.bnf_activity);

        //
        // Set the BNF for this screen.
        // We are adding the BNF string to the TextView in the layout, but they can be added
        // to any view.
        // The broadcast receiver will get the result when the voice command is spoken.
        //
        findViewById(R.id.bnfDescription).setContentDescription("hf_override:" + TIME_BNF);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (asrBroadcastReceiver != null) {
            unregisterReceiver(asrBroadcastReceiver);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(asrBroadcastReceiver, new IntentFilter(ACTION_SPEECH_EVENT));
    }

    /**
     * Broadcast receiver for being notified when speech events occur
     */
    private BroadcastReceiver asrBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action != null && action.equals(ACTION_SPEECH_EVENT)) {
                final String asrCommand = intent.getStringExtra("command");
                Toast.makeText(getBaseContext(), asrCommand, Toast.LENGTH_LONG).show();
            }
        }
    };
}
