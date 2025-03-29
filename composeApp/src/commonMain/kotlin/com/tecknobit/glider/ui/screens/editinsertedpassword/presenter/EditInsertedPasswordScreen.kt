package com.tecknobit.glider.ui.screens.editinsertedpassword.presenter

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.ImeAction
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.glider.ui.components.PasswordInputField
import com.tecknobit.glider.ui.screens.editinsertedpassword.presentation.EditInsertedPasswordScreenViewModel
import com.tecknobit.glider.ui.shared.presenters.EditPasswordFormScreen

class EditInsertedPasswordScreen(
    passwordId: String,
) : EditPasswordFormScreen<EditInsertedPasswordScreenViewModel>(
    viewModel = EditInsertedPasswordScreenViewModel(
        passwordId = passwordId
    )
) {

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

    @Composable
    @RequiresSuperCall
    override fun CollectStates() {
        super.CollectStates()
        viewModel.passwordError = remember { mutableStateOf(false) }
    }

    @Composable
    @RequiresSuperCall
    override fun CollectStatesAfterLoading() {
        super.CollectStatesAfterLoading()
        viewModel.passwordValue = remember {
            mutableStateOf(viewModel.password.value!!.password.value)
        }
    }

}