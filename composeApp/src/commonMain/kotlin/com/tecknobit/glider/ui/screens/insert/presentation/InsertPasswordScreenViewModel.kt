package com.tecknobit.glider.ui.screens.insert.presentation

import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isPasswordValid
import com.tecknobit.glider.ui.shared.presentations.PasswordFormViewModel
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.password_inserted
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InsertPasswordScreenViewModel(
    passwordId: String?,
) : PasswordFormViewModel(
    passwordId = passwordId,
) {

    lateinit var password: MutableState<String>

    lateinit var passwordError: MutableState<Boolean>

    /**
     * Method to execute the operation related to the password such generating or inserting
     */
    override fun performPasswordOperation() {
        if (!validateForm())
            return
        viewModelScope.launch {
            _performingPasswordOperation.emit(true)
            // TODO: MAKE THE REQUEST THEN
            delay(1000) // TODO: TO REMOVE
            resetForm()
            _performingPasswordOperation.emit(false)
            showSnackbarMessage(Res.string.password_inserted)
        }
    }

    @RequiresSuperCall
    override fun validateForm(): Boolean {
        val validity = super.validateForm()
        if (!validity)
            return false
        if (!isPasswordValid(password.value)) {
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
        password.value = ""
    }

}