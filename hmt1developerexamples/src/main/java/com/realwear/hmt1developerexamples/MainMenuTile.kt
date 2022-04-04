/**
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 *
 *
 * Contact info@realwear.com for further information about the use of this code.
 */
package com.realwear.hmt1developerexamples

import android.annotation.SuppressLint
import android.widget.LinearLayout
import android.content.Intent
import android.content.ComponentName
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.realwear.hmt1developerexamples.databinding.CommandSquareBinding

/**
 * Given the [context], MainMenuTile represents a clickable tile constructed with the icon
 * [commandResID] and title [imageResID].
 *
 * Launches [launchClass] when clicked.
 */
@SuppressLint("ViewConstructor")
class MainMenuTile (
    context: Context,
    private val commandResID: Int,
    private val imageResID: Int,
    private val launchClass: String
) : LinearLayout(context), View.OnClickListener {
    private val binding: CommandSquareBinding

    /**
     * Set the image displayed on the tile via [resID]
     */
    private fun setImage(resID: Int) {
        val drawable = ResourcesCompat.getDrawable(resources, resID, context.theme)
        binding.imageView.setImageDrawable(drawable)
    }

    /**
     * Set the text displayed on the tile via [resID]
     */
    private fun setSmallText(resID: Int) {
        binding.smallTextView.text = resources.getString(resID)
    }

    /**
     * Called when the [tile] is clicked
     */
    override fun onClick(tile: View) {
        val packageName = context.packageName
        val intent = Intent().apply {
            component = ComponentName(packageName, "$packageName.$launchClass")
        }
        context.startActivity(intent)
    }

    init {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CommandSquareBinding.inflate(layoutInflater)
        setImage(imageResID)
        setSmallText(commandResID)
        setOnClickListener(this)
    }
}