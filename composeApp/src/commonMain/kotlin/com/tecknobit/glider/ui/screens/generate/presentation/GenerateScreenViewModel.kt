package com.tecknobit.glider.ui.screens.generate.presentation

import androidx.lifecycle.viewModelScope
import com.tecknobit.glider.ui.shared.presentaions.PasswordFormViewModel
import kotlinx.coroutines.launch

class GenerateScreenViewModel(
    passwordId: String?,
) : PasswordFormViewModel(
    passwordId = passwordId
) {


    fun generatePassword() {
        if (!validateForm())
            return
        viewModelScope.launch {
            // TODO: MAKE THE REQUEST THEN
        }
    }

}