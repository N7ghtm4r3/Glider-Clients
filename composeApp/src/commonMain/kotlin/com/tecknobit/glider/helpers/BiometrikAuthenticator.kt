package com.tecknobit.glider.helpers

import androidx.compose.runtime.Composable
import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi

// TODO: TO USE THE OFFICIAL API INSTEAD
@Composable
@Deprecated(
    message = "Will be created a standalone library which contains this api, will be redesigned to be general purpose" +
            "or will be integrated as component in Equinox"
)
@FutureEquinoxApi
expect fun BiometrikAuthenticator(
    onSuccess: () -> Unit,
    onFailure: () -> Unit,
)
