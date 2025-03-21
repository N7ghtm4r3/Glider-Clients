@file:OptIn(ExperimentalComposeApi::class, ExperimentalLayoutApi::class)

package com.tecknobit.glider.ui.screens.account.presenter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecknobit.equinoxcompose.components.ChameleonText
import com.tecknobit.equinoxcompose.components.EquinoxOutlinedTextField
import com.tecknobit.equinoxcompose.components.EquinoxTextField
import com.tecknobit.equinoxcompose.components.stepper.Step
import com.tecknobit.equinoxcompose.components.stepper.StepContent
import com.tecknobit.equinoxcompose.components.stepper.Stepper
import com.tecknobit.equinoxcompose.session.EquinoxLocalUser.ApplicationTheme
import com.tecknobit.equinoxcompose.utilities.ResponsiveClass.COMPACT_CONTENT
import com.tecknobit.equinoxcompose.utilities.ResponsiveClass.MEDIUM_CONTENT
import com.tecknobit.equinoxcompose.utilities.ResponsiveClassComponent
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.LANGUAGES_SUPPORTED
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isEmailValid
import com.tecknobit.equinoxcore.helpers.InputsValidator.Companion.isPasswordValid
import com.tecknobit.glider.SPLASHSCREEN
import com.tecknobit.glider.bodyFontFamily
import com.tecknobit.glider.localUser
import com.tecknobit.glider.navigator
import com.tecknobit.glider.ui.components.DeleteAccount
import com.tecknobit.glider.ui.components.Logout
import com.tecknobit.glider.ui.screens.account.presentation.AccountScreenViewModel
import com.tecknobit.glider.ui.shared.presenters.GliderScreenTab
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.account
import glider.composeapp.generated.resources.change_email
import glider.composeapp.generated.resources.change_language
import glider.composeapp.generated.resources.change_password
import glider.composeapp.generated.resources.change_theme
import glider.composeapp.generated.resources.delete
import glider.composeapp.generated.resources.logout
import glider.composeapp.generated.resources.new_email
import glider.composeapp.generated.resources.new_password
import glider.composeapp.generated.resources.wrong_email
import glider.composeapp.generated.resources.wrong_password
import org.jetbrains.compose.resources.stringResource

class AccountScreenTab : GliderScreenTab<AccountScreenViewModel>(
    viewModel = AccountScreenViewModel(),
    title = Res.string.account
) {

    @Composable
    override fun ColumnScope.ScreenContent() {
        UserDetails()
        Settings()
    }

    /**
     * The details of the [localUser]
     */
    @Composable
    @NonRestartableComposable
    @ResponsiveClassComponent(
        classes = [MEDIUM_CONTENT, COMPACT_CONTENT]
    )
    private fun UserDetails() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = 10.dp
                    )
            ) {
                Text(
                    text = localUser.completeName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = viewModel.email.value,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp
                )
                ActionButtons()
            }
        }
    }

    /**
     * The actions can be execute on the [localUser] account such logout and delete account
     */
    @Composable
    @NonRestartableComposable
    private fun ActionButtons() {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            val logout = remember { mutableStateOf(false) }
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.inversePrimary
                ),
                shape = RoundedCornerShape(
                    size = 10.dp
                ),
                onClick = { logout.value = true }
            ) {
                ChameleonText(
                    text = stringResource(Res.string.logout),
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    backgroundColor = MaterialTheme.colorScheme.inversePrimary
                )
            }
            Logout(
                viewModel = viewModel,
                show = logout
            )
            val deleteAccount = remember { mutableStateOf(false) }
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                shape = RoundedCornerShape(
                    size = 10.dp
                ),
                onClick = { deleteAccount.value = true }
            ) {
                Text(
                    text = stringResource(Res.string.delete),
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            DeleteAccount(
                viewModel = viewModel,
                show = deleteAccount
            )
        }
    }

    /**
     * The settings section to customize the [localUser] experience
     */
    @Composable
    @NonRestartableComposable
    private fun Settings() {
        val steps = remember {
            arrayOf(
                Step(
                    stepIcon = Icons.Default.AlternateEmail,
                    title = Res.string.change_email,
                    content = { ChangeEmail() },
                    dismissAction = { visible -> visible.value = false },
                    confirmAction = { visible ->
                        viewModel.changeEmail(
                            onSuccess = {
                                visible.value = false
                            }
                        )
                    }
                ),
                Step(
                    stepIcon = Icons.Default.Password,
                    title = Res.string.change_password,
                    content = { ChangePassword() },
                    dismissAction = { visible -> visible.value = false },
                    confirmAction = { visible ->
                        viewModel.changePassword(
                            onSuccess = {
                                visible.value = false
                            }
                        )
                    }
                ),
                Step(
                    stepIcon = Icons.Default.Language,
                    title = Res.string.change_language,
                    content = { ChangeLanguage() },
                    dismissAction = { visible -> visible.value = false },
                    confirmAction = { visible ->
                        viewModel.changeLanguage(
                            onSuccess = {
                                visible.value = false
                                navigator.navigate(SPLASHSCREEN)
                            }
                        )
                    }
                ),
                Step(
                    stepIcon = Icons.Default.Palette,
                    title = Res.string.change_theme,
                    content = { ChangeTheme() },
                    dismissAction = { visible -> visible.value = false },
                    confirmAction = { visible ->
                        viewModel.changeTheme(
                            onChange = {
                                visible.value = false
                                navigator.navigate(SPLASHSCREEN)
                            }
                        )
                    }
                )
            )
        }
        Stepper(
            steps = steps
        )
    }

    /**
     * Section to change the [localUser]'s email
     */
    @StepContent(
        number = 1
    )
    @Composable
    @NonRestartableComposable
    private fun ChangeEmail() {
        val focusRequester = remember { FocusRequester() }
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
        viewModel.newEmail = remember { mutableStateOf("") }
        viewModel.newEmailError = remember { mutableStateOf(false) }
        EquinoxTextField(
            modifier = Modifier
                .focusRequester(focusRequester),
            textFieldColors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            value = viewModel.newEmail,
            textFieldStyle = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = bodyFontFamily
            ),
            isError = viewModel.newEmailError,
            mustBeInLowerCase = true,
            allowsBlankSpaces = false,
            validator = { isEmailValid(it) },
            errorText = Res.string.wrong_email,
            errorTextStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = bodyFontFamily
            ),
            placeholder = Res.string.new_email,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Email
            )
        )
    }

    /**
     * Section to change the [localUser]'s password
     */
    @StepContent(
        number = 2
    )
    @Composable
    @NonRestartableComposable
    private fun ChangePassword() {
        val focusRequester = remember { FocusRequester() }
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
        viewModel.newPassword = remember { mutableStateOf("") }
        viewModel.newPasswordError = remember { mutableStateOf(false) }
        var hiddenPassword by remember { mutableStateOf(true) }
        EquinoxOutlinedTextField(
            modifier = Modifier
                .focusRequester(focusRequester),
            outlinedTextFieldColors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            value = viewModel.newPassword,
            outlinedTextFieldStyle = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = bodyFontFamily
            ),
            isError = viewModel.newPasswordError,
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
            validator = { isPasswordValid(it) },
            errorText = stringResource(Res.string.wrong_password),
            errorTextStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = bodyFontFamily
            ),
            placeholder = stringResource(Res.string.new_password),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            )
        )
    }

    /**
     * Section to change the [localUser]'s language
     */
    @StepContent(
        number = 3
    )
    @Composable
    @NonRestartableComposable
    private fun ChangeLanguage() {
        Column(
            modifier = Modifier
                .selectableGroup()
        ) {
            LANGUAGES_SUPPORTED.entries.forEach { entry ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = viewModel.language.value == entry.key,
                        onClick = { viewModel.language.value = entry.key }
                    )
                    Text(
                        text = entry.value
                    )
                }
            }
        }
    }

    /**
     * Section to change the [localUser]'s theme
     */
    @StepContent(
        number = 4
    )
    @Composable
    @NonRestartableComposable
    private fun ChangeTheme() {
        Column(
            modifier = Modifier
                .selectableGroup()
        ) {
            ApplicationTheme.entries.forEach { entry ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = viewModel.theme.value == entry,
                        onClick = { viewModel.theme.value = entry }
                    )
                    Text(
                        text = entry.name
                    )
                }
            }
        }
    }

    /**
     * Method to collect or instantiate the states of the screen
     */
    @Composable
    override fun CollectStates() {
        viewModel.email = remember { mutableStateOf(localUser.email) }
        viewModel.password = remember { mutableStateOf(localUser.password) }
        viewModel.language = remember { mutableStateOf(localUser.language) }
        viewModel.theme = remember { mutableStateOf(localUser.theme) }
    }

}