package com.tecknobit.glider.ui.screens.generate.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecknobit.glider.ui.screens.generate.presentation.GenerateScreenViewModel
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.include_numbers
import glider.composeapp.generated.resources.include_special_characters
import glider.composeapp.generated.resources.include_uppercase_letters
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * Component used to insert the options to use as configuration to generate the password
 *
 * @param modifier The modifier to apply to the component
 * @param viewModel The support viewmodel used by the screen
 */
@Composable
@NonRestartableComposable
fun PasswordOptions(
    modifier: Modifier = Modifier,
    viewModel: GenerateScreenViewModel,
) {
    Column(
        modifier = modifier
            .width(300.dp)
    ) {
        PasswordOptionCheckBox(
            checked = viewModel.includeNumbers,
            infoText = Res.string.include_numbers
        )
        PasswordOptionCheckBox(
            checked = viewModel.includeUppercaseLetters,
            infoText = Res.string.include_uppercase_letters
        )
        PasswordOptionCheckBox(
            checked = viewModel.includeSpecialCharacters,
            infoText = Res.string.include_special_characters
        )
    }
}

/**
 * Custom [Checkbox] used to select or unselect a password option
 *
 * @param checked Whether the option is enabled
 * @param infoText The informative text about the option
 */
@Composable
@NonRestartableComposable
private fun PasswordOptionCheckBox(
    checked: MutableState<Boolean>,
    infoText: StringResource,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked.value,
            onCheckedChange = { checked.value = it }
        )
        Text(
            text = stringResource(infoText),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}