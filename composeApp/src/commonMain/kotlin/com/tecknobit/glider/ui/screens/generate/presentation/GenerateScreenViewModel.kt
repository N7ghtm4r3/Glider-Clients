package com.tecknobit.glider.ui.screens.generate.presentation

import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.tecknobit.glider.ui.screens.generate.components.QuantityPickerState
import com.tecknobit.glider.ui.shared.presentations.PasswordFormViewModel
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.password_generated
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class GenerateScreenViewModel(
    passwordId: String?,
) : PasswordFormViewModel(
    passwordId = passwordId
) {

    lateinit var quantityPickerState: QuantityPickerState

    lateinit var includeNumbers: MutableState<Boolean>

    lateinit var includeUppercaseLetters: MutableState<Boolean>

    lateinit var includeSpecialCharacters: MutableState<Boolean>

    private val _generatingPassword = MutableSharedFlow<Boolean>(
        replay = 1
    )
    val generatingPassword = _generatingPassword.asSharedFlow()

    fun generatePassword() {
        if (!validateForm())
            return
        viewModelScope.launch {
            // TODO: MAKE THE REQUEST THEN
            _generatingPassword.emit(true)
            delay(Random.nextInt(3) * 1000L) // TODO: TO REMOVE
            resetForm()
            _generatingPassword.emit(false)
        }
    }

    private fun resetForm() {
        tail.value = ""
        scopes.value = ""
        includeNumbers.value = true
        includeUppercaseLetters.value = true
        includeSpecialCharacters.value = true
        quantityPickerState.reset()
        showSnackbarMessage(
            message = Res.string.password_generated
        )
    }

}