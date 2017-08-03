package sample.realwear.com.showhelpexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String ACTION_HELP_TEXT = "com.realwear.wearhf.intent.action.UPDATE_HELP_COMMANDS";

    String EXTRA_TEXT = "com.realwear.wearhf.intent.extra.HELP_COMMANDS";
    String EXTRA_SOURCE = "com.realwear.wearhf.intent.extra.SOURCE_PACKAGE";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final ArrayList<String> helpCommands = new ArrayList<>();
        helpCommands.add("Sample 1");
        helpCommands.add("Sample 2");

        final Intent intent = new Intent(ACTION_HELP_TEXT);
        intent.putStringArrayListExtra(EXTRA_TEXT, helpCommands);
        intent.putExtra(EXTRA_SOURCE, getPackageName());
        sendBroadcast(intent);
    }
}
