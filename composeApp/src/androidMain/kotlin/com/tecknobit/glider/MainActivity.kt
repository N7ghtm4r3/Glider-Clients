package com.tecknobit.glider

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.StrictMode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import com.tecknobit.equinoxcompose.session.setUpSession

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        // TODO: TO SET
        // appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        setUpSession(
            hasBeenDisconnectedAction = {
                localUser.clear()
                navigator.navigate(AUTH_SCREEN)
            }
        )
    }

}
