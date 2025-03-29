package com.tecknobit.glider.ui.shared.data

import com.tecknobit.glidercore.SCOPES_KEY
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class PasswordDetails(
    val id: String,
    val tail: String,
    @SerialName(SCOPES_KEY)
    private val _scopes: String,
    val password: String? = null,
) {

    @Transient
    val scopes = if (_scopes.isBlank())
        emptySet()
    else
        _scopes.split(",").toSet()

}