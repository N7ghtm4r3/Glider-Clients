package com.tecknobit.glider.ui.screens.keychain.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import com.tecknobit.equinoxcompose.utilities.copyOnClipboard
import com.tecknobit.equinoxcompose.viewmodels.EquinoxViewModel
import com.tecknobit.equinoxcore.pagination.PaginatedResponse
import com.tecknobit.equinoxcore.time.TimeFormatter
import com.tecknobit.glider.ui.screens.keychain.data.Password
import com.tecknobit.glider.ui.screens.keychain.data.PasswordEvent
import com.tecknobit.glidercore.enums.PasswordEventType
import com.tecknobit.glidercore.enums.PasswordType
import com.tecknobit.glidercore.enums.PasswordType.GENERATED
import com.tecknobit.glidercore.enums.PasswordType.INSERTED
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.password_copied
import io.github.ahmad_hamwi.compose.pagination.PaginationState
import kotlin.random.Random

class KeychainScreenViewModel : EquinoxViewModel(
    snackbarHostState = SnackbarHostState()
) {

    lateinit var keywords: MutableState<String>

    lateinit var includeGeneratedPasswords: MutableState<Boolean>

    lateinit var includeInsertedPasswords: MutableState<Boolean>

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
        // TODO: MAKE THE REQUEST THEN
        // TODO: TO APPLY THE FILTERS ALSO
        passwordsState.appendPage(
            items = if (Random.nextBoolean()) {
                listOf(
                    Password(
                        id = Random.nextLong().toString(),
                        creationDate = TimeFormatter.currentTimestamp(),
                        password = "q3K6S;r{,Tn8Ab6wfpVRnx-((\\ARYSb'",
                        tail = "Password #1",
                        scopes = setOf(
                            "tecknobit",
                            "mock",
                            "tecknobit1",
                            "tecknobit3",
                            "tecknobit5"
                        ),
                        type = GENERATED,
                        events = listOf(
                            PasswordEvent(
                                id = Random.nextLong().toString(),
                                eventDate = TimeFormatter.currentTimestamp(),
                                type = PasswordEventType.GENERATED
                            ),
                            PasswordEvent(
                                id = Random.nextLong().toString(),
                                eventDate = TimeFormatter.currentTimestamp(),
                                type = PasswordEventType.COPIED
                            ),
                            PasswordEvent(
                                id = Random.nextLong().toString(),
                                eventDate = TimeFormatter.currentTimestamp(),
                                type = PasswordEventType.EDITED
                            ),
                            PasswordEvent(
                                id = Random.nextLong().toString(),
                                eventDate = TimeFormatter.currentTimestamp(),
                                type = PasswordEventType.REFRESHED
                            ),
                        )
                    ),
                    Password(
                        id = Random.nextLong().toString(),
                        creationDate = TimeFormatter.currentTimestamp(),
                        password = "q3K6S;r{,Tn8Ab6wfpVRnx-((\\ARYSb'",
                        tail = "Password #2",
                        type = INSERTED,
                        events = listOf(
                            PasswordEvent(
                                id = Random.nextLong().toString(),
                                eventDate = TimeFormatter.currentTimestamp(),
                                type = PasswordEventType.INSERTED
                            ),
                            PasswordEvent(
                                id = Random.nextLong().toString(),
                                eventDate = TimeFormatter.currentTimestamp(),
                                type = PasswordEventType.COPIED
                            ),
                            PasswordEvent(
                                id = Random.nextLong().toString(),
                                eventDate = TimeFormatter.currentTimestamp(),
                                type = PasswordEventType.EDITED
                            ),
                            PasswordEvent(
                                id = Random.nextLong().toString(),
                                eventDate = TimeFormatter.currentTimestamp(),
                                type = PasswordEventType.REFRESHED
                            ),
                        )
                    ),
                    Password(
                        id = Random.nextLong().toString(),
                        creationDate = TimeFormatter.currentTimestamp(),
                        password = "q3K6S;r{,Tn8Ab6wfpVRnx-((\\ARYSb'",
                        tail = "Password #3",
                        type = GENERATED,
                        events = listOf(
                            PasswordEvent(
                                id = Random.nextLong().toString(),
                                eventDate = TimeFormatter.currentTimestamp(),
                                type = PasswordEventType.GENERATED
                            ),
                            PasswordEvent(
                                id = Random.nextLong().toString(),
                                eventDate = TimeFormatter.currentTimestamp(),
                                type = PasswordEventType.COPIED
                            ),
                            PasswordEvent(
                                id = Random.nextLong().toString(),
                                eventDate = TimeFormatter.currentTimestamp(),
                                type = PasswordEventType.EDITED
                            ),
                            PasswordEvent(
                                id = Random.nextLong().toString(),
                                eventDate = TimeFormatter.currentTimestamp(),
                                type = PasswordEventType.REFRESHED
                            ),
                        )
                    ),
                )
            } else
                emptyList(), // TODO: TO USE THE REAL DATA
            nextPageKey = page + 1, // TODO: TO USE THE REAL DATA
            isLastPage = Random.nextBoolean() // TODO: TO USE THE REAL DATA
        )
    }

    fun typeFilterApplied(
        type: PasswordType,
    ): Boolean {
        return when (type) {
            GENERATED -> includeGeneratedPasswords.value
            INSERTED -> includeInsertedPasswords.value
        }
    }

    fun applyTypeFilters(
        type: PasswordType,
    ) {
        when (type) {
            GENERATED -> includeGeneratedPasswords.value = !includeGeneratedPasswords.value
            INSERTED -> includeInsertedPasswords.value = !includeInsertedPasswords.value
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