package com.tecknobit.glider.ui.screens.account.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.viewModelScope
import com.tecknobit.equinoxcompose.viewmodels.EquinoxProfileViewModel
import com.tecknobit.equinoxcore.network.Requester.Companion.sendPaginatedRequest
import com.tecknobit.equinoxcore.network.Requester.Companion.sendRequest
import com.tecknobit.equinoxcore.pagination.PaginatedResponse.Companion.DEFAULT_PAGE
import com.tecknobit.glider.localUser
import com.tecknobit.glider.requester
import com.tecknobit.glider.ui.screens.account.data.ConnectedDevice
import io.github.ahmad_hamwi.compose.pagination.PaginationState
import kotlinx.coroutines.launch

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
        viewModelScope.launch {
            requester.sendPaginatedRequest(
                request = {
                    getConnectedDevices(
                        page = page
                    )
                },
                serializer = ConnectedDevice.serializer(),
                onSuccess = { paginatedResponse ->
                    connectedDevicesState.appendPage(
                        items = paginatedResponse.data,
                        nextPageKey = paginatedResponse.nextPage,
                        isLastPage = paginatedResponse.isLastPage
                    )
                },
                onFailure = { showSnackbarMessage(it) }
            )
        }
    }

    fun disconnectDevice(
        device: ConnectedDevice,
        onDisconnected: () -> Unit,
    ) {
        viewModelScope.launch {
            requester.sendRequest(
                request = {
                    disconnectDevice(
                        device = device
                    )
                },
                onSuccess = {
                    connectedDevicesState.refresh()
                    onDisconnected()
                },
                onFailure = { showSnackbarMessage(it) }
            )
        }
    }

    // TODO: TO REMOVE AND OVERRIDE INSTEAD THE clearSession PREBUILT METHOD
    fun logout(
        onClear: (() -> Unit)? = null,
    ) {
        viewModelScope.launch {
            requester.sendRequest(
                request = {
                    disconnectDevice(
                        deviceId = localUser.deviceId!!
                    )
                },
                onSuccess = {
                    localUser.clear()
                    requester.setUserCredentials(
                        userId = null,
                        userToken = null
                    )
                    requester.deviceId = null
                    onClear?.invoke()
                },
                onFailure = { showSnackbarMessage(it) }
            )
        }
    }

}