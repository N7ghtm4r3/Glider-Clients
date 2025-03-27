package com.tecknobit.glider.ui.screens.account.data

import com.tecknobit.glider.localUser
import com.tecknobit.glidercore.LAST_LOGIN_KEY
import com.tecknobit.glidercore.enums.ConnectedDeviceType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConnectedDevice(
    val id: String,
    val brand: String,
    val model: String,
    val browser: String? = null,
    @SerialName(LAST_LOGIN_KEY)
    val lastLogin: Long,
    val type: ConnectedDeviceType,
) {

    fun isTheCurrentDevice(): Boolean {
        return localUser.deviceId == id
    }

}