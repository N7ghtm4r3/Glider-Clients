package com.tecknobit.glider.ui.screens.keychain.data

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import com.tecknobit.equinoxcore.annotations.Wrapper
import com.tecknobit.equinoxcore.helpers.PASSWORD_KEY
import com.tecknobit.equinoxcore.time.TimeFormatter
import com.tecknobit.glidercore.CREATION_DATE_KEY
import com.tecknobit.glidercore.EVENTS_KEY
import com.tecknobit.glidercore.SCOPES_KEY
import com.tecknobit.glidercore.enums.PasswordEventType
import com.tecknobit.glidercore.enums.PasswordEventType.COPIED
import com.tecknobit.glidercore.enums.PasswordEventType.REFRESHED
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
    @SerialName(PASSWORD_KEY)
    private val _password: String,
    @SerialName(SCOPES_KEY)
    private val _scopes: String,
    val type: PasswordType,
    @SerialName(EVENTS_KEY)
    private val _events: List<PasswordEvent>,
) {

    @Transient
    val scopes = if (_scopes.isBlank())
        emptySet()
    else
        _scopes.split(",").toSet()

    @Transient
    val events = _events.toMutableStateList()

    @Transient
    val password = mutableStateOf(_password)

    fun hasScopes(): Boolean {
        return scopes.isNotEmpty()
    }

    @Wrapper
    fun appendCopiedPasswordEvent() {
        appendPasswordEvent(
            eventType = COPIED
        )
    }

    @Wrapper
    fun appendRefreshedPasswordEvent() {
        appendPasswordEvent(
            eventType = REFRESHED
        )
    }

    private fun appendPasswordEvent(
        eventType: PasswordEventType,
    ) {
        val timestamp = TimeFormatter.currentTimestamp()
        events.add(
            index = 0,
            element = PasswordEvent(
                id = timestamp.toString(),
                eventDate = timestamp,
                type = eventType
            )
        )
    }

    fun refreshPassword(
        refreshedPassword: String,
    ) {
        password.value = refreshedPassword
        appendRefreshedPasswordEvent()
    }

}