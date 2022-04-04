/**
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 *
 *
 * Contact info@realwear.com for further information about the use of this code.
 */
package com.realwear.hmt1developerexamples

import android.view.View
import android.widget.BaseAdapter
import android.view.ViewGroup

/**
 * Adapter used to drive the tile view with the [tileList] passed in.
 */
class MainMenuTileAdaptor(private val tileList: Array<View>) : BaseAdapter() {
    /**
     * Get the number of tiles in the adaptor
     */
    override fun getCount(): Int {
        return tileList.size
    }

    /**
     * Get a tile from the tile list at [position].
     */
    override fun getItem(position: Int): Any {
        return tileList[position]
    }

    /**
     * Get the item ID for a tile. Always returns [position].
     */
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * Get the tile view at the specified [position] in the data set, passing in a [convertView] to
     * reuse if possible and the [parent] ViewGroup to be attached to.
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return tileList[position]
    }

    /**
     * Get if a tile is clickable at [position]. Always return true.
     */
    override fun isEnabled(position: Int): Boolean {
        return true
    }
}