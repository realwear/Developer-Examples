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
import com.realwear.hmt1developerexamples.databinding.ActionButtonMainBinding

/**
 * Activity that shows how to interact with the action button on a HMT-1 device
 */
class ActionButtonActivity : Activity() {
    private val binding: ActionButtonMainBinding by lazy { ActionButtonMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Disable the action button default behaviour
        binding.actionButtonImageView.contentDescription = "hf_no_ptt_home"
    }

    override fun onResume() {
        super.onResume()
        binding.actionButtonImageView.setImageResource(R.drawable.radio_button_unchecked)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    /**
     * Sets radio button to checked if the [keyCode] is ACTION_BTN_KEY_CODE and returns true,
     * otherwise returns call to superclass' onKeyDown with [keyCode] and [event]
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return when (keyCode) {
            ACTION_BTN_KEY_CODE -> {
                binding.actionButtonImageView.setImageResource(R.drawable.radio_button_red)
                true
            }
            else -> super.onKeyDown(keyCode, event)
        }
    }

    /**
     * Sets the radio button to unchecked if the [keyCode] is ACTION_BTN_KEY_CODE and returns true,
     * otherwise returns call to superclass' onKeyUp with [keyCode] and [event]
     */
    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        return when (keyCode) {
            ACTION_BTN_KEY_CODE -> {
                binding.actionButtonImageView.setImageResource(R.drawable.radio_button_unchecked)
                true
            }
            else -> super.onKeyUp(keyCode, event)
        }
    }

    companion object {
        private const val ACTION_BTN_KEY_CODE = 500
    }
}