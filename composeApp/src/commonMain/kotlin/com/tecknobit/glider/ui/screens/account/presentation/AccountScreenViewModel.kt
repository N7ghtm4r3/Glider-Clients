package com.tecknobit.glider.ui.screens.account.presentation

import androidx.compose.material3.SnackbarHostState
import com.tecknobit.equinoxcompose.network.EquinoxRequester
import com.tecknobit.equinoxcompose.viewmodels.EquinoxProfileViewModel
import com.tecknobit.glider.localUser

class AccountScreenViewModel : EquinoxProfileViewModel(
    snackbarHostState = SnackbarHostState(),
    // TODO: TO SET
    requester = object : EquinoxRequester(
        host = "",
        connectionErrorMessage = "TODO()"
    ) {
        // TODO: TO SET
    },
    localUser = localUser
)