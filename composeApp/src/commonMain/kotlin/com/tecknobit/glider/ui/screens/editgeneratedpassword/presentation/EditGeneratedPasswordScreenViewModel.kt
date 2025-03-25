package com.tecknobit.glider.ui.screens.editgeneratedpassword.presentation

import androidx.lifecycle.viewModelScope
import com.tecknobit.glider.navigator
import com.tecknobit.glider.ui.shared.presentations.EditPasswordFormViewModel
import kotlinx.coroutines.launch

class EditGeneratedPasswordScreenViewModel(
    passwordId: String,
) : EditPasswordFormViewModel(
    passwordId = passwordId
) {

    /**
     * Method to execute the operation related to the password such generating or inserting
     */
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

}