package com.tecknobit.glider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.tecknobit.glider.helpers.BiometrikAuthenticator
import com.tecknobit.glider.ui.theme.GliderTheme
import com.tecknobit.octocatkdu.OctocatKDUConfig
import com.tecknobit.octocatkdu.UpdaterDialog
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.app_name
import glider.composeapp.generated.resources.app_version
import org.jetbrains.compose.resources.stringResource
import java.util.Locale

/**
 * Method to check whether are available any updates for each platform and then launch the application
 * which the correct first screen to display
 *
 */
@Composable
actual fun CheckForUpdatesAndLaunch() {
    GliderTheme {
        var launchApp by remember { mutableStateOf(true) }
        UpdaterDialog(
            config = OctocatKDUConfig(
                locale = Locale.getDefault(),
                appName = stringResource(Res.string.app_name),
                currentVersion = stringResource(Res.string.app_version),
                onUpdateAvailable = { launchApp = false },
                dismissAction = { launchApp = true }
            )
        )
        if (launchApp) {
            BiometrikAuthenticator(
                onSuccess = {
                    startSession()
                }
            )
        }
    }
}

/**
 * Method to manage correctly the back navigation from the current screen
 *
 */
@Composable
@NonRestartableComposable
actual fun CloseApplicationOnNavBack() {
}

/**
 * Method to set locale language for the application
 *
 */
actual fun setUserLanguage() {
    Locale.setDefault(Locale.forLanguageTag(localUser.language))
}