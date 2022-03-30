/**
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 *
 *
 * Contact info@realwear.com for further information about the use of this code.
 */
package com.realwear.hmt1developerexamples

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView

/**
 * Activity that shows how to interact with the action button on a HMT-1 device
 */
class ActionButtonActivity : Activity() {
    private val _actionButtonImageView: ImageView by lazy {
        findViewById<View>(R.id.actionButtonImageView) as ImageView
    }

    /**
     * Called when the activity is created
     *
     * @param savedInstanceState See Android docs
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.action_button_main)

        // Disable the action button default behaviour
        _actionButtonImageView.contentDescription = "hf_no_ptt_home"
    }

    /**
     * Called when activity is resumed - See Android docs
     */
    override fun onResume() {
        super.onResume()
        _actionButtonImageView.setImageResource(R.drawable.radio_button_unchecked)
    }

    /**
     * Called when activity is paused - See Android docs
     */
    override fun onPause() {
        super.onPause()
        finish()
    }

    /**
     * Called when a key is pressed - See Android docs
     *
     * @param keyCode The value in event.getKeyCode()
     * @param event   Description of the key event
     * @return true if the action button was pressed, otherwise false
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            ACTION_BTN_KEY_CODE -> {
                _actionButtonImageView.setImageResource(R.drawable.radio_button_red)
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * Called when a key is released - See Android docs
     *
     * @param keyCode The value in event.getKeyCode()
     * @param event   Description of the key event
     * @return true if the action button was pressed, otherwise false
     */
    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            ACTION_BTN_KEY_CODE -> {
                _actionButtonImageView.setImageResource(R.drawable.radio_button_unchecked)
                return true
            }
        }
        return super.onKeyUp(keyCode, event)
    }

    companion object {
        private const val ACTION_BTN_KEY_CODE = 500
    }
}