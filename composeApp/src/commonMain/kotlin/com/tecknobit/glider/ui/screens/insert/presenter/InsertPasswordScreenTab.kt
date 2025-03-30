package com.tecknobit.glider.ui.screens.insert.presenter

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.ImeAction
import com.tecknobit.equinoxcompose.session.screens.EquinoxNoModelScreen
import com.tecknobit.equinoxcompose.session.screens.EquinoxScreen
import com.tecknobit.glider.ui.components.PasswordInputField
import com.tecknobit.glider.ui.screens.insert.presentation.InsertPasswordScreenViewModel
import com.tecknobit.glider.ui.shared.presenters.GliderScreenTab
import com.tecknobit.glider.ui.shared.presenters.PasswordFormScreen
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.insert
import glider.composeapp.generated.resources.inserting

/**
 * The `InsertPasswordScreenTab` class is useful to display the form allowing the user to insert the
 * password data to insert it
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @see EquinoxNoModelScreen
 * @see EquinoxScreen
 * @see GliderScreenTab
 * @see PasswordFormScreen
 */
class InsertPasswordScreenTab : PasswordFormScreen<InsertPasswordScreenViewModel>(
    viewModel = InsertPasswordScreenViewModel(),
    title = Res.string.insert
) {

    /**
     * The form where the user can insert the details of the password
     */
    @Composable
    override fun ColumnScope.Form() {
        TailInputField()
        ScopesInputField(
            imeAction = ImeAction.Next
        )
        PasswordInputField(
            password = viewModel.passwordValue,
            passwordError = viewModel.passwordError
        )
        PerformFormActionButton(
            performActionText = Res.string.insert,
            performingActionText = Res.string.inserting
        )
    }

    /**
     * Method to collect or instantiate the states of the screen
     */
    @Composable
    @NonRestartableComposable
    override fun CollectStates() {
        super.CollectStates()
        viewModel.passwordValue = remember { mutableStateOf("") }
        viewModel.passwordError = remember { mutableStateOf(false) }
    }

}