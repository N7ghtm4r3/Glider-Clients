package com.tecknobit.glider.ui.screens.generate.presentation

import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.tecknobit.equinoxcompose.components.quantitypicker.QuantityPickerState
import com.tecknobit.equinoxcompose.session.viewmodels.EquinoxViewModel
import com.tecknobit.equinoxcompose.utilities.copyOnClipboard
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.network.Requester.Companion.toResponseContent
import com.tecknobit.equinoxcore.network.sendRequestAsyncHandlers
import com.tecknobit.glider.helpers.KReviewer
import com.tecknobit.glider.requester
import com.tecknobit.glider.ui.shared.presentations.PasswordFormViewModel
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.copy
import glider.composeapp.generated.resources.password_generated
import kotlinx.coroutines.launch

/**
 * The `GenerateScreenViewModel` class is the support class used by the
 * [com.tecknobit.glider.ui.screens.generate.presenter.GenerateScreenTab] to generate a new password
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @see androidx.lifecycle.ViewModel
 * @see com.tecknobit.equinoxcompose.session.Retriever
 * @see EquinoxViewModel
 * @see PasswordFormViewModel
 *
 */
class GenerateScreenViewModel : PasswordFormViewModel() {

    /**
     * `quantityPickerState` state used to handle the length of the password to generate
     */
    lateinit var quantityPickerState: QuantityPickerState

    /**
     * `includeNumbers` whether the generated password must include the numbers
     */
    lateinit var includeNumbers: MutableState<Boolean>

    /**
     * `includeUppercaseLetters` whether the generated password must include the uppercase letters
     */
    lateinit var includeUppercaseLetters: MutableState<Boolean>

    /**
     * `includeSpecialCharacters` whether the generated password must include the special characters
     */
    lateinit var includeSpecialCharacters: MutableState<Boolean>

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
                    generatePassword(
                        length = quantityPickerState.quantityPicked,
                        tail = tail.value,
                        scopes = scopes.value,
                        includeNumbers = includeNumbers.value,
                        includeUppercaseLetters = includeUppercaseLetters.value,
                        includeSpecialCharacters = includeSpecialCharacters.value
                    )
                },
                onSuccess = {
                    _performingPasswordOperation.emit(false)
                    val kReviewer = KReviewer()
                    kReviewer.reviewInApp {
                        resetForm(it.toResponseContent())
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
     * Method to reset the form to the initial state to perform a new action
     *
     * @param extra Extra parameters to use to reset the form state
     */
    @RequiresSuperCall
    override fun resetForm(vararg extra: Any) {
        super.resetForm(*extra)
        includeNumbers.value = true
        includeUppercaseLetters.value = true
        includeSpecialCharacters.value = true
        quantityPickerState.reset()
        showSnackbarMessage(
            message = Res.string.password_generated,
            actionLabel = Res.string.copy,
            onActionPerformed = {
                copyOnClipboard(
                    content = extra[0] as String
                )
            }
        )
    }

}