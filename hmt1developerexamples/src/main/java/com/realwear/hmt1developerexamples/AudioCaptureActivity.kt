/*
 * RealWear Development Software, Source Code and Object Code.
 * (c) RealWear, Inc. All rights reserved.
 *
 * Contact info@realwear.com for further information about the use of this code.
 */
package com.realwear.hmt1developerexamples

import android.Manifest
import android.app.Activity
import android.os.CountDownTimer
import android.os.Bundle
import android.view.WindowManager
import com.realwear.hmt1developerexamples.R
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import android.media.*
import androidx.core.app.ActivityCompat
import com.realwear.hmt1developerexamples.AudioCaptureActivity
import android.os.Environment
import kotlin.Throws
import android.util.Log
import android.view.View
import android.widget.*
import java.io.*
import java.lang.Exception
import java.util.concurrent.TimeUnit

/**
 * Activity that shows how capture audio at different frequencies on a HMT-1 device.
 */
class AudioCaptureActivity : Activity(), Runnable {
    private val _recordButton: Button       by lazy { findViewById(R.id.recordButton) }
    private val _playbackButton: Button     by lazy { findViewById(R.id.playbackButton) }
    private val _progressBar: ProgressBar   by lazy { findViewById(R.id.progressBar) }
    private val _filenameLabel: TextView    by lazy { findViewById(R.id.filenameLabel) }
    private val _monoButton: RadioButton    by lazy { findViewById(R.id.monoButton) }
    private val _stereoButton: RadioButton  by lazy { findViewById(R.id.stereoButton) }
    private val _rate8Button: RadioButton   by lazy { findViewById(R.id.rate8Button) }
    private val _rate16Button: RadioButton  by lazy { findViewById(R.id.rate16Button) }
    private val _rate44Button: RadioButton  by lazy { findViewById(R.id.rate44Button) }
    private val _rate48Button: RadioButton  by lazy { findViewById(R.id.rate48Button) }

    private var _playbackAudioTimer: CountDownTimer? = null
    private var _sampleRate = 0
    private var _sampleRatedString: String? = null
    private var _channels: Short = 0
    private var _channelsString: String? = null
    private var _audioRecorder: AudioRecord? = null
    private var _motor: Thread? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.audio_capture_main)

        // Check that permissions are granted before continuing
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.RECORD_AUDIO), 0
            )
        }
    }

    override fun onResume() {
        super.onResume()
        _recordButton.isEnabled = true
        _playbackButton.isEnabled = true
        _progressBar.visibility = View.INVISIBLE
        _filenameLabel.text = ""
        setButtonState(true)
        _rate44Button.performClick()
        _stereoButton.performClick()
    }

    override fun onPause() {
        super.onPause()
        if (_motor != null) {
            _audioRecorder?.stop()
            _audioRecorder?.release()
            _audioRecorder = null
            try {
                _motor?.join()
            } catch (ex: Exception) {
                Log.e(TAG, "Failed to stop thread", ex)
            }
        }
        _motor = null
        if (_playbackAudioTimer != null) {
            _playbackAudioTimer?.cancel()
        }
    }

    /**
     * Called when the start record button is pressed
     *
     * @param view The start record button
     */
    fun onStartRecord(view: View?) {
        val monoOrStereo =
            if (_channels.toInt() == 1) AudioFormat.CHANNEL_IN_MONO else AudioFormat.CHANNEL_IN_STEREO
        _audioRecorder = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            _sampleRate,
            monoOrStereo,
            AudioFormat.ENCODING_PCM_16BIT,
            4096
        )
        if (_audioRecorder?.state == AudioRecord.STATE_UNINITIALIZED) {
            Toast.makeText(this, "Failed to initialize AudioRecorder", Toast.LENGTH_LONG).show()
            return
        }
        _progressBar.progress = 0
        _progressBar.visibility = View.VISIBLE
        setButtonState(false)
        _audioRecorder?.startRecording()
        _motor = Thread(this)
        _motor?.name = "Recording Thread"
        _motor?.priority = Thread.MIN_PRIORITY
        _motor?.start()
    }

    /**
     * Called when the start playback button is pressed
     *
     * @param view The start playback button
     */
    fun onStartPlayback(view: View?) {
        val recordingFile = recordingFile
        try {
            val mp = MediaPlayer()
            mp.setDataSource(recordingFile.absolutePath)
            mp.prepare()
            mp.start()
            setButtonState(false)
            _progressBar.progress = 0
            _progressBar.visibility = View.VISIBLE
            _playbackAudioTimer =
                object : CountDownTimer(mp.duration.toLong(), TimeUnit.SECONDS.toMillis(1)) {
                    override fun onTick(millisUntilFinished: Long) {
                        val duration = mp.duration.toFloat()
                        val msPlayed = duration - millisUntilFinished.toInt()
                        val progress = msPlayed / duration * _progressBar.max
                        _progressBar.progress = progress.toInt()
                    }

                    override fun onFinish() {
                        setButtonState(true)
                        _progressBar.visibility = View.INVISIBLE
                    }
                }.start()
        } catch (ex: IOException) {
            Toast.makeText(this, "Failed to play back file", Toast.LENGTH_LONG).show()
            Log.e(TAG, "Playback error: ", ex)
            ex.printStackTrace()
        }
    }

    /**
     * Thread used to record audio and update the progress bar
     */
    override fun run() {
        val bitsPerSecond =
            if (_audioRecorder?.audioFormat == AudioFormat.ENCODING_PCM_16BIT) 16 else 8
        val bos = ByteArrayOutputStream()
        val buffer = ByteArray(4096 * 2)
        val startTime = System.currentTimeMillis()
        var seconds = 0f
        while (seconds < SAMPLE_LENGTH_SECONDS && _audioRecorder != null && _audioRecorder?.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
            val bytesRead = _audioRecorder?.read(buffer, 0, buffer.size)
            if (bytesRead!! > 0) {
                bos.write(buffer, 0, bytesRead)
            }
            seconds =
                TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTime).toFloat()
            updateProgressBar(seconds)
        }
        if (_audioRecorder != null) {
            _audioRecorder?.stop()
            _audioRecorder?.release()
            _audioRecorder = null
            writeWAVFile(recordingFile, bos, bitsPerSecond)
        }
        runOnUiThread {
            _progressBar.visibility = View.INVISIBLE
            setButtonState(true)
        }
    }

    /**
     * Update the progress bar based on the number of seconds that have been recorded
     *
     * @param seconds The number of seconds recorded
     */
    private fun updateProgressBar(seconds: Float) {
        runOnUiThread {
            val progress = seconds / SAMPLE_LENGTH_SECONDS * _progressBar.max
            _progressBar.progress = progress.toInt()
        }
    }

    /**
     * Called when the user changes the number of channels
     *
     * @param view The selected number of channels
     */
    fun onChannelsChanged(view: View) {
        if (view == _monoButton) {
            _channels = 1
            _channelsString = "mono"
        }
        if (view == _stereoButton) {
            _channels = 2
            _channelsString = "stereo"
        }
        updateFileName()
    }

    /**
     * Called when the user changes the sample rate
     *
     * @param view The selected sample rate
     */
    fun onSampleRateChanged(view: View) {
        if (view == _rate8Button) {
            _sampleRate = 8000
            _sampleRatedString = "8KHz"
        }
        if (view == _rate16Button) {
            _sampleRate = 16000
            _sampleRatedString = "16KHz"
        }
        if (view == _rate44Button) {
            _sampleRate = 44100
            _sampleRatedString = "44KHz"
        }
        if (view == _rate48Button) {
            _sampleRate = 48000
            _sampleRatedString = "48KHz"
        }
        updateFileName()
    }

    /**
     * Updated the filename based on the selected settings
     */
    private fun updateFileName() {
        _filenameLabel?.text = recordingFile.toString()
    }

    /**
     * Get the file that for recording and playing back an audio file based on the current settings.
     *
     * @return The file that should be used.
     */
    private val recordingFile: File
        private get() {
            val filename = "audio_test_" + _sampleRatedString + "_" + _channelsString + ".wav"
            val mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC)
            return File(mediaStorageDir, filename)
        }

    /**
     * Enable or disable the on screen options
     *
     * @param isEnabled true to enable the options, false to disable them
     */
    private fun setButtonState(isEnabled: Boolean) {
        _rate8Button?.isEnabled = isEnabled
        _rate16Button?.isEnabled = isEnabled
        _rate44Button?.isEnabled = isEnabled
        _rate48Button?.isEnabled = isEnabled
        _monoButton?.isEnabled = isEnabled
        _stereoButton?.isEnabled = isEnabled
        _playbackButton?.isEnabled = isEnabled
        _recordButton?.isEnabled = isEnabled
    }

    /**
     * Write the recorded audio data to a wav file.
     *
     * @param file         The file to write audio data to.
     * @param outputStream The recorded audio data.
     */
    private fun writeWAVFile(file: File, outputStream: ByteArrayOutputStream, bitsPerSamples: Int) {
        val audioData = outputStream.toByteArray()
        try {
            val fos = FileOutputStream(file)
            val dos = DataOutputStream(fos)
            writeWaveFileHeader(dos, audioData.size, bitsPerSamples)
            fos.write(audioData)
            fos.flush()
            fos.close()
        } catch (ex: FileNotFoundException) {
            Toast.makeText(this, "Error creating wav file", Toast.LENGTH_LONG).show()
            Log.e(TAG, "Error creating wav file: ", ex)
        } catch (ex: IOException) {
            Toast.makeText(this, "Error writing wav file", Toast.LENGTH_LONG).show()
            Log.e(TAG, "Error writing wav file: ", ex)
        }
        rescanFolder(file.parent)
    }

    /**
     * Write wav file header information to a file
     *
     * @param dos           The stream to write the header information to
     * @param audioLen      The number of bytes of audio data that will be written
     * @param bitsPerSample The number of bits per sample
     * @throws IOException if an error occurs while writing to the output stream
     */
    @Throws(IOException::class)
    private fun writeWaveFileHeader(dos: DataOutputStream, audioLen: Int, bitsPerSample: Int) {
        val wavHeaderSize = 44
        val totalDataLen = audioLen + wavHeaderSize - 8 /* Ignore chunk marker and size */
        val waveFormatPcm: Short = 0x1
        val byteSize = 8

        //
        // Riff chunk marker and size
        //
        dos.writeBytes("RIFF")
        dos.write(intToByteArray(totalDataLen))
        dos.writeBytes("WAVE")

        //
        // Format chunk marker and size
        //
        dos.writeBytes("fmt ")
        dos.write(intToByteArray(16)) // WAVE file type has 16 bits of format data

        //
        // Format data (16 bits)
        //
        dos.write(shortToByteArray(waveFormatPcm))
        dos.write(shortToByteArray(_channels))
        dos.write(intToByteArray(_sampleRate))
        val blockAlign = _channels * bitsPerSample / byteSize
        val avgBytesPerSec = _sampleRate * blockAlign
        dos.write(intToByteArray(avgBytesPerSec))
        dos.write(shortToByteArray(blockAlign.toShort()))
        dos.write(shortToByteArray(bitsPerSample.toShort()))

        //
        // Data chunk marker and size
        //
        dos.writeBytes("data")
        dos.write(intToByteArray(audioLen))
    }

    /**
     * Scan through the output folder to ensure Android finds the new files
     *
     * @param dest The output folder path
     */
    private fun rescanFolder(dest: String) {
        var files = File(dest).listFiles { pathname -> // Scan files only (not folders)
            pathname.isFile
        }
        val paths = arrayOfNulls<String>(files.size)
        for (co in files.indices) {
            paths[co] = files[co].absolutePath
        }
        MediaScannerConnection.scanFile(this, paths, null, null)
        files = File(dest).listFiles { pathname -> // and now recursively scan subfolders
            pathname.isDirectory
        }
        for (file in files) {
            rescanFolder(file.absolutePath)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (grantResult in grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                setResult(-1)
                Toast.makeText(this, R.string.permissions_error, Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    companion object {
        private const val TAG = "HMT1DevApp-Audio"
        private const val SAMPLE_LENGTH_SECONDS = 10

        /**
         * Convert an int to a byte array
         *
         * @param data The int to convert
         * @return Byte array containing the int data
         */
        private fun intToByteArray(data: Int): ByteArray {
            return byteArrayOf(
                (data and 0xff).toByte(),
                (data shr 8 and 0xff).toByte(),
                (data shr 16 and 0xff).toByte(),
                (data shr 24 and 0xff).toByte()
            )
        }

        /**
         * Convert a short to a byte array
         *
         * @param data the short to convert
         * @return Byte array containing the short data
         */
        private fun shortToByteArray(data: Short): ByteArray {
            return data.toInt().let {
                byteArrayOf(
                    (it and 0xff).toByte(),
                    (it shr 8 and 0xff).toByte())
            }
        }
    }
}