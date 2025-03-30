package com.tecknobit.glider.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.tecknobit.equinoxcompose.components.EquinoxOutlinedTextField
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isPasswordValid
import com.tecknobit.glider.ui.theme.InputFieldShape
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.password
import glider.composeapp.generated.resources.wrong_password
import org.jetbrains.compose.resources.stringResource

/**
 * Custom [EquinoxOutlinedTextField] used to allow the user insert the password values
 *
 * @param password The state container of the password value
 * @param passwordError The state handles the validity of the password
 */
@Composable
@NonRestartableComposable
fun PasswordInputField(
    password: MutableState<String>,
    passwordError: MutableState<Boolean>,
) {
    var hiddenPassword by remember { mutableStateOf(true) }
    EquinoxOutlinedTextField(
        modifier = Modifier
            .padding(
                top = 10.dp,
                bottom = 25.dp
            ),
        placeholder = stringResource(Res.string.password),
        value = password,
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
        isError = passwordError,
        validator = { isPasswordValid(it) }
    )
}