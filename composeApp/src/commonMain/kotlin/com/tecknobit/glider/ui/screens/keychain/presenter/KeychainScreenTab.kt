@file:OptIn(ExperimentalMultiplatform::class)

package com.tecknobit.glider.ui.screens.keychain.presenter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecknobit.equinoxcompose.components.DebouncedOutlinedTextField
import com.tecknobit.equinoxcompose.session.ManagedContent
import com.tecknobit.equinoxcompose.utilities.responsiveAssignment
import com.tecknobit.glider.ui.screens.keychain.components.PasswordTypeFiltersMenu
import com.tecknobit.glider.ui.screens.keychain.components.Passwords
import com.tecknobit.glider.ui.screens.keychain.presentation.KeychainScreenViewModel
import com.tecknobit.glider.ui.shared.presenters.GliderScreenTab
import com.tecknobit.glider.ui.theme.InputFieldShape
import com.tecknobit.glidercore.enums.PasswordType
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.keychain
import glider.composeapp.generated.resources.search_placeholder

class KeychainScreenTab : GliderScreenTab<KeychainScreenViewModel>(
    viewModel = KeychainScreenViewModel(),
    title = Res.string.keychain
) {

    // TODO: WHEN POSSIBLE CUSTOMIZE THE COLORS OF THE ManagedContent
    @Composable
    override fun ColumnScope.ScreenContent() {
        ManagedContent(
            viewModel = viewModel,
            content = {
                Column {
                    Filters()
                    Passwords(
                        viewModel = viewModel
                    )
                }
            }
        )
    }

    @Composable
    @NonRestartableComposable
    private fun Filters() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = responsiveAssignment(
                onExpandedSizeClass = { Arrangement.Center },
                onMediumSizeClass = { Arrangement.Center },
                onCompactSizeClass = { Arrangement.Start }
            )
        ) {
            DebouncedOutlinedTextField(
                value = viewModel.keywords,
                shape = InputFieldShape,
                width = 250.dp,
                debounce = { viewModel.passwordsState.refresh() },
                placeholder = Res.string.search_placeholder
            )
            Spacer(Modifier.width(10.dp))
            Column {
                val expanded = remember { mutableStateOf(false) }
                IconButton(
                    onClick = { expanded.value = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterAlt,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                PasswordTypeFiltersMenu(
                    expanded = expanded,
                    viewModel = viewModel
                )
            }
        }
    }

    /**
     * Method to collect or instantiate the states of the screen
     */
    @Composable
    override fun CollectStates() {
        viewModel.keywords = remember { mutableStateOf("") }
        viewModel.passwordTypes = remember { mutableStateListOf() }
        LaunchedEffect(Unit) {
            viewModel.passwordTypes.addAll(PasswordType.entries)
        }
    }

}