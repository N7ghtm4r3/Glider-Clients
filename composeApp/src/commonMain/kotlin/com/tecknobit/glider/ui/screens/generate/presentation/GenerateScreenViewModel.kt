package com.tecknobit.glider.ui.screens.generate.presentation

import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.SnackbarResult.Dismissed
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.tecknobit.equinoxcompose.utilities.copyOnClipboard
import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.network.Requester.Companion.sendRequest
import com.tecknobit.equinoxcore.network.Requester.Companion.toResponseContent
import com.tecknobit.glider.requester
import com.tecknobit.glider.ui.screens.generate.components.QuantityPickerState
import com.tecknobit.glider.ui.shared.presentations.PasswordFormViewModel
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.copy
import glider.composeapp.generated.resources.password_generated
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString

class GenerateScreenViewModel : PasswordFormViewModel() {

    lateinit var quantityPickerState: QuantityPickerState

    lateinit var includeNumbers: MutableState<Boolean>

    lateinit var includeUppercaseLetters: MutableState<Boolean>

    lateinit var includeSpecialCharacters: MutableState<Boolean>

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
                    viewModelScope.launch {
                        _performingPasswordOperation.emit(false)
                        resetForm(it.toResponseContent())
                    }
                },
                onFailure = {
                    viewModelScope.launch {
                        _performingPasswordOperation.emit(false)
                    }
                    showSnackbarMessage(it)
                }
            )
        }
    }

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

    @FutureEquinoxApi
    @Deprecated(
        message = "TO USE THE BUILT-IN ONE"
    )
    fun showSnackbarMessage(
        message: StringResource,
        actionLabel: StringResource? = null,
        onDismiss: (() -> Unit)? = null,
        onActionPerformed: (() -> Unit)? = null,
    ) {
        viewModelScope.launch {
            snackbarHostState!!.showSnackbar(
                message = getString(
                    resource = message
                ),
                actionLabel = if (actionLabel != null) {
                    getString(
                        resource = actionLabel
                    )
                } else
                    null
            ).let {
                when (it) {
                    Dismissed -> onDismiss?.invoke()
                    ActionPerformed -> onActionPerformed?.invoke()
                }
            }
        }
    }

}