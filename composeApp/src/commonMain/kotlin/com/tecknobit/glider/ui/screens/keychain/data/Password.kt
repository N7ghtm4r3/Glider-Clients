package com.tecknobit.glider.ui.screens.keychain.data

import com.tecknobit.glidercore.CREATION_DATE_KEY
import com.tecknobit.glidercore.enums.PasswordType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Password(
    val id: String,
    @SerialName(CREATION_DATE_KEY)
    val creationDate: Long,
    val tail: String,
    val password: String,
    val scopes: Set<String> = emptySet(),
    val type: PasswordType,
) {

    fun hasScopes(): Boolean {
        return scopes.isNotEmpty()
    }

}