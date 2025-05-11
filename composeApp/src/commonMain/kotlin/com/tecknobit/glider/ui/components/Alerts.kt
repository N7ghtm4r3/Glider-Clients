package com.tecknobit.glider.ui.components

import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecknobit.equinoxcompose.components.EquinoxAlertDialog
import com.tecknobit.glider.SPLASHSCREEN
import com.tecknobit.glider.displayFontFamily
import com.tecknobit.glider.navigator
import com.tecknobit.glider.ui.screens.account.data.ConnectedDevice
import com.tecknobit.glider.ui.screens.account.presentation.AccountScreenViewModel
import com.tecknobit.glider.ui.screens.keychain.data.Password
import com.tecknobit.glider.ui.screens.keychain.presentation.KeychainScreenViewModel
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.delete
import glider.composeapp.generated.resources.delete_password
import glider.composeapp.generated.resources.delete_password_warn_text
import glider.composeapp.generated.resources.delete_warn_text
import glider.composeapp.generated.resources.disconnect_device
import glider.composeapp.generated.resources.disconnect_device_text
import glider.composeapp.generated.resources.logout
import glider.composeapp.generated.resources.logout_warn_text
import glider.composeapp.generated.resources.refresh_password
import glider.composeapp.generated.resources.refresh_password_warn_text

/**
 * `titleStyle` -> the style to apply to the title of the [EquinoxAlertDialog]
 */
val titleStyle = TextStyle(
    fontFamily = displayFontFamily,
    fontSize = 18.sp
)

/**
 * Alert to warn about the password refreshing
 *
 * @param viewModel The support viewmodel for the screen
 * @param show Whether the alert is shown
 * @param password The password to refresh
 */
@Composable
fun RefreshPassword(
    viewModel: KeychainScreenViewModel,
    show: MutableState<Boolean>,
    password: Password,
) {
    EquinoxAlertDialog(
        icon = Icons.Default.Refresh,
        modifier = Modifier
            .widthIn(
                max = 400.dp
            ),
        viewModel = viewModel,
        show = show,
        title = Res.string.refresh_password,
        titleStyle = titleStyle,
        text = Res.string.refresh_password_warn_text,
        confirmAction = {
            viewModel.refreshPassword(
                password = password,
                onRefresh = { show.value = false }
            )
        }
    )
}

/**
 * Alert to warn about the password deletion
 *
 * @param viewModel The support viewmodel for the screen
 * @param show Whether the alert is shown
 * @param password The password to delete
 */
@Composable
fun DeletePassword(
    viewModel: KeychainScreenViewModel,
    show: MutableState<Boolean>,
    password: Password,
) {
    EquinoxAlertDialog(
        icon = Icons.Default.Delete,
        modifier = Modifier
            .widthIn(
                max = 400.dp
            ),
        viewModel = viewModel,
        show = show,
        title = Res.string.delete_password,
        titleStyle = titleStyle,
        text = Res.string.delete_password_warn_text,
        confirmAction = {
            viewModel.deletePassword(
                password = password,
                onDelete = { show.value = false }
            )
        }
    )
}

/**
 * Alert to warn about the device disconnection action
 *
 * @param viewModel The support viewmodel for the screen
 * @param show Whether the alert is shown
 * @param device The device to disconnect
 */
@Composable
fun DisconnectDevice(
    viewModel: AccountScreenViewModel,
    show: MutableState<Boolean>,
    device: ConnectedDevice,
) {
    EquinoxAlertDialog(
        icon = Icons.AutoMirrored.Filled.Logout,
        modifier = Modifier
            .widthIn(
                max = 400.dp
            ),
        viewModel = viewModel,
        show = show,
        title = Res.string.disconnect_device,
        titleStyle = titleStyle,
        text = Res.string.disconnect_device_text,
        confirmAction = {
            viewModel.disconnectDevice(
                device = device,
                onDisconnected = { show.value = false }
            )
        }
    )
}

/**
 * Alert to warn about the logout action
 *
 * @param viewModel The support viewmodel for the screen
 * @param show Whether the alert is shown
 */
@Composable
fun Logout(
    viewModel: AccountScreenViewModel,
    show: MutableState<Boolean>,
) {
    EquinoxAlertDialog(
        icon = Icons.AutoMirrored.Filled.Logout,
        modifier = Modifier
            .widthIn(
                max = 400.dp
            ),
        viewModel = viewModel,
        show = show,
        title = Res.string.logout,
        titleStyle = titleStyle,
        text = Res.string.logout_warn_text,
        confirmAction = {
            viewModel.clearSession {
                navigator.navigate(SPLASHSCREEN)
            }
        }
    )
}

/**
 * Alert to warn about the account deletion
 *
 * @param viewModel The support viewmodel for the screen
 * @param show Whether the alert is shown
 */
@Composable
fun DeleteAccount(
    viewModel: AccountScreenViewModel,
    show: MutableState<Boolean>,
) {
    EquinoxAlertDialog(
        icon = Icons.Default.Delete,
        modifier = Modifier
            .widthIn(
                max = 400.dp
            ),
        viewModel = viewModel,
        show = show,
        title = Res.string.delete,
        titleStyle = titleStyle,
        text = Res.string.delete_warn_text,
        confirmAction = {
            viewModel.deleteAccount {
                navigator.navigate(SPLASHSCREEN)
            }
        }
    )
}