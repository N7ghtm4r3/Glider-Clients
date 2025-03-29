package com.tecknobit.glider.ui.screens.editinsertedpassword.presentation

import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isPasswordValid
import com.tecknobit.equinoxcore.network.Requester.Companion.sendRequest
import com.tecknobit.glider.navigator
import com.tecknobit.glider.requester
import com.tecknobit.glider.ui.shared.presentations.EditPasswordFormViewModel
import kotlinx.coroutines.launch

class EditInsertedPasswordScreenViewModel(
    passwordId: String,
) : EditPasswordFormViewModel(
    passwordId = passwordId
) {

    lateinit var passwordValue: MutableState<String>

    lateinit var passwordError: MutableState<Boolean>

    override fun performPasswordOperation() {
        if (!validateForm())
            return
        viewModelScope.launch {
            _performingPasswordOperation.emit(true)
            requester.sendRequest(
                request = {
                    editPassword(
                        passwordId = passwordId,
                        tail = tail.value,
                        scopes = scopes.value,
                        password = passwordValue.value
                    )
                },
                onSuccess = {
                    viewModelScope.launch {
                        _performingPasswordOperation.emit(false)
                        navigator.goBack()
                    }
                },
                onFailure = {
                    viewModelScope.launch {
                        _performingPasswordOperation.emit(false)
                        showSnackbarMessage(it)
                    }
                }
            )
        }
    }

    @RequiresSuperCall
    override fun validateForm(): Boolean {
        val validity = super.validateForm()
        if (!validity)
            return false
        if (!isPasswordValid(passwordValue.value)) {
            passwordError.value = true
            return false
        }
        return true
    }

}