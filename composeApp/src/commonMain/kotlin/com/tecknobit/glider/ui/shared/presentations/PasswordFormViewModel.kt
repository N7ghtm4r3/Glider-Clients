package com.tecknobit.glider.ui.shared.presentations

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import com.tecknobit.equinoxcompose.session.viewmodels.EquinoxViewModel
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.annotations.Structure
import com.tecknobit.glidercore.helpers.GliderInputsValidator.scopesAreValid
import com.tecknobit.glidercore.helpers.GliderInputsValidator.tailIsValid
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * The `PasswordFormViewModel` class is the support class used by the screens which have to handle
 * the forms where the user can insert the details about a password such tail, scopes and password
 * value
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @see androidx.lifecycle.ViewModel
 * @see com.tecknobit.equinoxcompose.session.Retriever
 * @see EquinoxViewModel
 *
 */
@Structure
abstract class PasswordFormViewModel : EquinoxViewModel(
    snackbarHostState = SnackbarHostState()
) {

    /**
     * `tail` the tail of the password
     */
    lateinit var tail: MutableState<String>

    /**
     * `tailError` whether the [tail] field is not valid
     */
    lateinit var tailError: MutableState<Boolean>

    /**
     * `scopes` the scopes of the password
     */
    lateinit var scopes: MutableState<String>

    /**
     * `scopesError` whether the [scopes] field is not valid
     */
    lateinit var scopesError: MutableState<Boolean>

    /**
     * `_performingPasswordOperation` whether is currently performing an operation related to a
     * password such generating, inserting or editing
     */
    protected val _performingPasswordOperation = MutableSharedFlow<Boolean>(
        replay = 1
    )
    val performingPasswordOperation = _performingPasswordOperation.asSharedFlow()

    /**
     * Method to execute the operation related to the password such generating or inserting
     */
    abstract fun performPasswordOperation()

    /**
     * Method to validate the form values
     *
     * @return whether the form is valid as [Boolean]
     */
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

}