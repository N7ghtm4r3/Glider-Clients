package com.tecknobit.glider.ui.screens.keychain.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecknobit.equinoxcompose.session.Retriever
import com.tecknobit.equinoxcompose.session.setHasBeenDisconnectedValue
import com.tecknobit.equinoxcompose.session.setServerOfflineValue
import com.tecknobit.equinoxcompose.utilities.copyOnClipboard
import com.tecknobit.equinoxcompose.viewmodels.EquinoxViewModel
import com.tecknobit.equinoxcore.network.Requester.Companion.toResponseContent
import com.tecknobit.equinoxcore.network.sendPaginatedRequest
import com.tecknobit.equinoxcore.network.sendRequest
import com.tecknobit.equinoxcore.pagination.PaginatedResponse
import com.tecknobit.glider.requester
import com.tecknobit.glider.ui.screens.keychain.data.Password
import com.tecknobit.glidercore.enums.PasswordType
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.password_copied
import io.github.ahmad_hamwi.compose.pagination.PaginationState
import kotlinx.coroutines.launch

/**
 * The `KeychainScreenViewModel` class is the support class used by
 * [com.tecknobit.glider.ui.screens.keychain.presenter.KeychainScreenTab] to display and manage the
 * passwords owned by the [com.tecknobit.glider.localUser]
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see ViewModel
 * @see Retriever.RetrieverWrapper
 *
 */
class KeychainScreenViewModel : EquinoxViewModel(
    snackbarHostState = SnackbarHostState()
) {

    /**
     *`keywords` the keywords used to filter the [passwordsState] result
     */
    lateinit var keywords: MutableState<String>

    /**
     *`passwordTypes` the types of the password to filter the [passwordsState] result
     */
    lateinit var passwordTypes: SnapshotStateList<PasswordType>

    /**
     *`passwordsState` the state used to handle the pagination of the passwords list
     */
    val passwordsState = PaginationState<Int, Password>(
        initialPageKey = PaginatedResponse.DEFAULT_PAGE,
        onRequestPage = { page ->
            loadPasswords(
                page = page
            )
        }
    )

    /**
     * Method used to load and retrieve the passwords to append to the [passwordsState]
     *
     * @param page The page to request
     */
    private fun loadPasswords(
        page: Int,
    ) {
        viewModelScope.launch {
            requester.sendPaginatedRequest(
                request = {
                    getKeychain(
                        page = page,
                        keywords = keywords.value,
                        types = passwordTypes
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

    /**
     * Method used to copy and notify the event
     *
     * @param password The password to copy
     */
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
                        content = password.password.value,
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

    /**
     * Method used to request the password refreshing
     *
     * @param password The password to refresh
     * @param onRefresh The callback to invoke after the password refreshed
     */
    fun refreshPassword(
        password: Password,
        onRefresh: () -> Unit,
    ) {
        viewModelScope.launch {
            requester.sendRequest(
                request = {
                    refreshPassword(
                        password = password
                    )
                },
                onSuccess = {
                    password.refreshPassword(
                        refreshedPassword = it.toResponseContent()
                    )
                    onRefresh()
                },
                onFailure = { showSnackbarMessage(it) }
            )
        }
    }

    /**
     * Method used to request the password deletion
     *
     * @param password The password to refresh
     * @param onDelete The callback to invoke after the password delete
     */
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