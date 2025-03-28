package com.tecknobit.glider.ui.screens.keychain.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import com.tecknobit.equinoxcompose.session.setServerOfflineValue
import com.tecknobit.equinoxcompose.utilities.copyOnClipboard
import com.tecknobit.equinoxcompose.viewmodels.EquinoxViewModel
import com.tecknobit.equinoxcore.network.Requester.Companion.sendPaginatedRequest
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
                onFailure = { /*setHasBeenDisconnectedValue(true)*/ },
                onConnectionError = { setServerOfflineValue(true) }
            )
        }
    }

    fun copy(
        password: Password,
    ) {
        // TODO: MAKE THE REQUEST TO REGISTER THE EVENT THEN
        copyOnClipboard(
            content = password.password,
            onCopy = {
                showSnackbarMessage(
                    message = Res.string.password_copied
                )
            }
        )
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
        // TODO: MAKE THE REQUEST THEN
        onDelete()
        passwordsState.refresh()
    }

}