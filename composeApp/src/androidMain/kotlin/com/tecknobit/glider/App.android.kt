package com.tecknobit.glider

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import com.tecknobit.equinoxcore.utilities.AppContext
import moe.tlaster.precompose.navigation.BackHandler
import java.util.Locale

/**
 * Method to check whether are available any updates for each platform and then launch the application
 * which the correct first screen to display
 *
 */
@Composable
@NonRestartableComposable
actual fun CheckForUpdatesAndLaunch() {
    // TODO: TO SET
    /*appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
        val isUpdateAvailable = info.updateAvailability() == UPDATE_AVAILABLE
        val isUpdateSupported = info.isImmediateUpdateAllowed
        if (isUpdateAvailable && isUpdateSupported) {
            appUpdateManager.startUpdateFlowForResult(
                info,
                launcher,
                AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
            )
        } else
            startSession()
    }.addOnFailureListener {
        startSession()
    }*/
    startSession()
}

/**
 * Method to manage correctly the back navigation from the current screen
 *
 */
@NonRestartableComposable
@Composable
actual fun CloseApplicationOnNavBack() {
    val activity = LocalActivity.current
    BackHandler {
        activity?.finishAffinity()
    }
}

/**
 * Method to set locale language for the application
 *
 */
actual fun setUserLanguage() {
    val locale = Locale(localUser.language)
    Locale.setDefault(locale)
    val context = AppContext.get()
    val config = context.resources.configuration
    config.setLocale(locale)
    context.createConfigurationContext(config)
}