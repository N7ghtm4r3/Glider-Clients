package com.tecknobit.glider.ui.screens.generate.presenter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.tecknobit.equinoxcompose.components.EquinoxOutlinedTextField
import com.tecknobit.glider.ui.screens.generate.components.QuantityPicker
import com.tecknobit.glider.ui.screens.generate.components.rememberQuantityPickerState
import com.tecknobit.glider.ui.screens.generate.presentation.GenerateScreenViewModel
import com.tecknobit.glider.ui.shared.presenters.GliderScreenTab
import com.tecknobit.glidercore.helpers.GliderInputsValidator
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.generate
import glider.composeapp.generated.resources.scopes
import glider.composeapp.generated.resources.tail
import glider.composeapp.generated.resources.wrong_scopes
import glider.composeapp.generated.resources.wrong_tail

class GenerateScreenTab(
    passwordId: String? = null,
) : GliderScreenTab<GenerateScreenViewModel>(
    viewModel = GenerateScreenViewModel(
        passwordId = passwordId
    ),
    title = Res.string.generate
) {

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
            EquinoxOutlinedTextField(
                shape = RoundedCornerShape(
                    size = 10.dp
                ),
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
                shape = RoundedCornerShape(
                    size = 10.dp
                ),
                value = viewModel.scopes,
                placeholder = Res.string.scopes,
                errorText = Res.string.wrong_scopes,
                allowsBlankSpaces = false,
                isError = viewModel.scopesError,
                validator = { GliderInputsValidator.scopesAreValid(it) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            QuantityPicker(
                modifier = Modifier
                    .padding(
                        top = 10.dp
                    ),
                state = rememberQuantityPickerState(
                    initialQuantity = 0
                )
            )
        }
    }

    /**
     * Method to collect or instantiate the states of the screen
     */
    @Composable
    override fun CollectStates() {
        viewModel.tail = remember { mutableStateOf("") }
        viewModel.tailError = remember { mutableStateOf(false) }
        viewModel.scopes = remember { mutableStateOf("") }
        viewModel.scopesError = remember { mutableStateOf(false) }
        viewModel.passwordLength = remember { mutableStateOf("") }
        viewModel.passwordLengthError = remember { mutableStateOf(false) }
    }

}