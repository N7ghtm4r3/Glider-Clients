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

/**
 * The `Password` data class represents the password data
 *
 * @property id The identifier of the password
 * @property creationDate When the password has been created
 * @property tail The tail of the password
 * @property _password The value of the password
 * @property _scopes The scopes of the password
 * @property type The type of the password
 * @property _events The events of the password
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 */
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

    /**
     * `scopes` the scopes of the password
     */
    @Transient
    val scopes = if (_scopes.isBlank())
        emptySet()
    else
        _scopes.split(",").toSet()

    /**
     * `events` the events of the password
     */
    @Transient
    val events = _events.toMutableStateList()

    /**
     * `password` state container of the password value
     */
    @Transient
    val password = mutableStateOf(_password)

    /**
     * Method used to check whether the password has any scopes attached
     *
     * @return whether the password has any scopes attached as [Boolean]
     */
    fun hasScopes(): Boolean {
        return scopes.isNotEmpty()
    }

    /**
     * Method used to locally append the [PasswordEventType.COPIED] event
     */
    @Wrapper
    fun appendCopiedPasswordEvent() {
        appendPasswordEvent(
            eventType = COPIED
        )
    }

    /**
     * Method used to locally append the [PasswordEventType.REFRESHED] event
     */
    @Wrapper
    fun appendRefreshedPasswordEvent() {
        appendPasswordEvent(
            eventType = REFRESHED
        )
    }

    /**
     * Method used to locally append an event related to the password
     *
     * @param eventType The type of the event to append
     */
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

    /**
     * Method used to locally refresh the [password] value
     *
     * @param refreshedPassword The new value of the refreshed password
     */
    fun refreshPassword(
        refreshedPassword: String,
    ) {
        password.value = refreshedPassword
        appendRefreshedPasswordEvent()
    }

}