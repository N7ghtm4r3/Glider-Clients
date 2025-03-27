package com.tecknobit.glider.ui.screens.account.presentation

import androidx.compose.material3.SnackbarHostState
import com.tecknobit.equinoxcompose.viewmodels.EquinoxProfileViewModel
import com.tecknobit.equinoxcore.pagination.PaginatedResponse.Companion.DEFAULT_PAGE
import com.tecknobit.equinoxcore.time.TimeFormatter
import com.tecknobit.glider.localUser
import com.tecknobit.glider.requester
import com.tecknobit.glider.ui.screens.account.data.ConnectedDevice
import com.tecknobit.glidercore.enums.ConnectedDeviceType
import io.github.ahmad_hamwi.compose.pagination.PaginationState
import kotlin.random.Random

class AccountScreenViewModel : EquinoxProfileViewModel(
    snackbarHostState = SnackbarHostState(),
    requester = requester,
    localUser = localUser
) {

    val connectedDevicesState = PaginationState<Int, ConnectedDevice>(
        initialPageKey = DEFAULT_PAGE,
        onRequestPage = { page ->
            loadDevices(
                page = page
            )
        }
    )

    private fun loadDevices(
        page: Int,
    ) {
        // TODO: TO MAKE THE REQUEST THEN
        connectedDevicesState.appendPage(
            items = listOf(
                ConnectedDevice(
                    id = Random.nextLong().toString(),
                    brand = "Oppo",
                    model = "11",
                    lastLogin = TimeFormatter.currentTimestamp(),
                    type = ConnectedDeviceType.MOBILE
                ),
                ConnectedDevice(
                    id = Random.nextLong().toString(),
                    brand = "Lenovo",
                    model = "Thinkpad",
                    lastLogin = TimeFormatter.currentTimestamp(),
                    type = ConnectedDeviceType.DESKTOP
                ),
                ConnectedDevice(
                    id = Random.nextLong().toString(),
                    brand = "Lenovo",
                    model = "Thinkpad",
                    browser = "Chrome",
                    lastLogin = TimeFormatter.currentTimestamp(),
                    type = ConnectedDeviceType.WEB
                )
            ), // TODO: TO USE THE REAL DATA
            nextPageKey = page + 1, // TODO: TO USE THE REAL DATA
            isLastPage = Random.nextBoolean() // TODO: TO USE THE REAL DATA
        )
    }

    fun disconnectDevice(
        device: ConnectedDevice,
        onDisconnected: () -> Unit,
    ) {
        // TODO: MAKE THE REQUEST THEN
        connectedDevicesState.refresh()
        onDisconnected()
    }

}