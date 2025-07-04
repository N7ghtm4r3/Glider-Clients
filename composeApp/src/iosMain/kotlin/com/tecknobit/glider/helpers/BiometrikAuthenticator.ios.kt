@file:OptIn(ExperimentalForeignApi::class)

package com.tecknobit.glider.helpers

import androidx.compose.runtime.Composable
import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi
import kotlinx.cinterop.ExperimentalForeignApi
import platform.LocalAuthentication.LAContext
import platform.LocalAuthentication.LAPolicyDeviceOwnerAuthenticationWithBiometrics

private var alreadyAuthenticated = false

@Composable
@FutureEquinoxApi
@Deprecated(
    message = "Will be created a standalone library which contains this api, will be redesigned to be general purpose" +
            "or will be integrated as component in Equinox"
)
actual fun BiometrikAuthenticator(
    requestOnFirstOpenOnly: Boolean,
    onSuccess: () -> Unit,
    onFailure: () -> Unit,
) {
    if (requestOnFirstOpenOnly && alreadyAuthenticated)
        onSuccess()
    else {
        val context = LAContext()
        if (context.canEvaluatePolicy(LAPolicyDeviceOwnerAuthenticationWithBiometrics, null)) {
            context.evaluatePolicy(
                policy = LAPolicyDeviceOwnerAuthenticationWithBiometrics,
                localizedReason = ""
            ) { success, _ ->
                if (success) {
                    alreadyAuthenticated = true
                    onSuccess()
                } else
                    onFailure()
            }
        } else
            onSuccess()
    }
}