package com.tecknobit.glider

import androidx.compose.ui.window.ComposeUIViewController
import com.tecknobit.ametistaengine.AmetistaEngine
import com.tecknobit.equinoxcompose.session.setUpSession

/**
 * Method to start the of `Glider` iOs application
 */
fun MainViewController() {
    AmetistaEngine.intake()
    ComposeUIViewController {
        setUpSession(
            hasBeenDisconnectedAction = {
                localUser.clear()
                navigator.navigate(AUTH_SCREEN)
            }
        )
        App()
    }
}