/**
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 *
 *
 * Contact info@realwear.com for further information about the use of this code.
 */
package com.realwear.hmt1developerexamples

import android.app.Activity
import android.widget.EditText
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Activity that shows how to accept dictation input from the user on a HMT-1 device
 */
class DictationActivity : Activity() {
    private val _textField: EditText by lazy { findViewById<View>(R.id.dictationField) as EditText }

    /**
     * Called when the activity is created
     *
     * @param savedInstanceState See Android docs
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dictation_main)
    }

    /**
     * Listener for when a the launch dictation button is clicked
     *
     * @param view The launch camera button
     */
    fun onLaunchDictation(view: View?) {
        val intent = Intent(ACTION_DICTATION)
        startActivityForResult(intent, DICTATION_REQUEST_CODE)
    }

    /**
     * Listener for when a the launch keyboard button is clicked
     *
     * @param view The launch camera button
     */
    fun onLaunchKeyboard(view: View?) {
        _textField.isFocusable = true
        _textField.isFocusableInTouchMode = true
        _textField.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(_textField, InputMethodManager.SHOW_FORCED)
    }

    /**
     * Listener for result from external activities. Receives text data dictation.
     *
     * @param requestCode See Android docs
     * @param resultCode  See Android docs
     * @param data        See Android docs
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode == RESULT_OK && requestCode == DICTATION_REQUEST_CODE) {
            val result: String? = data.getStringExtra("result")

            _textField.setText(result)
        }
    }

    companion object {
        // Request code identifying dictation events
        private const val DICTATION_REQUEST_CODE = 34

        // Dictation intent action
        private const val ACTION_DICTATION = "com.realwear.keyboard.intent.action.DICTATION"
    }
}