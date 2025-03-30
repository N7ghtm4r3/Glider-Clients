package com.tecknobit.glider.ui.shared.data

import com.tecknobit.glidercore.SCOPES_KEY
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * The `PasswordDetails` data class represents the basic information of a password
 *
 * @property id The identifier of the password
 * @property tail The tail of the password
 * @property _scopes The scopes of the password
 * @property password The value of the password
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 */
@Serializable
data class PasswordDetails(
    val id: String,
    val tail: String,
    @SerialName(SCOPES_KEY)
    private val _scopes: String,
    val password: String? = null,
) {

    /**
     * `scopes` the scopes of the password
     */
    @Transient
    val scopes = if (_scopes.isBlank())
        emptySet()
    else
        _scopes.split(",").toSet()

}