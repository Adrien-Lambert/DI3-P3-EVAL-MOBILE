package com.example.evalp3.common

import android.media.MediaPlayer
import java.io.File

actual class SoundManager {
    private var mediaPlayer: MediaPlayer? = null

    actual fun playSound(audioData: ByteArray) {
        release()
        try {
            val tempFile = File.createTempFile("portal_sound", ".mp3")
            tempFile.deleteOnExit()
            tempFile.writeBytes(audioData)

            mediaPlayer = MediaPlayer().apply {
                setDataSource(tempFile.absolutePath)
                prepare()
                start()
                setOnCompletionListener {
                    it.release()
                    tempFile.delete()
                }
            }
        } catch (_: Exception) {
            // Silently ignore audio errors
        }
    }

    actual fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
