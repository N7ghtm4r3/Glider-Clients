package com.tecknobit.glider.ui.screens.insert.presenter

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.tecknobit.equinoxcompose.components.EquinoxOutlinedTextField
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isPasswordValid
import com.tecknobit.glider.ui.screens.insert.presentation.InsertPasswordScreenViewModel
import com.tecknobit.glider.ui.shared.presenters.PasswordFormScreen
import com.tecknobit.glider.ui.theme.InputFieldShape
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.insert
import glider.composeapp.generated.resources.inserting
import glider.composeapp.generated.resources.password
import glider.composeapp.generated.resources.wrong_password
import org.jetbrains.compose.resources.stringResource

class InsertPasswordScreenTab(
    passwordId: String? = null,
) : PasswordFormScreen<InsertPasswordScreenViewModel>(
    viewModel = InsertPasswordScreenViewModel(
        passwordId = passwordId
    ),
    title = Res.string.insert
) {

    @Composable
    override fun ColumnScope.Form() {
        TailInputField()
        ScopesInputField(
            imeAction = ImeAction.Next
        )
        PasswordInputField()
        PerformFormActionButton(
            performActionText = Res.string.insert,
            performingActionText = Res.string.inserting
        )
    }

    @Composable
    @NonRestartableComposable
    private fun PasswordInputField() {
        var hiddenPassword by remember { mutableStateOf(true) }
        EquinoxOutlinedTextField(
            modifier = Modifier
                .padding(
                    bottom = 25.dp
                ),
            value = viewModel.password,
            label = stringResource(Res.string.password),
            shape = InputFieldShape,
            allowsBlankSpaces = false,
            trailingIcon = {
                IconButton(
                    onClick = { hiddenPassword = !hiddenPassword }
                ) {
                    Icon(
                        imageVector = if (hiddenPassword)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (hiddenPassword)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            errorText = stringResource(Res.string.wrong_password),
            isError = viewModel.passwordError,
            validator = { isPasswordValid(it) }
        )
    }

    @Composable
    @NonRestartableComposable
    override fun CollectStates() {
        super.CollectStates()
        viewModel.password = remember { mutableStateOf("") }
        viewModel.passwordError = remember { mutableStateOf(false) }
    }

}