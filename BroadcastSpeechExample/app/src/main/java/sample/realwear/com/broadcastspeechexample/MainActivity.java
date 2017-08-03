package sample.realwear.com.broadcastspeechexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String ACTION_OVERRIDE = "com.realwear.wearhf.intent.action.OVERRIDE_COMMANDS";

    String EXTRA_COMMANDS = "com.realwear.wearhf.intent.extra.COMMANDS";
    String EXTRA_SOURCE = "com.realwear.wearhf.intent.extra.SOURCE_PACKAGE";
    String EXTRA_GLOBAL = "com.realwear.wearhf.intent.extra.GLOBAL_COMMANDS";
    String EXTRA_BUTTON = "com.realwear.wearhf.intent.extra.ACTION_BUTTON_HOME";
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(speechBroadcastReceiver, new IntentFilter(ACTION_SPEECH_EVENT));
        mIntent = new Intent(ACTION_OVERRIDE);
        mIntent.putExtra(EXTRA_COMMANDS, "Start|Stop|Press Me");
        mIntent.putExtra(EXTRA_SOURCE, getPackageName());
        sendBroadcast(mIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(speechBroadcastReceiver);
    }

    String ACTION_SPEECH_EVENT = "com.realwear.wearhf.intent.action.SPEECH_EVENT";
    String EXTRA_COMMAND = "com.realwear.wearhf.intent.extra.COMMAND";
    String EXTRA_ORIG_COMMAND = "com.realwear.wearhf.intent.extra.ORIGINAL_COMMAND";
    String EXTRA_CONFIDENCE = "com.realwear.wearhf.intent.extra.CONFIDENCE";

    private BroadcastReceiver speechBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(ACTION_SPEECH_EVENT)) {
                //Speech detected
                String command = intent.getStringExtra(EXTRA_COMMAND);
                String origCommand = intent.getStringExtra(EXTRA_ORIG_COMMAND);
                int confidence = intent.getIntExtra(EXTRA_CONFIDENCE, -1);

                Toast.makeText(MainActivity.this, command, Toast.LENGTH_LONG).show();

                sendBroadcast(mIntent);
            }
        }
    };
}
