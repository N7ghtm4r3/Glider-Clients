package com.tecknobit.glider.ui.screens.insert.presentation

import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.tecknobit.equinoxcompose.viewmodels.EquinoxViewModel
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isPasswordValid
import com.tecknobit.equinoxcore.network.sendRequestAsyncHandlers
import com.tecknobit.glider.helpers.KReviewer
import com.tecknobit.glider.requester
import com.tecknobit.glider.ui.shared.presentations.PasswordFormViewModel
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.password_inserted
import kotlinx.coroutines.launch

/**
 * The `InsertPasswordScreenViewModel` class is the support class used by the
 * [com.tecknobit.glider.ui.screens.insert.presenter.InsertPasswordScreenTab] to insert a new password
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @see androidx.lifecycle.ViewModel
 * @see com.tecknobit.equinoxcompose.session.Retriever
 * @see EquinoxViewModel
 * @see PasswordFormViewModel
 *
 */
class InsertPasswordScreenViewModel : PasswordFormViewModel() {

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
                    insertPassword(
                        tail = tail.value,
                        scopes = scopes.value,
                        password = passwordValue.value
                    )
                },
                onSuccess = {
                    _performingPasswordOperation.emit(false)
                    val kReviewer = KReviewer()
                    kReviewer.reviewInApp {
                        resetForm()
                    }
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

    /**
     * Method to reset the form to the initial state to perform a new action
     *
     * @param extra Extra parameters to use to reset the form state
     */
    @RequiresSuperCall
    override fun resetForm(
        vararg extra: Any,
    ) {
        super.resetForm(*extra)
        passwordValue.value = ""
        showSnackbarMessage(Res.string.password_inserted)
    }

}