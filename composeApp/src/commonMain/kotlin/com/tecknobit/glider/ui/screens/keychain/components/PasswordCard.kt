package com.tecknobit.glider.ui.screens.keychain.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tecknobit.equinoxcompose.utilities.copyOnClipboard
import com.tecknobit.glider.ui.components.PasswordTypeBadge
import com.tecknobit.glider.ui.screens.keychain.data.Password
import com.tecknobit.glider.ui.screens.keychain.presentation.KeychainScreenViewModel
import com.tecknobit.glider.ui.theme.AppTypography
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.copy
import glider.composeapp.generated.resources.password_copied
import org.jetbrains.compose.resources.stringResource

private const val HIDDEN_PASSWORD = "********"

@Composable
@NonRestartableComposable
fun PasswordCard(
    viewModel: KeychainScreenViewModel,
    password: Password,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = Color.Transparent
            ),
            overlineContent = {
                PasswordTypeBadge(
                    type = password.type
                )
            },
            headlineContent = {
                Text(
                    text = password.tail,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = AppTypography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            supportingContent = {
                PasswordText(
                    password = password
                )
            }
        )
        CopyPasswordButton(
            viewModel = viewModel,
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
                    text = password.password,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            AnimatedVisibility(
                visible = hidden
            ) {
                Text(
                    text = HIDDEN_PASSWORD,
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
private fun CopyPasswordButton(
    viewModel: KeychainScreenViewModel,
    password: Password,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                copyOnClipboard(
                    content = password.password,
                    onCopy = {
                        viewModel.showSnackbarMessage(
                            message = Res.string.password_copied
                        )
                    }
                )
            }
    ) {
        Text(
            modifier = Modifier
                .padding(
                    all = 8.dp
                )
                .fillMaxWidth(),
            text = stringResource(Res.string.copy),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            style = AppTypography.titleMedium
        )
    }
}