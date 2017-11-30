package com.realwear.hmt1developerexamples;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Luke on 29/11/2017.
 */

public class BNFGrammarActivity extends Activity {

    private static final String ACTION_SPEECH_EVENT = "com.realwear.wearhf.intent.action.SPEECH_EVENT";
    //
    // Intent actions for registering voice commands and for being notified when they are triggered
    //
    private static final String ACTION_OVERRIDE_COMMANDS =
            "com.realwear.wearhf.intent.action.OVERRIDE_COMMANDS";
    private static final String ACTION_RESTORE_COMMANDS =
            "com.realwear.wearhf.intent.action.RESTORE_COMMANDS";

    // Identifier for the package that is setting the voice commands
    private static final String EXTRA_SOURCE_PACKAGE =
            "com.realwear.wearhf.intent.extra.SOURCE_PACKAGE";

    // Identifier used when registering voice commands
    private static final String EXTRA_COMMANDS =
            "com.realwear.wearhf.intent.extra.COMMANDS";

    /*
        BNF Grammar for allowing a user to say the hour and minute
     */
    private static String timeBNF = "#BNF+EM V2.0;" +
            "!grammar Commands;\n" +
            "!start <Commands>;\n" +
            "<Commands>:<global_commands>|<Hour> !optional(<Minute>);\n" +
            "<Minute>:1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|55|56|57|58|59;\n" +
            "<Hour>:1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24;";

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
        setContentView(R.layout.bnf_acitivity);

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (asrBroadcastReceiver!=null)unregisterReceiver(asrBroadcastReceiver);

        Intent intent = new Intent(ACTION_RESTORE_COMMANDS);
        intent.putExtra(EXTRA_SOURCE_PACKAGE, getPackageName());
        sendBroadcast(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(asrBroadcastReceiver, new IntentFilter(ACTION_SPEECH_EVENT));

        sendCommands();
    }

    private BroadcastReceiver asrBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            //     Log.d(TAG, "Broadcast Action=" + action);
            if (action.equals(ACTION_SPEECH_EVENT)) {
                String asrCommand = intent.getStringExtra("command");
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
        intent.putExtra(EXTRA_COMMANDS, timeBNF);
        sendBroadcast(intent);
    }
}
