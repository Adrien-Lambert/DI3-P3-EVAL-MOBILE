package com.example.evalp3

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Rick and Morty Locations",
    ) {
        App()
    }
}
