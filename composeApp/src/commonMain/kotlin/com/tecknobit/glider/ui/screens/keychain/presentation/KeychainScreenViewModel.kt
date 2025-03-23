package com.tecknobit.glider.ui.screens.keychain.presentation

import androidx.compose.material3.SnackbarHostState
import com.tecknobit.equinoxcompose.viewmodels.EquinoxViewModel
import com.tecknobit.equinoxcore.pagination.PaginatedResponse
import com.tecknobit.glider.ui.screens.keychain.data.Password
import com.tecknobit.glidercore.enums.PasswordStatus
import com.tecknobit.glidercore.enums.PasswordType
import io.github.ahmad_hamwi.compose.pagination.PaginationState
import kotlin.random.Random

class KeychainScreenViewModel : EquinoxViewModel(
    snackbarHostState = SnackbarHostState()
) {

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
            items = listOf(
                Password(
                    id = Random.nextLong().toString(),
                    password = "q3K6S;r{,Tn8Ab6wfpVRnx-((\\ARYSb'",
                    tail = "Password #1",
                    status = PasswordStatus.ACTIVE,
                    type = PasswordType.GENERATED
                ),
                Password(
                    id = Random.nextLong().toString(),
                    password = "q3K6S;r{,Tn8Ab6wfpVRnx-((\\ARYSb'",
                    tail = "Password #2",
                    status = PasswordStatus.ACTIVE,
                    type = PasswordType.GENERATED
                ),
                Password(
                    id = Random.nextLong().toString(),
                    password = "q3K6S;r{,Tn8Ab6wfpVRnx-((\\ARYSb'",
                    tail = "Password #3",
                    status = PasswordStatus.ACTIVE,
                    type = PasswordType.GENERATED
                ),
            ), // TODO: TO USE THE REAL DATA
            nextPageKey = page + 1, // TODO: TO USE THE REAL DATA
            isLastPage = Random.nextBoolean() // TODO: TO USE THE REAL DATA
        )
    }

}