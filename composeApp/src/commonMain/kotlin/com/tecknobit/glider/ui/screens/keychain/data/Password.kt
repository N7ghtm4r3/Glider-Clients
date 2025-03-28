package com.tecknobit.glider.ui.screens.keychain.data

import com.tecknobit.glidercore.CREATION_DATE_KEY
import com.tecknobit.glidercore.SCOPES_KEY
import com.tecknobit.glidercore.enums.PasswordType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Password(
    val id: String,
    @SerialName(CREATION_DATE_KEY)
    val creationDate: Long,
    val tail: String,
    val password: String,
    @SerialName(SCOPES_KEY)
    private val _scopes: String,
    val type: PasswordType,
    val events: List<PasswordEvent>,
) {

    @Transient
    val scopes = if (_scopes.isBlank())
        emptySet()
    else
        _scopes.split(",").toSet()

    fun hasScopes(): Boolean {
        return scopes.isNotEmpty()
    }

}