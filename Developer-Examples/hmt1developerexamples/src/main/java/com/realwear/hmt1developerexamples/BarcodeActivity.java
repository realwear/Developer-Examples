/**
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 * <p>
 * Contact info@realwear.com for further information about the use of this code.
 */

package com.realwear.hmt1developerexamples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Activity that shows how to scan a barcode using a HMT-1 device
 */
public class BarcodeActivity extends Activity {

    // Request code identifying the barcode scanner events
    private static final int BARCODE_REQUEST_CODE = 1984;

    // Barcode scanner intent action
    private static final String SCAN_BARCODE =
            "com.realwear.barcodereader.intent.action.SCAN_BARCODE";

    // Identifier for the result string returned by the barcode scanner
    private static final String EXTRA_RESULT = "com.realwear.barcodereader.intent.extra.RESULT";

    //
    // Available barcode symbologies
    //
    private static final String EXTRA_CODE_128 = "com.realwear.barcodereader.intent.extra.CODE_128";
    private static final String EXTRA_CODE_DM = "com.realwear.barcodereader.intent.extra.CODE_DM";
    private static final String EXTRA_CODE_EAN_UPC =
            "com.realwear.barcodereader.intent.extra.CODE_EAN_UPC";
    private static final String EXTRA_CODE_QR = "com.realwear.barcodereader.intent.extra.CODE_QR";

    private TextView mBarcodeResultsView;

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
        setContentView(R.layout.barcode_main);

        mBarcodeResultsView = (TextView) findViewById(R.id.barcode_textview);
        mBarcodeResultsView.setText("");
    }

    /**
     * Listener for when a the launch barcode scanner button is clicked
     *
     * @param view The launch barcode scanner button
     */
    public void onLaunchBarcode(View view) {
        mBarcodeResultsView.setText("");

        Intent intent = new Intent(SCAN_BARCODE);

        //
        // Set which symbologies are enabled. If none is specified, all are enabled by default
        //
        intent.putExtra(EXTRA_CODE_128, false);
        intent.putExtra(EXTRA_CODE_DM, true);
        intent.putExtra(EXTRA_CODE_EAN_UPC, true);
        intent.putExtra(EXTRA_CODE_QR, true);

        startActivityForResult(intent, BARCODE_REQUEST_CODE);
    }

    /**
     * Listener for result from external activities. Receives barcode data from scanner.
     *
     * @param requestCode See Android docs
     * @param resultCode  See Android docs
     * @param data        See Android docs
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == BARCODE_REQUEST_CODE) {
            String result = "[No Barcode]";
            if (data != null) {
                result = data.getStringExtra(EXTRA_RESULT);
            }

            mBarcodeResultsView.setText(result);
        }
    }
}
