/**
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 * <p>
 * Contact info@realwear.com for further information about the use of this code.
 */

package com.realwear.hmt1developerexamples;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Class which represents a clickable time to start an example
 */
public class MainMenuTile extends LinearLayout implements View.OnClickListener {
    private String mLaunchClass;

    /**
     * Constructor
     *
     * @param context      The context
     * @param commandResID The speech command to activate the tile
     * @param imageResID   The image to display on the tile
     * @param launchClass  The class to launch when the tile is clicked by the user
     */
    public MainMenuTile(Context context, int commandResID, int imageResID, String launchClass) {
        super(context);

        mLaunchClass = launchClass;

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.command_square, this, true);

        setImage(imageResID);
        setSmallText(commandResID);
        setOnClickListener(this);
    }

    /**
     * Set the image displayed on the tile
     *
     * @param resID The image to display
     */
    public void setImage(int resID) {
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        Drawable drawable = getResources().getDrawable(resID, getContext().getTheme());
        iv.setImageDrawable(drawable);
    }

    /**
     * Set the text displayed on the tile
     *
     * @param resID The text to display
     */
    public void setSmallText(int resID) {
        TextView tv = (TextView) findViewById(R.id.smallTextView);
        String text = getResources().getString(resID);
        tv.setText(text);
    }

    /**
     * Called when the tile is clicked
     *
     * @param view This tile
     */
    @Override
    public void onClick(View view) {
        String packageName = getContext().getPackageName();

        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, packageName + "." + mLaunchClass));
        getContext().startActivity(intent);
    }
}
