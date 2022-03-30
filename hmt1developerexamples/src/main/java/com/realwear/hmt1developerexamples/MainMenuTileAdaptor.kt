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
 * Adapter used to drive the tile view
 */
class MainMenuTileAdaptor
/**
 * Constructor
 *
 * @param _tileList The tile list to display
 */(private val _tileList: Array<View>) : BaseAdapter() {
    /**
     * Get the number of tiles in the adaptor
     *
     * @return The number of tiles
     */
    override fun getCount(): Int {
        return _tileList.size
    }

    /**
     * Get a tile from the data set
     *
     * @param position The index of the tile to get
     * @return The tile or null if not found
     */
    override fun getItem(position: Int): Any {
        return _tileList[position]
    }

    /**
     * Get the item ID for a tile. Always returns position.
     *
     * @param position The index of the tile to get the item ID for
     * @return Always returns position
     */
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * Get the tile view at the specified position in the data set
     *
     * @param position    The index of the tile view to get
     * @param convertView The old view to reuse, if possible
     * @param parent      The parent that this view will eventually be attached to
     * @return The tile view
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return _tileList[position]
    }

    /**
     * Get if a tile is clickable
     *
     * @param position The index of the tile
     * @return Always true
     */
    override fun isEnabled(position: Int): Boolean {
        return true
    }
}