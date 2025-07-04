package com.tecknobit.glider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import com.tecknobit.glider.helpers.BiometrikAuthenticator
import kotlinx.browser.document

/**
 * Method to check whether are available any updates for each platform and then launch the application
 * which the correct first screen to display
 *
 */
@Composable
@NonRestartableComposable
actual fun CheckForUpdatesAndLaunch() {
    BiometrikAuthenticator(
        onSuccess = {
            startSession()
        }
    )
}

/**
 * Method to set locale language for the application
 */
actual fun setUserLanguage() {
    document.documentElement?.setAttribute("lang", localUser.language)
}

/**
 * Method to manage correctly the back navigation from the current screen
 *
 */
@Composable
@NonRestartableComposable
actual fun CloseApplicationOnNavBack() {

}