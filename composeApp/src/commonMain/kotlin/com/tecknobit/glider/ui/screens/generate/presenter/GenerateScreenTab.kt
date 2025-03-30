package com.tecknobit.glider.ui.screens.generate.presenter

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecknobit.equinoxcompose.session.screens.EquinoxNoModelScreen
import com.tecknobit.equinoxcompose.session.screens.EquinoxScreen
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.PASSWORD_MAX_LENGTH
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.PASSWORD_MIN_LENGTH
import com.tecknobit.glider.ui.screens.generate.components.PasswordOptions
import com.tecknobit.glider.ui.screens.generate.components.QuantityPicker
import com.tecknobit.glider.ui.screens.generate.components.rememberQuantityPickerState
import com.tecknobit.glider.ui.screens.generate.presentation.GenerateScreenViewModel
import com.tecknobit.glider.ui.shared.presenters.GliderScreenTab
import com.tecknobit.glider.ui.shared.presenters.PasswordFormScreen
import com.tecknobit.glider.ui.theme.AppTypography
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.generate
import glider.composeapp.generated.resources.generating
import glider.composeapp.generated.resources.password_length
import org.jetbrains.compose.resources.stringResource

/**
 * The `GenerateScreenTab` class is useful to display the form allowing the user to insert the
 * password data to generate it
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @see EquinoxNoModelScreen
 * @see EquinoxScreen
 * @see GliderScreenTab
 * @see PasswordFormScreen
 */
class GenerateScreenTab : PasswordFormScreen<GenerateScreenViewModel>(
    viewModel = GenerateScreenViewModel(),
    title = Res.string.generate
) {

    /**
     * The form where the user can insert the details of the password
     */
    @Composable
    override fun ColumnScope.Form() {
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
        TailInputField()
        ScopesInputField()
        PasswordOptions(
            modifier = Modifier
                .padding(
                    top = 10.dp,
                    bottom = 15.dp
                ),
            viewModel = viewModel
        )
        PerformFormActionButton(
            performActionText = Res.string.generate,
            performingActionText = Res.string.generating
        )
    }

    /**
     * Method to collect or instantiate the states of the screen
     */
    @Composable
    @RequiresSuperCall
    override fun CollectStates() {
        super.CollectStates()
        viewModel.quantityPickerState = rememberQuantityPickerState(
            initialQuantity = PASSWORD_MIN_LENGTH,
            minQuantity = PASSWORD_MIN_LENGTH,
            maxQuantity = PASSWORD_MAX_LENGTH,
            longPressQuantity = 4
        )
        viewModel.includeNumbers = remember { mutableStateOf(true) }
        viewModel.includeUppercaseLetters = remember { mutableStateOf(true) }
        viewModel.includeSpecialCharacters = remember { mutableStateOf(true) }
    }

}