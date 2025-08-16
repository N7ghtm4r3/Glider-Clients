@file:OptIn(ExperimentalComposeApi::class)

package com.tecknobit.glider.ui.shared.presentations

import androidx.compose.runtime.ExperimentalComposeApi
import androidx.lifecycle.viewModelScope
import com.tecknobit.equinoxcompose.session.sessionflow.SessionFlowState
import com.tecknobit.equinoxcompose.session.viewmodels.EquinoxViewModel
import com.tecknobit.equinoxcore.annotations.Structure
import com.tecknobit.equinoxcore.network.Requester.Companion.toResponseData
import com.tecknobit.equinoxcore.network.sendRequest
import com.tecknobit.glider.requester
import com.tecknobit.glider.ui.shared.data.PasswordDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

/**
 * The `EditPasswordFormViewModel` class is the support class used by the screens which have to handle
 * the forms where the user can insert the new details about a password such tail, scopes and password
 * value to edit that password
 *
 * @property passwordId The identifier of the password
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @see androidx.lifecycle.ViewModel
 * @see com.tecknobit.equinoxcompose.session.Retriever
 * @see EquinoxViewModel
 * @see PasswordFormViewModel
 *
 */
@Structure
abstract class EditPasswordFormViewModel(
    protected val passwordId: String,
) : PasswordFormViewModel() {

    /**
     * `_password` the password to edit
     */
    protected val _password = MutableStateFlow<PasswordDetails?>(
        value = null
    )
    val password = _password.asStateFlow()

    /**
     * `sessionFlowState` the state used to manage the session lifecycle in the screen
     */
    lateinit var sessionFlowState: SessionFlowState

    /**
     * Method to request the current password details
     */
    fun retrievePassword() {
        viewModelScope.launch {
            requester.sendRequest(
                request = {
                    getPassword(
                        passwordId = passwordId
                    )
                },
                onSuccess = {
                    sessionFlowState.notifyOperational()
                    _password.value = Json.decodeFromJsonElement(it.toResponseData())
                },
                onFailure = { showSnackbarMessage(it) },
                onConnectionError = { sessionFlowState.notifyServerOffline() }
            )
        }
    }

}