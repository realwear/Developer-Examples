package sample.realwear.com.broadcastspeechexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    String ACTION_OVERRIDE = "com.realwear.wearhf.intent.action.OVERRIDE_COMMANDS";

    String EXTRA_COMMANDS = "com.realwear.wearhf.intent.extra.COMMANDS";
    String EXTRA_SOURCE = "com.realwear.wearhf.intent.extra.SOURCE_PACKAGE";
    String EXTRA_GLOBAL = "com.realwear.wearhf.intent.extra.GLOBAL_COMMANDS";
    String EXTRA_BUTTON = "com.realwear.wearhf.intent.extra.ACTION_BUTTON_HOME";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent(ACTION_OVERRIDE);
        intent.putExtra(EXTRA_COMMANDS, "Start|Stop|Press Me");
        intent.putExtra(EXTRA_SOURCE, getPackageName());
        sendBroadcast(intent);
    }
}
