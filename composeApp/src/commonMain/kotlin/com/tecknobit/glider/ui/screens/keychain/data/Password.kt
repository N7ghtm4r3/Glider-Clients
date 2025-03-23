package com.tecknobit.glider.ui.screens.keychain.data

import com.tecknobit.glidercore.enums.PasswordStatus
import com.tecknobit.glidercore.enums.PasswordType
import kotlinx.serialization.Serializable

@Serializable
data class Password(
    val id: String,
    val tail: String,
    val password: String,
    val scopes: List<String> = emptyList(),
    val type: PasswordType,
    val status: PasswordStatus,
)
