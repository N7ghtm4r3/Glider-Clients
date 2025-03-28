package com.tecknobit.glider.ui.screens.keychain.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import com.tecknobit.equinoxcompose.session.setHasBeenDisconnectedValue
import com.tecknobit.equinoxcompose.session.setServerOfflineValue
import com.tecknobit.equinoxcompose.utilities.copyOnClipboard
import com.tecknobit.equinoxcompose.viewmodels.EquinoxViewModel
import com.tecknobit.equinoxcore.network.Requester.Companion.sendPaginatedRequest
import com.tecknobit.equinoxcore.network.Requester.Companion.sendRequest
import com.tecknobit.equinoxcore.pagination.PaginatedResponse
import com.tecknobit.glider.requester
import com.tecknobit.glider.ui.screens.keychain.data.Password
import com.tecknobit.glidercore.enums.PasswordType
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.password_copied
import io.github.ahmad_hamwi.compose.pagination.PaginationState
import kotlinx.coroutines.launch

class KeychainScreenViewModel : EquinoxViewModel(
    snackbarHostState = SnackbarHostState()
) {

    lateinit var keywords: MutableState<String>

    lateinit var passwordTypes: SnapshotStateList<PasswordType>

    val passwordsState = PaginationState<Int, Password>(
        initialPageKey = PaginatedResponse.DEFAULT_PAGE,
        onRequestPage = { page ->
            loadPasswords(
                page = page
            )
        }
    )

    private fun loadPasswords(
        page: Int,
    ) {
        viewModelScope.launch {
            requester.sendPaginatedRequest(
                request = {
                    getKeychain(
                        page = page,
                        keywords = keywords.value,
                        passwordTypes = passwordTypes
                    )
                },
                serializer = Password.serializer(),
                onSuccess = { paginatedResponse ->
                    setServerOfflineValue(false)
                    passwordsState.appendPage(
                        items = paginatedResponse.data,
                        nextPageKey = paginatedResponse.nextPage,
                        isLastPage = paginatedResponse.isLastPage
                    )
                },
                onFailure = { setHasBeenDisconnectedValue(true) },
                onConnectionError = { setServerOfflineValue(true) }
            )
        }
    }

    fun copy(
        password: Password,
    ) {
        viewModelScope.launch {
            requester.sendRequest(
                request = {
                    copyPassword(
                        password = password
                    )
                },
                onSuccess = {
                    copyOnClipboard(
                        content = password.password,
                        onCopy = {
                            password.appendCopiedPasswordEvent()
                            showSnackbarMessage(
                                message = Res.string.password_copied
                            )
                        }
                    )
                },
                onFailure = { showSnackbarMessage(it) }
            )
        }
    }

    fun refreshPassword(
        password: Password,
        onRefresh: () -> Unit,
    ) {
        // TODO: MAKE THE REQUEST THEN
        onRefresh()
        passwordsState.refresh()
    }

    fun deletePassword(
        password: Password,
        onDelete: () -> Unit,
    ) {
        viewModelScope.launch {
            requester.sendRequest(
                request = {
                    deletePassword(
                        password = password
                    )
                },
                onSuccess = {
                    onDelete()
                    passwordsState.refresh()
                },
                onFailure = { showSnackbarMessage(it) }
            )
        }
    }

}