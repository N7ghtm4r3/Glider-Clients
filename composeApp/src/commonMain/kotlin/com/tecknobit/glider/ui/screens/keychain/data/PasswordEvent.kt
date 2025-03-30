package com.tecknobit.glider.ui.screens.keychain.data

import com.tecknobit.glidercore.EVENT_DATE_KEY
import com.tecknobit.glidercore.enums.PasswordEventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The `PasswordEvent` data class represents an event related to password's lifecycle
 *
 * @property id The identifier of the event
 * @property eventDate When the event occurred
 * @property type The type of the event
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 */
@Serializable
data class PasswordEvent(
    val id: String,
    @SerialName(EVENT_DATE_KEY)
    val eventDate: Long,
    val type: PasswordEventType,
)
