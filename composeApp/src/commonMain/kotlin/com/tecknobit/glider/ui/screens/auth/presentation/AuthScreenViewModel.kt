package com.tecknobit.glider.ui.screens.auth.presentation

import androidx.compose.material3.SnackbarHostState
import com.tecknobit.equinoxcompose.viewmodels.EquinoxAuthViewModel
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.glider.localUser
import com.tecknobit.glider.requester
import kotlinx.serialization.json.JsonObject

class AuthScreenViewModel : EquinoxAuthViewModel(
    snackbarHostState = SnackbarHostState(),
    requester = requester,
    localUser = localUser
) {

    @RequiresSuperCall
    override fun launchApp(
        response: JsonObject,
        name: String,
        surname: String,
        language: String,
        vararg custom: Any?,
    ) {
        super.launchApp(response, name, surname, language, *custom)
    }

}