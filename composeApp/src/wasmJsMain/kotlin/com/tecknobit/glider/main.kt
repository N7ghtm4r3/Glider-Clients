package com.tecknobit.glider

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.tecknobit.ametistaengine.AmetistaEngine
import com.tecknobit.equinoxcompose.session.setUpSession
import kotlinx.browser.document

/**
 * Method to start the of `Glider` webapp
 */
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    AmetistaEngine.intake()
    ComposeViewport(document.body!!) {
        setUpSession(
            hasBeenDisconnectedAction = {
                localUser.clear()
                navigator.navigate(AUTH_SCREEN)
            }
        )
        App()
    }
}