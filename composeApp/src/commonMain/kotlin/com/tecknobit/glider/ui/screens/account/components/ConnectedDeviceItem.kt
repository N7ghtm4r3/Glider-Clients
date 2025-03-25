@file:OptIn(ExperimentalMaterial3Api::class)

package com.tecknobit.glider.ui.screens.account.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.DesktopWindows
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecknobit.equinoxcompose.utilities.responsiveAssignment
import com.tecknobit.equinoxcore.time.TimeFormatter.EUROPEAN_DATE_PATTERN
import com.tecknobit.equinoxcore.time.TimeFormatter.H24_HOURS_MINUTES_SECONDS_PATTERN
import com.tecknobit.equinoxcore.time.TimeFormatter.toDateString
import com.tecknobit.glider.ui.components.DisconnectDevice
import com.tecknobit.glider.ui.icons.Globe
import com.tecknobit.glider.ui.screens.account.data.ConnectedDevice
import com.tecknobit.glider.ui.screens.account.presentation.AccountScreenViewModel
import com.tecknobit.glider.ui.theme.applyDarkTheme
import com.tecknobit.glidercore.enums.ConnectedDeviceType.MOBILE
import com.tecknobit.glidercore.enums.ConnectedDeviceType.WEB
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.current_device
import glider.composeapp.generated.resources.last_login
import glider.composeapp.generated.resources.with_the_browser
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
@NonRestartableComposable
fun ConnectedDeviceItem(
    viewModel: AccountScreenViewModel,
    connectedDevice: ConnectedDevice,
    isLast: Boolean,
) {
    val isTheCurrentDevice = connectedDevice.isTheCurrentDevice()
    ListItem(
        leadingContent = {
            DeviceIcon(
                device = connectedDevice
            )
        },
        overlineContent = {
            Text(
                text = connectedDevice.brand,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        headlineContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = connectedDevice.model,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (isTheCurrentDevice)
                    CurrentDeviceBadge()
            }
        },
        supportingContent = {
            Text(
                text = stringResource(
                    resource = Res.string.last_login,
                    connectedDevice.lastLogin.toDateString(
                        pattern = EUROPEAN_DATE_PATTERN
                    ),
                    connectedDevice.lastLogin.toDateString(
                        pattern = H24_HOURS_MINUTES_SECONDS_PATTERN
                    )
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        },
        trailingContent = if (!isTheCurrentDevice) {
            {
                val disconnectDevice = remember { mutableStateOf(false) }
                IconButton(
                    onClick = { disconnectDevice.value = true }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = null,
                        tint = if (applyDarkTheme())
                            MaterialTheme.colorScheme.onError
                        else
                            MaterialTheme.colorScheme.error
                    )
                }
                DisconnectDevice(
                    viewModel = viewModel,
                    show = disconnectDevice,
                    device = connectedDevice
                )
            }
        } else
            null
    )
    if (!isLast)
        DevicesDivider()
}

@Composable
@NonRestartableComposable
private fun DeviceIcon(
    device: ConnectedDevice,
) {
    if (device.type == WEB) {
        val state = rememberTooltipState()
        val scope = rememberCoroutineScope()
        TooltipBox(
            positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
            state = state,
            tooltip = {
                RichTooltip {
                    Text(
                        text = stringResource(
                            resource = Res.string.with_the_browser,
                            device.browser!!
                        )
                    )
                }
            }
        ) {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        scope.launch {
                            state.show()
                        }
                    },
                imageVector = Globe,
                contentDescription = "Device type"
            )
        }
    } else {
        Icon(
            imageVector = when (device.type) {
                MOBILE -> Icons.Default.Smartphone
                else -> Icons.Default.DesktopWindows
            },
            contentDescription = "Device type"
        )
    }
}

@Composable
@NonRestartableComposable
private fun CurrentDeviceBadge() {
    Text(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    size = 8.dp
                )
            )
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(
                vertical = 1.dp,
                horizontal = 4.dp
            ),
        text = stringResource(Res.string.current_device),
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
@NonRestartableComposable
private fun DevicesDivider() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(
                    fraction = responsiveAssignment(
                        onExpandedSizeClass = { 0.5f },
                        onMediumSizeClass = { 0.5f },
                        onCompactSizeClass = { 0.5f }
                    )
                )
        )
    }
}