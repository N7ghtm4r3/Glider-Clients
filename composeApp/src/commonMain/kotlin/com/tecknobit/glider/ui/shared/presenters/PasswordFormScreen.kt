package com.tecknobit.glider.ui.shared.presenters

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.tecknobit.equinoxcompose.annotations.ScreenCoordinator
import com.tecknobit.equinoxcompose.annotations.ScreenSection
import com.tecknobit.equinoxcompose.components.EquinoxOutlinedTextField
import com.tecknobit.equinoxcompose.session.screens.EquinoxNoModelScreen
import com.tecknobit.equinoxcompose.session.screens.EquinoxScreen
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.annotations.Structure
import com.tecknobit.glider.ui.shared.presentations.PasswordFormViewModel
import com.tecknobit.glider.ui.theme.AppTypography
import com.tecknobit.glider.ui.theme.ButtonShape
import com.tecknobit.glider.ui.theme.ButtonSize
import com.tecknobit.glider.ui.theme.InputFieldShape
import com.tecknobit.glidercore.helpers.GliderInputsValidator
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.comma_separated_list
import glider.composeapp.generated.resources.scopes
import glider.composeapp.generated.resources.tail
import glider.composeapp.generated.resources.wrong_scopes
import glider.composeapp.generated.resources.wrong_tail
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * The `PasswordFormScreen` class is useful to display the forms allowing the user to insert the
 * password data to generate or insert a password
 *
 * @property viewModel The support viewmodel of the screen
 * @property title The title of the screen
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @param V generic type used to allow the use of own viewmodel in custom screens
 *
 * @see EquinoxNoModelScreen
 * @see EquinoxScreen
 * @see GliderScreenTab
 */
@Structure
@ScreenCoordinator
abstract class PasswordFormScreen<V : PasswordFormViewModel>(
    viewModel: V,
    title: StringResource,
) : GliderScreenTab<V>(
    viewModel = viewModel,
    title = title
) {

    /**
     * `performingPasswordOperation` whether is currently performing an operation related to a
     * password such generating, inserting or editing
     */
    protected lateinit var performingPasswordOperation: State<Boolean>

    /**
     * The custom content displayed in the tab
     */
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
            Form()
        }
    }

    /**
     * The form where the user can insert the details of the password
     */
    @Composable
    @ScreenSection
    protected abstract fun ColumnScope.Form()

    /**
     * Custom [EquinoxOutlinedTextField] used to allow the user insert the value of the tail of the
     * password
     */
    @Composable
    protected fun TailInputField() {
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
    }

    /**
     * Custom [EquinoxOutlinedTextField] used to allow the user insert the value of the scopes of the
     * password
     *
     * @param modifier The modifier to apply to the component
     * @param imeAction The ime action to attach to the component
     */
    @Composable
    protected fun ScopesInputField(
        modifier: Modifier = Modifier,
        imeAction: ImeAction = ImeAction.Done,
    ) {
        EquinoxOutlinedTextField(
            modifier = modifier
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
                imeAction = imeAction
            ),
            supportingText = {
                Text(
                    text = stringResource(Res.string.comma_separated_list)
                )
            }
        )
    }

    /**
     * Custom [Button] used to perform the form action
     *
     * @param performActionText The representative text of the action to perform
     * @param performingActionText The representative text of the performing action
     */
    @Composable
    protected fun PerformFormActionButton(
        performActionText: StringResource,
        performingActionText: StringResource,
    ) {
        val softwareKeyboardController = LocalSoftwareKeyboardController.current
        Button(
            modifier = Modifier
                .size(ButtonSize),
            shape = ButtonShape,
            enabled = !performingPasswordOperation.value,
            onClick = {
                softwareKeyboardController?.hide()
                viewModel.performPasswordOperation()
            }
        ) {
            Text(
                text = stringResource(
                    if (performingPasswordOperation.value)
                        performingActionText
                    else
                        performActionText
                ),
                style = AppTypography.bodyLarge
            )
        }
    }

    /**
     * Method to collect or instantiate the states of the screen
     */
    @Composable
    @RequiresSuperCall
    override fun CollectStates() {
        viewModel.tail = remember { mutableStateOf("") }
        viewModel.tailError = remember { mutableStateOf(false) }
        viewModel.scopes = remember { mutableStateOf("") }
        viewModel.scopesError = remember { mutableStateOf(false) }
        performingPasswordOperation = viewModel.performingPasswordOperation.collectAsState(
            initial = false
        )
    }

}