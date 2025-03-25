package com.tecknobit.glider.ui.screens.editinsertedpassword.presentation

import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isPasswordValid
import com.tecknobit.glider.navigator
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
            // TODO: MAKE THE REQUEST THEN
            _performingPasswordOperation.emit(false)
            navigator.goBack()
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