package com.tecknobit.glider.ui.shared.presentations

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import com.tecknobit.equinoxcompose.viewmodels.EquinoxViewModel
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.annotations.Structure
import com.tecknobit.glidercore.helpers.GliderInputsValidator.scopesAreValid
import com.tecknobit.glidercore.helpers.GliderInputsValidator.tailIsValid

@Structure
abstract class PasswordFormViewModel(
    protected val passwordId: String?,
) : EquinoxViewModel(
    snackbarHostState = SnackbarHostState()
) {

    lateinit var tail: MutableState<String>

    lateinit var tailError: MutableState<Boolean>

    lateinit var scopes: MutableState<String>

    lateinit var scopesError: MutableState<Boolean>

    @RequiresSuperCall
    protected open fun validateForm(): Boolean {
        if (!tailIsValid(tail.value)) {
            tailError.value = true
            return false
        }
        if (!scopesAreValid(scopes.value)) {
            scopesError.value = true
            return false
        }
        return true
    }

}