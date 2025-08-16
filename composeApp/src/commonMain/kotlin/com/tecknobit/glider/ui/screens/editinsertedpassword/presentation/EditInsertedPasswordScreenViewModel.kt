package com.tecknobit.glider.ui.screens.editinsertedpassword.presentation

import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.tecknobit.equinoxcompose.session.viewmodels.EquinoxViewModel
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isPasswordValid
import com.tecknobit.equinoxcore.network.sendRequestAsyncHandlers
import com.tecknobit.glider.navigator
import com.tecknobit.glider.requester
import com.tecknobit.glider.ui.screens.editgeneratedpassword.presentation.EditGeneratedPasswordScreenViewModel
import com.tecknobit.glider.ui.shared.presentations.EditPasswordFormViewModel
import com.tecknobit.glider.ui.shared.presentations.PasswordFormViewModel
import kotlinx.coroutines.launch

/**
 * The `EditInsertedPasswordScreenViewModel` class is the support class used by the
 * [com.tecknobit.glider.ui.screens.editinsertedpassword.presenter.EditInsertedPasswordScreen]
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
class EditInsertedPasswordScreenViewModel(
    passwordId: String,
) : EditPasswordFormViewModel(
    passwordId = passwordId
) {

    /**
     * `passwordValue` the value of the password
     */
    lateinit var passwordValue: MutableState<String>

    /**
     * `passwordError` whether the [passwordValue] field is not valid
     */
    lateinit var passwordError: MutableState<Boolean>

    /**
     * Method to execute the operation related to the password such generating or inserting
     */
    override fun performPasswordOperation() {
        if (!validateForm())
            return
        viewModelScope.launch {
            _performingPasswordOperation.emit(true)
            requester.sendRequestAsyncHandlers(
                request = {
                    editPassword(
                        passwordId = passwordId,
                        tail = tail.value,
                        scopes = scopes.value,
                        password = passwordValue.value
                    )
                },
                onSuccess = {
                    _performingPasswordOperation.emit(false)
                    navigator.popBackStack()
                },
                onFailure = {
                    _performingPasswordOperation.emit(false)
                    showSnackbarMessage(it)
                }
            )
        }
    }

    /**
     * Method to validate the form values
     *
     * @return whether the form is valid as [Boolean]
     */
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