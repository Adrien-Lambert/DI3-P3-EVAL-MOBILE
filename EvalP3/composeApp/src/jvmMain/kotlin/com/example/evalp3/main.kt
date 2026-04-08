package com.example.evalp3

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.evalp3.common.sharedModules
import com.example.evalp3.data.platformModule
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(sharedModules() + platformModule)
    }

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Rick and Morty Locations",
        ) {
            App()
        }
    }
}
