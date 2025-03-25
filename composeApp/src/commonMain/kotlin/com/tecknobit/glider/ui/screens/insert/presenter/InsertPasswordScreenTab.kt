package com.tecknobit.glider.ui.screens.insert.presenter

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.ImeAction
import com.tecknobit.glider.ui.components.PasswordInputField
import com.tecknobit.glider.ui.screens.insert.presentation.InsertPasswordScreenViewModel
import com.tecknobit.glider.ui.shared.presenters.PasswordFormScreen
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.insert
import glider.composeapp.generated.resources.inserting

class InsertPasswordScreenTab : PasswordFormScreen<InsertPasswordScreenViewModel>(
    viewModel = InsertPasswordScreenViewModel(),
    title = Res.string.insert
) {

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

    @Composable
    @NonRestartableComposable
    override fun CollectStates() {
        super.CollectStates()
        viewModel.passwordValue = remember { mutableStateOf("") }
        viewModel.passwordError = remember { mutableStateOf(false) }
    }

}