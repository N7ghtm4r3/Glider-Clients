package com.tecknobit.glider.ui.screens.editgeneratedpassword.presenter

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.tecknobit.equinoxcompose.annotations.ScreenSection
import com.tecknobit.equinoxcompose.session.screens.EquinoxNoModelScreen
import com.tecknobit.equinoxcompose.session.screens.EquinoxScreen
import com.tecknobit.glider.ui.screens.editgeneratedpassword.presentation.EditGeneratedPasswordScreenViewModel
import com.tecknobit.glider.ui.shared.presenters.EditPasswordFormScreen
import com.tecknobit.glider.ui.shared.presenters.GliderScreenTab
import com.tecknobit.glider.ui.shared.presenters.PasswordFormScreen

/**
 * The `EditGeneratedPasswordScreen` class is useful to display the form allowing the user to edit
 * an existing [com.tecknobit.glidercore.enums.PasswordType.GENERATED] password
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
class EditGeneratedPasswordScreen(
    passwordId: String,
) : EditPasswordFormScreen<EditGeneratedPasswordScreenViewModel>(
    viewModel = EditGeneratedPasswordScreenViewModel(
        passwordId = passwordId
    )
) {

    /**
     * The form where the user can insert the details of the password
     */
    @Composable
    @ScreenSection
    override fun ColumnScope.Form() {
        TailInputField()
        ScopesInputField(
            modifier = Modifier
                .padding(
                    bottom = 15.dp
                ),
            imeAction = ImeAction.Next
        )
        EditPasswordButton()
    }

}