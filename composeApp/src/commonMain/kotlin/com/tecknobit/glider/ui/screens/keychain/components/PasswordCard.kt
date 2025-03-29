@file:OptIn(ExperimentalMaterial3Api::class)

package com.tecknobit.glider.ui.screens.keychain.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tecknobit.glider.EDIT_GENERATED_PASSWORD_SCREEN
import com.tecknobit.glider.EDIT_INSERTED_PASSWORD_SCREEN
import com.tecknobit.glider.navigator
import com.tecknobit.glider.ui.components.DeletePassword
import com.tecknobit.glider.ui.components.RefreshPassword
import com.tecknobit.glider.ui.icons.SettingsBRoll
import com.tecknobit.glider.ui.screens.keychain.data.Password
import com.tecknobit.glider.ui.screens.keychain.presentation.KeychainScreenViewModel
import com.tecknobit.glider.ui.theme.AppTypography
import com.tecknobit.glider.ui.theme.applyDarkTheme
import com.tecknobit.glidercore.enums.PasswordType.GENERATED

private const val HIDDEN_PASSWORD = "********"

@Composable
@NonRestartableComposable
fun PasswordCard(
    viewModel: KeychainScreenViewModel,
    password: Password,
) {
    val showTimeline = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        onClick = {
            viewModel.copy(
                password = password
            )
        }
    ) {
        ToolsBar(
            password = password,
            viewModel = viewModel,
            showTimeline = showTimeline
        )
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 12.dp
                )
                .padding(
                    bottom = 12.dp
                )
        ) {
            PasswordTypeBadge(
                type = password.type
            )
            Text(
                modifier = Modifier
                    .padding(
                        top = 5.dp
                    ),
                text = password.tail,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = AppTypography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            PasswordText(
                password = password
            )
        }
        Scopes(
            password = password
        )
        PasswordTimeline(
            show = showTimeline,
            password = password
        )
    }
}

@Composable
@NonRestartableComposable
private fun PasswordText(
    password: Password,
) {
    var hidden by remember { mutableStateOf(true) }
    val passwordValue by remember { password.password }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(3f)
        ) {
            AnimatedVisibility(
                visible = !hidden
            ) {
                Text(
                    text = passwordValue,
                    style = AppTypography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            AnimatedVisibility(
                visible = hidden
            ) {
                Text(
                    text = HIDDEN_PASSWORD,
                    style = AppTypography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            IconButton(
                modifier = Modifier
                    .size(26.dp),
                onClick = { hidden = !hidden }
            ) {
                Icon(
                    imageVector = Icons.Default.Visibility,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
@NonRestartableComposable
private fun ToolsBar(
    viewModel: KeychainScreenViewModel,
    password: Password,
    showTimeline: MutableState<Boolean>,
) {
    var hidden by rememberSaveable { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                all = 12.dp
            )
    ) {
        Row(
            modifier = Modifier
                .animateContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        hidden = !hidden
                        if (hidden)
                            showTimeline.value = false
                    },
                imageVector = if (hidden)
                    SettingsBRoll
                else
                    Icons.Outlined.Close,
                contentDescription = null
            )
            if (!hidden) {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            if (password.type == GENERATED) {
                                navigator.navigate(
                                    route = "$EDIT_GENERATED_PASSWORD_SCREEN/${password.id}"
                                )
                            } else {
                                navigator.navigate(
                                    route = "$EDIT_INSERTED_PASSWORD_SCREEN/${password.id}"
                                )
                            }
                        },
                    imageVector = Icons.Default.Edit,
                    contentDescription = null
                )
                PasswordTimelineButton(
                    showTimeline = showTimeline
                )
                if (password.type == GENERATED) {
                    RefreshPasswordButton(
                        viewModel = viewModel,
                        password = password
                    )
                }
                DeletePasswordButton(
                    viewModel = viewModel,
                    password = password
                )
            }
        }
    }
}

@Composable
@NonRestartableComposable
private fun PasswordTimelineButton(
    showTimeline: MutableState<Boolean>,
) {
    Icon(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { showTimeline.value = !showTimeline.value },
        imageVector = Icons.Default.Timeline,
        contentDescription = null
    )
}

@Composable
@NonRestartableComposable
private fun RefreshPasswordButton(
    viewModel: KeychainScreenViewModel,
    password: Password,
) {
    val refreshPassword = remember { mutableStateOf(false) }
    Icon(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { refreshPassword.value = !refreshPassword.value },
        imageVector = Icons.Default.Refresh,
        contentDescription = null
    )
    RefreshPassword(
        viewModel = viewModel,
        show = refreshPassword,
        password = password
    )
}

@Composable
@NonRestartableComposable
private fun DeletePasswordButton(
    viewModel: KeychainScreenViewModel,
    password: Password,
) {
    val deletePassword = remember { mutableStateOf(false) }
    Icon(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { deletePassword.value = !deletePassword.value },
        imageVector = Icons.Default.Delete,
        contentDescription = null,
        tint = if (applyDarkTheme())
            MaterialTheme.colorScheme.onError
        else
            MaterialTheme.colorScheme.errorContainer
    )
    DeletePassword(
        viewModel = viewModel,
        show = deletePassword,
        password = password
    )
}