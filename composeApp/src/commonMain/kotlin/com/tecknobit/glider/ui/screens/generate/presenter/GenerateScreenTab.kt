package com.tecknobit.glider.ui.screens.generate.presenter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.tecknobit.equinoxcompose.components.EquinoxOutlinedTextField
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.PASSWORD_MAX_LENGTH
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.PASSWORD_MIN_LENGTH
import com.tecknobit.glider.ui.screens.generate.components.PasswordOptions
import com.tecknobit.glider.ui.screens.generate.components.QuantityPicker
import com.tecknobit.glider.ui.screens.generate.components.rememberQuantityPickerState
import com.tecknobit.glider.ui.screens.generate.presentation.GenerateScreenViewModel
import com.tecknobit.glider.ui.shared.presenters.GliderScreenTab
import com.tecknobit.glider.ui.theme.AppTypography
import com.tecknobit.glider.ui.theme.ButtonShape
import com.tecknobit.glider.ui.theme.ButtonSize
import com.tecknobit.glider.ui.theme.InputFieldShape
import com.tecknobit.glidercore.helpers.GliderInputsValidator
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.comma_separated_list
import glider.composeapp.generated.resources.generate
import glider.composeapp.generated.resources.password_length
import glider.composeapp.generated.resources.scopes
import glider.composeapp.generated.resources.tail
import glider.composeapp.generated.resources.wrong_scopes
import glider.composeapp.generated.resources.wrong_tail
import org.jetbrains.compose.resources.stringResource

class GenerateScreenTab(
    passwordId: String? = null,
) : GliderScreenTab<GenerateScreenViewModel>(
    viewModel = GenerateScreenViewModel(
        passwordId = passwordId
    ),
    title = Res.string.generate
) {

    private lateinit var generatingPassword: State<Boolean>

    @Composable
    override fun ColumnScope.ScreenContent() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            QuantityPicker(
                state = viewModel.quantityPickerState,
                informativeText = stringResource(Res.string.password_length),
                informativeTextStyle = AppTypography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                quantityIndicatorStyle = AppTypography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            EquinoxOutlinedTextField(
                modifier = Modifier
                    .padding(
                        top = 10.dp
                    ),
                shape = InputFieldShape,
                value = viewModel.tail,
                placeholder = Res.string.tail,
                errorText = Res.string.wrong_tail,
                isError = viewModel.tailError,
                validator = { GliderInputsValidator.tailIsValid(it) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            EquinoxOutlinedTextField(
                modifier = Modifier
                    .padding(
                        top = 10.dp
                    ),
                shape = InputFieldShape,
                value = viewModel.scopes,
                placeholder = Res.string.scopes,
                errorText = Res.string.wrong_scopes,
                allowsBlankSpaces = false,
                isError = viewModel.scopesError,
                validator = { GliderInputsValidator.scopesAreValid(it) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                supportingText = {
                    Text(
                        text = stringResource(Res.string.comma_separated_list)
                    )
                }
            )
            PasswordOptions(
                modifier = Modifier
                    .padding(
                        top = 10.dp,
                        bottom = 15.dp
                    ),
                viewModel = viewModel
            )
            Button(
                modifier = Modifier
                    .size(ButtonSize),
                shape = ButtonShape,
                enabled = !generatingPassword.value,
                onClick = { viewModel.generatePassword() }
            ) {
                Text(
                    text = stringResource(Res.string.generate),
                    style = AppTypography.bodyLarge
                )
            }
        }
    }

    /**
     * Method to collect or instantiate the states of the screen
     */
    @Composable
    override fun CollectStates() {
        viewModel.quantityPickerState = rememberQuantityPickerState(
            initialQuantity = PASSWORD_MIN_LENGTH,
            minQuantity = PASSWORD_MIN_LENGTH,
            maxQuantity = PASSWORD_MAX_LENGTH,
            longPressQuantity = 4
        )
        viewModel.tail = remember { mutableStateOf("") }
        viewModel.tailError = remember { mutableStateOf(false) }
        viewModel.scopes = remember { mutableStateOf("") }
        viewModel.scopesError = remember { mutableStateOf(false) }
        viewModel.includeNumbers = remember { mutableStateOf(true) }
        viewModel.includeUppercaseLetters = remember { mutableStateOf(true) }
        viewModel.includeSpecialCharacters = remember { mutableStateOf(true) }
        generatingPassword = viewModel.generatingPassword.collectAsState(
            initial = false
        )
    }

}