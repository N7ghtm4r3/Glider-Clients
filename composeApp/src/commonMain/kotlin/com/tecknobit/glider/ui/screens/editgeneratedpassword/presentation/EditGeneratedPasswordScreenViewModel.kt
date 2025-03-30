package com.tecknobit.glider.ui.screens.editgeneratedpassword.presentation

import androidx.lifecycle.viewModelScope
import com.tecknobit.equinoxcompose.viewmodels.EquinoxViewModel
import com.tecknobit.equinoxcore.network.Requester.Companion.sendRequest
import com.tecknobit.glider.navigator
import com.tecknobit.glider.requester
import com.tecknobit.glider.ui.shared.presentations.EditPasswordFormViewModel
import com.tecknobit.glider.ui.shared.presentations.PasswordFormViewModel
import kotlinx.coroutines.launch

/**
 * The `EditGeneratedPasswordScreenViewModel` class is the support class used by the
 * [com.tecknobit.glider.ui.screens.editgeneratedpassword.presenter.EditGeneratedPasswordScreen]
 *
 * @property passwordId The identifier of the password
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @see androidx.lifecycle.ViewModel
 * @see com.tecknobit.equinoxcompose.session.Retriever
 * @see EquinoxViewModel
 * @see PasswordFormViewModel
 * @see EditGeneratedPasswordScreenViewModel
 *
 */
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
            requester.sendRequest(
                request = {
                    editPassword(
                        passwordId = passwordId,
                        tail = tail.value,
                        scopes = scopes.value
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

}