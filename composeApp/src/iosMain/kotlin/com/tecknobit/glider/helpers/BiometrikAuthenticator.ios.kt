@file:OptIn(ExperimentalForeignApi::class)

package com.tecknobit.glider.helpers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi
import kotlinx.cinterop.ExperimentalForeignApi
import platform.LocalAuthentication.LAContext
import platform.LocalAuthentication.LAPolicyDeviceOwnerAuthenticationWithBiometrics

@Composable
@FutureEquinoxApi
@Deprecated(
    message = "Will be created a standalone library which contains this api, will be redesigned to be general purpose" +
            "or will be integrated as component in Equinox"
)
actual fun BiometrikAuthenticator(
    onSuccess: () -> Unit,
    onFailure: () -> Unit,
) {
    val context = remember { LAContext() }
    if (context.canEvaluatePolicy(LAPolicyDeviceOwnerAuthenticationWithBiometrics, null)) {
        context.evaluatePolicy(LAPolicyDeviceOwnerAuthenticationWithBiometrics, "") { success, _ ->
            if (success)
                onSuccess()
            else
                onFailure()
        }
    } else
        onSuccess()
}