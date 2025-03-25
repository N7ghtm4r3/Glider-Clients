package com.tecknobit.glider.ui.screens.editgeneratedpassword.presenter

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.tecknobit.glider.ui.screens.editgeneratedpassword.presentation.EditGeneratedPasswordScreenViewModel
import com.tecknobit.glider.ui.shared.presenters.EditPasswordFormScreen

class EditGeneratedPasswordScreen(
    passwordId: String,
) : EditPasswordFormScreen<EditGeneratedPasswordScreenViewModel>(
    viewModel = EditGeneratedPasswordScreenViewModel(
        passwordId = passwordId
    )
) {

    @Composable
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