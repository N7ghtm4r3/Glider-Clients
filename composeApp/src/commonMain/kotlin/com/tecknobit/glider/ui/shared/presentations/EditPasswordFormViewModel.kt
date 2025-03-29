package com.tecknobit.glider.ui.shared.presentations

import androidx.lifecycle.viewModelScope
import com.tecknobit.equinoxcore.annotations.Structure
import com.tecknobit.equinoxcore.network.Requester.Companion.sendRequest
import com.tecknobit.equinoxcore.network.Requester.Companion.toResponseData
import com.tecknobit.glider.requester
import com.tecknobit.glider.ui.shared.data.PasswordDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

@Structure
abstract class EditPasswordFormViewModel(
    protected val passwordId: String,
) : PasswordFormViewModel() {

    protected val _password = MutableStateFlow<PasswordDetails?>(
        value = null
    )
    val password = _password.asStateFlow()

    fun retrievePassword() {
        viewModelScope.launch {
            requester.sendRequest(
                request = {
                    getPassword(
                        passwordId = passwordId
                    )
                },
                onSuccess = {
                    _password.value = Json.decodeFromJsonElement(it.toResponseData())
                },
                onFailure = { showSnackbarMessage(it) }
            )
        }
    }

}