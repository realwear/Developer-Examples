/**
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 * <p>
 * Contact info@realwear.com for further information about the use of this code.
 */

package com.samples.realwear.wearml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Main activity that displays a simple layout that can be improved using WearML
 */
public class MainActivity extends AppCompatActivity {

    private TextView mTitle;
    private TextView mDescription;

    /**
     * Called when the activity is created
     *
     * @param savedInstanceState See Android docs
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mTitle = (TextView) findViewById(R.id.title);
        mDescription = (TextView) findViewById(R.id.description);
    }

    /**
     * Called when the user selects Full Boar Scotch Ale
     *
     * @param view The Full Boar Scotch Ale button
     */
    public void onBoarClick(View view) {
        setContent(R.string.boar_title, R.string.boar_description);
    }

    /**
     * Called when the user selects California Sunshine RyePA
     *
     * @param view The California Sunshine RyePA button
     */
    public void onSunshineClick(View view) {
        setContent(R.string.sunshine_title, R.string.sunshine_description);
    }

    /**
     * Called when the user selects Deadicated Amber Ale
     *
     * @param view The Deadicated Amber Ale button
     */
    public void onAmberClick(View view) {
        setContent(R.string.amber_title, R.string.amber_description);
    }

    /**
     * Update the activity to show the details for an ale
     *
     * @param resTitle       The title of the ale
     * @param resDescription The description of the ale
     */
    public void setContent(int resTitle, int resDescription) {
        mTitle.setText(resTitle);
        mDescription.setText(resDescription);
    }
}
