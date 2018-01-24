/**
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 * <p>
 * Contact info@realwear.com for further information about the use of this code.
 */

package com.realwear.hmt1developerexamples;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Utils {
    /**
     * Copy a file from assets folder to external storage
     *
     * @param context           The calling context
     * @param filename          The file name of the asset to copy
     * @param destinationFolder The destination to copy the asset to
     * @return The file stored in external storage
     * @throws IOException if there is an error copying the file
     */
    public static File copyFromAssetsToExternal(Context context, String filename, String destinationFolder)
            throws IOException {

        InputStream inputStream = context.getAssets().open(filename);

        File outputFile = new File(context.getExternalFilesDir(destinationFolder), filename);
        OutputStream outputStream = new FileOutputStream(outputFile);

        byte[] buffer = new byte[1024];
        int read;
        while ((read = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, read);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();

        return outputFile;
    }
}
