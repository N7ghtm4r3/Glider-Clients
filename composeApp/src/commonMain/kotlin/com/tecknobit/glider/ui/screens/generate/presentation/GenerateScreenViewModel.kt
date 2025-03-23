package com.tecknobit.glider.ui.screens.generate.presentation

import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.SnackbarResult.Dismissed
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.tecknobit.equinoxcompose.utilities.copyOnClipboard
import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.glider.ui.screens.generate.components.QuantityPickerState
import com.tecknobit.glider.ui.shared.presentations.PasswordFormViewModel
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.copy
import glider.composeapp.generated.resources.password_generated
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import kotlin.random.Random

class GenerateScreenViewModel(
    passwordId: String?,
) : PasswordFormViewModel(
    passwordId = passwordId
) {

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
            // TODO: MAKE THE REQUEST THEN
            _performingPasswordOperation.emit(true)
            delay(Random.nextInt(3) * 1000L) // TODO: TO REMOVE
            resetForm(Random.nextLong().toString()) // TODO: TO PASS THE REAL ONE
            _performingPasswordOperation.emit(false)
        }
    }

    @RequiresSuperCall
    override fun resetForm(vararg extra: Any) {
        super.resetForm(*extra)
        includeNumbers.value = true
        includeUppercaseLetters.value = true
        includeSpecialCharacters.value = true
        quantityPickerState.reset()
        {
            copyOnClipboard(
                content = extra[0] as String
            )
        }
        super.showSnackbarMessage()
    }

    @FutureEquinoxApi
    @Deprecated(
        message = "TO USE THE BUILT-IN ONE",
        replaceWith = ReplaceWith("super.showSnackbarMessage()")
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