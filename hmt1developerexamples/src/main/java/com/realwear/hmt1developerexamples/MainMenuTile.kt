/**
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 *
 *
 * Contact info@realwear.com for further information about the use of this code.
 */
package com.realwear.hmt1developerexamples

import android.widget.LinearLayout
import com.realwear.hmt1developerexamples.R
import android.widget.TextView
import android.content.Intent
import android.content.ComponentName
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView

/**
 * Class which represents a clickable time to start an example
 */
class MainMenuTile
/**
 * Constructor
 *
 * @param context      The context
 * @param commandResID The speech command to activate the tile
 * @param imageResID   The image to display on the tile
 * @param _launchClass  The class to launch when the tile is clicked by the user
 */(
    context: Context,
    commandResID: Int,
    imageResID: Int,
    private val _launchClass: String
) : LinearLayout(context), View.OnClickListener {

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.command_square, this, true)
        setImage(imageResID)
        setSmallText(commandResID)
        setOnClickListener(this)
    }

    /**
     * Set the image displayed on the tile
     *
     * @param resID The image to display
     */
    private fun setImage(resID: Int) {
        val iv = findViewById<View>(R.id.imageView) as ImageView
        val drawable = resources.getDrawable(resID, context.theme)
        iv.setImageDrawable(drawable)
    }

    /**
     * Set the text displayed on the tile
     *
     * @param resID The text to display
     */
    private fun setSmallText(resID: Int) {
        val tv = findViewById<View>(R.id.smallTextView) as TextView
        val text = resources.getString(resID)
        tv.text = text
    }

    /**
     * Called when the tile is clicked
     *
     * @param view This tile
     */
    override fun onClick(view: View) {
        val packageName = context.packageName
        val intent = Intent()
        intent.component = ComponentName(packageName, "$packageName.$_launchClass")
        context.startActivity(intent)
    }


}