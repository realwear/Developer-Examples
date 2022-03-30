/**
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 *
 *
 * Contact info@realwear.com for further information about the use of this code.
 */
package com.realwear.hmt1developerexamples

import android.app.Activity
import android.widget.TextView
import android.os.Bundle
import android.view.WindowManager
import com.realwear.hmt1developerexamples.R
import android.content.Intent
import android.view.View
import com.realwear.hmt1developerexamples.BarcodeActivity

/**
 * Activity that shows how to scan a barcode using a HMT-1 device
 */
class BarcodeActivity : Activity() {
    private var mBarcodeResultsView: TextView? = null

    /**
     * Called when the activity is created
     *
     * @param savedInstanceState See Android docs
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.barcode_main)
        mBarcodeResultsView = findViewById<View>(R.id.barcode_textview) as TextView
        mBarcodeResultsView?.text = ""
    }

    /**
     * Listener for when a the launch barcode scanner button is clicked
     *
     * @param view The launch barcode scanner button
     */
    fun onLaunchBarcode(view: View?) {
        mBarcodeResultsView?.text = ""
        val intent = Intent(SCAN_BARCODE)

        //
        // Set which symbologies are enabled. If none is specified, all are enabled by default
        //
        intent.putExtra(EXTRA_CODE_128, false)
        intent.putExtra(EXTRA_CODE_DM, true)
        intent.putExtra(EXTRA_CODE_EAN_UPC, true)
        intent.putExtra(EXTRA_CODE_QR, true)
        startActivityForResult(intent, BARCODE_REQUEST_CODE)
    }

    /**
     * Listener for result from external activities. Receives barcode data from scanner.
     *
     * @param requestCode See Android docs
     * @param resultCode  See Android docs
     * @param data        See Android docs
     */
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode == RESULT_OK && requestCode == BARCODE_REQUEST_CODE) {
            val result = data.getStringExtra(EXTRA_RESULT)
            mBarcodeResultsView?.text = result
        }
    }

    companion object {
        // Request code identifying the barcode scanner events
        private const val BARCODE_REQUEST_CODE = 1984

        // Barcode scanner intent action
        private const val SCAN_BARCODE = "com.realwear.barcodereader.intent.action.SCAN_BARCODE"

        // Identifier for the result string returned by the barcode scanner
        private const val EXTRA_RESULT = "com.realwear.barcodereader.intent.extra.RESULT"

        //
        // Available barcode symbologies
        //
        private const val EXTRA_CODE_128 = "com.realwear.barcodereader.intent.extra.CODE_128"
        private const val EXTRA_CODE_DM = "com.realwear.barcodereader.intent.extra.CODE_DM"
        private const val EXTRA_CODE_EAN_UPC = "com.realwear.barcodereader.intent.extra.CODE_EAN_UPC"
        private const val EXTRA_CODE_QR = "com.realwear.barcodereader.intent.extra.CODE_QR"
    }
}