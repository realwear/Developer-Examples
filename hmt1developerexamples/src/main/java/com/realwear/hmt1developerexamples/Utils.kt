/**
 * RealWear Development Software, Source Code and Object Code
 * (c) RealWear, Inc. All rights reserved.
 *
 *
 * Contact info@realwear.com for further information about the use of this code.
 */
package com.realwear.hmt1developerexamples

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import kotlin.Throws

object Utils {
    /**
     * Copy a file from assets folder to external storage
     *
     * @param context           The calling context
     * @param filename          The file name of the asset to copy
     * @param destinationFolder The destination to copy the asset to
     * @return The file stored in external storage
     * @throws IOException if there is an error copying the file
     */
    @Throws(IOException::class)
    fun copyFromAssetsToExternal(
        context: Context,
        filename: String?,
        destinationFolder: String?
    ): File {
        val inputStream = context.assets.open(filename!!)
        val outputFile = File(context.getExternalFilesDir(destinationFolder), filename)
        val outputStream: OutputStream = FileOutputStream(outputFile)
        val buffer = ByteArray(1024)
        var read: Int
        while (inputStream.read(buffer).also { read = it } > 0) {
            outputStream.write(buffer, 0, read)
        }
        outputStream.flush()
        outputStream.close()
        inputStream.close()
        return outputFile
    }
}