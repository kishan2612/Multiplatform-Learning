package org.kmp.learning

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Multiplatform-Learning",
    ) {
        App()
    }
}