package com.tecknobit.glider.helpers

import androidx.compose.runtime.Composable
import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi

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
    // TODO: NOW WILL BE SKIPPED, BUT WILL BE INTEGRATED A WAY TO CORRECTLY BIO-AUTH
    onSuccess()
}