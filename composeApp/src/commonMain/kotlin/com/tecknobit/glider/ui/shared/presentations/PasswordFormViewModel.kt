package com.tecknobit.glider.ui.shared.presentations

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import com.tecknobit.equinoxcompose.viewmodels.EquinoxViewModel
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.annotations.Structure
import com.tecknobit.glidercore.helpers.GliderInputsValidator.scopesAreValid
import com.tecknobit.glidercore.helpers.GliderInputsValidator.tailIsValid
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@Structure
abstract class PasswordFormViewModel : EquinoxViewModel(
    snackbarHostState = SnackbarHostState()
) {

    lateinit var tail: MutableState<String>

    lateinit var tailError: MutableState<Boolean>

    lateinit var scopes: MutableState<String>

    lateinit var scopesError: MutableState<Boolean>

    protected val _performingPasswordOperation = MutableSharedFlow<Boolean>(
        replay = 1
    )
    val performingPasswordOperation = _performingPasswordOperation.asSharedFlow()

    /**
     * Method to execute the operation related to the password such generating or inserting
     */
    abstract fun performPasswordOperation()

    /**
     * Method to reset the form to the initial state to perform a new action
     *
     * @param extra Extra parameters to use to reset the form state
     */
    @RequiresSuperCall
    protected open fun resetForm(
        vararg extra: Any,
    ) {
        tail.value = ""
        scopes.value = ""
    }

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