/**
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 * <p>
 * Contact info@realwear.com for further information about the use of this code.
 */

package com.realwear.hmt1developerexamples;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Adapter used to drive the tile view
 */
public class MainMenuTileAdaptor extends BaseAdapter {
    private View mTileList[];

    /**
     * Constructor
     *
     * @param tileList The tile list to display
     */
    public MainMenuTileAdaptor(View[] tileList) {
        mTileList = tileList;
    }

    /**
     * Get the number of tiles in the adaptor
     *
     * @return The number of tiles
     */
    @Override
    public int getCount() {
        if (mTileList == null) {
            return 0;
        }

        return mTileList.length;
    }


    /**
     * Get a tile from the data set
     *
     * @param position The index of the tile to get
     * @return The tile or null if not found
     */
    @Override
    public Object getItem(int position) {
        if (mTileList == null || position < 0 || position >= mTileList.length) {
            return null;
        }

        return mTileList[position];
    }

    /**
     * Get the item ID for a tile. Always returns position.
     *
     * @param position The index of the tile to get the item ID for
     * @return Always returns position
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get the tile view at the specified position in the data set
     *
     * @param position    The index of the tile view to get
     * @param convertView The old view to reuse, if possible
     * @param parent      The parent that this view will eventually be attached to
     * @return The tile view
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        return mTileList[position];
    }

    /**
     * Get if a tile is clickable
     *
     * @param position The index of the tile
     * @return Always true
     */
    public boolean isEnabled(int position) {
        return true;
    }
}
