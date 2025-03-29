package com.tecknobit.glider.ui.screens.insert.presentation

import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isPasswordValid
import com.tecknobit.equinoxcore.network.Requester.Companion.sendRequest
import com.tecknobit.glider.helpers.KReviewer
import com.tecknobit.glider.requester
import com.tecknobit.glider.ui.shared.presentations.PasswordFormViewModel
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.password_inserted
import kotlinx.coroutines.launch

class InsertPasswordScreenViewModel : PasswordFormViewModel() {

    lateinit var passwordValue: MutableState<String>

    lateinit var passwordError: MutableState<Boolean>

    /**
     * Method to execute the operation related to the password such generating or inserting
     */
    override fun performPasswordOperation() {
        if (!validateForm())
            return
        viewModelScope.launch {
            _performingPasswordOperation.emit(true)
            requester.sendRequest(
                request = {
                    insertPassword(
                        tail = tail.value,
                        scopes = scopes.value,
                        password = passwordValue.value
                    )
                },
                onSuccess = {
                    viewModelScope.launch {
                        _performingPasswordOperation.emit(false)
                        val kReviewer = KReviewer()
                        kReviewer.reviewInApp {
                            resetForm()
                        }
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

    @RequiresSuperCall
    override fun resetForm(
        vararg extra: Any,
    ) {
        super.resetForm(*extra)
        passwordValue.value = ""
        showSnackbarMessage(Res.string.password_inserted)
    }

}