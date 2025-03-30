package com.tecknobit.glider

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.StrictMode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.annotation.ContentView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.tecknobit.ametistaengine.AmetistaEngine
import com.tecknobit.equinoxcompose.session.setUpSession
import com.tecknobit.equinoxcore.utilities.ContextActivityProvider

/**
 * The [MainActivity] is used as entry point of Glider's application for Android
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see ComponentActivity
 *
 */
class MainActivity : ComponentActivity() {

    companion object {

        /**
         * `appUpdateManager` the manager to check if there is an update available
         */
        lateinit var appUpdateManager: AppUpdateManager

        /**
         * `launcher` the result registered for [appUpdateManager] and the action to execute if fails
         */
        lateinit var launcher: ActivityResultLauncher<IntentSenderRequest>

    }

    init {
        launcher = registerForActivityResult(StartIntentSenderForResult()) { result ->
            if (result.resultCode != RESULT_OK)
                startSession()
        }
    }

    /**
     * {@inheritDoc}
     *
     * If your ComponentActivity is annotated with [ContentView], this will
     * call [setContentView] for you.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AmetistaEngine.intake()
        setContent {
            enableEdgeToEdge()
            initSession()
            App()
        }
    }

    /**
     * Method to init the instances for the session
     *
     * No-any params required
     */
    @Composable
    @NonRestartableComposable
    @SuppressLint("ComposableNaming")
    private fun initSession() {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        ContextActivityProvider.setCurrentActivity(this)
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        setUpSession(
            hasBeenDisconnectedAction = {
                localUser.clear()
                navigator.navigate(AUTH_SCREEN)
            }
        )
    }

}
