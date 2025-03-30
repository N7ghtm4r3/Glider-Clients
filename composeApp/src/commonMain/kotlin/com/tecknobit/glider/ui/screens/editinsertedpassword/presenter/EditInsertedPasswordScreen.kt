package com.tecknobit.glider.ui.screens.editinsertedpassword.presenter

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.ImeAction
import com.tecknobit.equinoxcompose.session.screens.EquinoxNoModelScreen
import com.tecknobit.equinoxcompose.session.screens.EquinoxScreen
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.glider.ui.components.PasswordInputField
import com.tecknobit.glider.ui.screens.editinsertedpassword.presentation.EditInsertedPasswordScreenViewModel
import com.tecknobit.glider.ui.shared.presenters.EditPasswordFormScreen
import com.tecknobit.glider.ui.shared.presenters.GliderScreenTab
import com.tecknobit.glider.ui.shared.presenters.PasswordFormScreen

/**
 * The `EditInsertedPasswordScreen` class is useful to display the form allowing the user to edit
 * an existing [com.tecknobit.glidercore.enums.PasswordType.INSERTED] password
 *
 * @param passwordId The identifier of the password
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @see EquinoxNoModelScreen
 * @see EquinoxScreen
 * @see GliderScreenTab
 * @see PasswordFormScreen
 * @see EditPasswordFormScreen
 */
class EditInsertedPasswordScreen(
    passwordId: String,
) : EditPasswordFormScreen<EditInsertedPasswordScreenViewModel>(
    viewModel = EditInsertedPasswordScreenViewModel(
        passwordId = passwordId
    )
) {

    /**
     * The form where the user can insert the details of the password
     */
    @Composable
    @NonRestartableComposable
    override fun ColumnScope.Form() {
        TailInputField()
        ScopesInputField(
            imeAction = ImeAction.Next
        )
        PasswordInputField(
            password = viewModel.passwordValue,
            passwordError = viewModel.passwordError
        )
        EditPasswordButton()
    }

    /**
     * Method to collect or instantiate the states of the screen
     */
    @Composable
    @RequiresSuperCall
    override fun CollectStates() {
        super.CollectStates()
        viewModel.passwordError = remember { mutableStateOf(false) }
    }

    /**
     * Method to collect or instantiate the states of the screen after a loading required to correctly
     * assign an initial value to the states
     */
    @Composable
    @RequiresSuperCall
    override fun CollectStatesAfterLoading() {
        super.CollectStatesAfterLoading()
        viewModel.passwordValue = remember {
            mutableStateOf(viewModel.password.value!!.password!!)
        }
    }

}