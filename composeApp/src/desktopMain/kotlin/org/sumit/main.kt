package org.sumit

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.sumit.di.initKoin
import org.sumit.di.initKoinJvm

val koin = initKoinJvm()

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "RecipeApp",
    ) {
        App()
    }
}