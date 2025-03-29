package com.tecknobit.glider.ui.shared.presentations

import androidx.compose.runtime.mutableStateListOf
import com.tecknobit.equinoxcore.annotations.Structure
import com.tecknobit.equinoxcore.time.TimeFormatter
import com.tecknobit.glider.ui.screens.keychain.data.Password
import com.tecknobit.glidercore.enums.PasswordType.GENERATED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

@Structure
abstract class EditPasswordFormViewModel(
    private val passwordId: String?,
) : PasswordFormViewModel() {

    protected val _password = MutableStateFlow<Password?>(
        value = null
    )
    val password = _password.asStateFlow()

    fun retrievePassword() {
        // TODO: MAKE THE REQUEST THEN
        _password.value = Password(
            id = Random.nextLong().toString(),
            creationDate = TimeFormatter.currentTimestamp(),
            _password = "q3K6S;r{,Tn8Ab6wfpVRnx-((\\ARYSb'",
            tail = "Password #1",
            _scopes = "",
            type = GENERATED,
            _events = mutableStateListOf()
        )
    }

}