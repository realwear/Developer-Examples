/**
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 * <p>
 * Contact info@realwear.com for further information about the use of this code.
 */

package com.realwear.hmt1developerexamples;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Activity that shows how to interact with the action button on a HMT-1 device
 */
public class ActionButtonActivity extends Activity {
    private static final int ActionBtnKeyCode = 500;

    private ImageView mActionButtonImageView;

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
        setContentView(R.layout.action_button_main);

        mActionButtonImageView = (ImageView) findViewById(R.id.actionButtonImageView);

        // Disable the action button default behaviour
        mActionButtonImageView.setContentDescription("hf_no_ptt_home");
    }

    /**
     * Called when activity is resumed - See Android docs
     */
    @Override
    protected void onResume() {
        super.onResume();
        mActionButtonImageView.setImageResource(R.drawable.radio_button_unchecked);
    }

    /**
     * Called when activity is paused - See Android docs
     */
    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }

    /**
     * Called when a key is pressed - See Android docs
     *
     * @param keyCode The value in event.getKeyCode()
     * @param event   Description of the key event
     * @return true if the action button was pressed, otherwise false
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case ActionBtnKeyCode:
                mActionButtonImageView.setImageResource(R.drawable.radio_button_red);
                return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * Called when a key is released - See Android docs
     *
     * @param keyCode The value in event.getKeyCode()
     * @param event   Description of the key event
     * @return true if the action button was pressed, otherwise false
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case ActionBtnKeyCode:
                mActionButtonImageView.setImageResource(R.drawable.radio_button_unchecked);
                return true;
        }

        return super.onKeyUp(keyCode, event);
    }
}
