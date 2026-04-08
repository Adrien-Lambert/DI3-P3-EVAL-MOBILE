package com.example.evalp3.common

import javazoom.jl.player.Player
import java.io.ByteArrayInputStream

actual class SoundManager {
    private var isReleased = false

    actual fun playSound(audioData: ByteArray) {
        if (isReleased) return

        Thread {
            try {
                val stream = ByteArrayInputStream(audioData)
                val player = Player(stream)
                player.play()
                player.close()
            } catch (_: Exception) {
                // Silently ignore audio errors
            }
        }.start()
    }

    actual fun release() {
        isReleased = true
    }
}
