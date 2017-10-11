/**
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 * <p>
 * Contact info@realwear.com for further information about the use of this code.
 */

package com.realwear.hmt1developerexamples;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Activity that shows how to accept dictation input from the user on a HMT-1 device
 */
public class DictationActivity extends Activity {

    // Request code identifying dictation events
    private static final int DICTATION_REQUEST_CODE = 34;

    // Dictation intent action
    private final static String ACTION_DICTATION = "com.realwear.keyboard.intent.action.DICTATION";

    private EditText mTextField;

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
        setContentView(R.layout.dictation_main);

        mTextField = (EditText) findViewById(R.id.dictationField);
    }

    /**
     * Listener for when a the launch dictation button is clicked
     *
     * @param view The launch camera button
     */
    public void onLaunchDictation(View view) {
        Intent intent = new Intent(ACTION_DICTATION);
        startActivityForResult(intent, DICTATION_REQUEST_CODE);
    }

    /**
     * Listener for when a the launch keyboard button is clicked
     *
     * @param view The launch camera button
     */
    public void onLaunchKeyboard(View view) {
        mTextField.setFocusable(true);
        mTextField.setFocusableInTouchMode(true);
        mTextField.requestFocus();

        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mTextField, InputMethodManager.SHOW_FORCED);
    }

    /**
     * Listener for result from external activities. Receives text data dictation.
     *
     * @param requestCode See Android docs
     * @param resultCode  See Android docs
     * @param data        See Android docs
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == DICTATION_REQUEST_CODE) {
            String result = "[Error]";
            if (data != null) {
                result = data.getStringExtra("result");
            }

            mTextField.setText(result);
        }
    }
}
