package com.tecknobit.glider

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.tecknobit.ametistaengine.AmetistaEngine
import com.tecknobit.equinoxcompose.session.setUpSession
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.app_name
import glider.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * Method to start the of `Glider` desktop app
 */
fun main() {
    AmetistaEngine.intake()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = stringResource(Res.string.app_name),
            icon = painterResource(Res.drawable.logo),
            state = WindowState(
                placement = WindowPlacement.Maximized
            )
        ) {
            setUpSession(
                hasBeenDisconnectedAction = {
                    localUser.clear()
                    navigator.navigate(AUTH_SCREEN)
                }
            )
            App()
        }
    }
}