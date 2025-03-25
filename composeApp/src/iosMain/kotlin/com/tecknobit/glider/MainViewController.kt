package com.tecknobit.glider

import androidx.compose.ui.window.ComposeUIViewController
import com.tecknobit.equinoxcompose.session.setUpSession

fun MainViewController() {
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