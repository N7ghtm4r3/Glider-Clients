package com.tecknobit.glider.ui.screens.keychain.data

import com.tecknobit.glidercore.EVENT_DATE_KEY
import com.tecknobit.glidercore.enums.PasswordEventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PasswordEvent(
    val id: String,
    @SerialName(EVENT_DATE_KEY)
    val eventDate: Long,
    val type: PasswordEventType,
)
