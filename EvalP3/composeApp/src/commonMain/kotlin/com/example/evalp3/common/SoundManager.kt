package com.example.evalp3.common

/**
 * Cross-platform sound manager using expect/actual.
 *
 * Each platform provides its own audio implementation:
 * - Android: uses [android.media.MediaPlayer] with a temporary file
 * - Desktop: uses JLayer MP3 decoder for playback
 *
 * Audio data is passed as a [ByteArray] loaded from Compose Resources,
 * making the sound file shared across platforms.
 */
expect class SoundManager {
    /** Plays the given audio data (MP3 format). */
    fun playSound(audioData: ByteArray)

    /** Releases audio resources. Should be called when no longer needed. */
    fun release()
}
