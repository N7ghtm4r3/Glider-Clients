package com.tecknobit.glider.ui.screens.auth.presentation

import androidx.compose.material3.SnackbarHostState
import com.tecknobit.equinoxcompose.network.EquinoxRequester
import com.tecknobit.equinoxcompose.viewmodels.EquinoxAuthViewModel
import com.tecknobit.glider.localUser

class AuthScreenViewModel : EquinoxAuthViewModel(
    snackbarHostState = SnackbarHostState(),
    // TODO: TO SET
    requester = object : EquinoxRequester(
        host = "",
        connectionErrorMessage = "TODO()"
    ) {
        // TODO: TO SET
    },
    localUser = localUser
) {

}