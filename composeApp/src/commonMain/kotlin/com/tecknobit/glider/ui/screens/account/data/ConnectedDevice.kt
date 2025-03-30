package com.tecknobit.glider.ui.screens.account.data

import com.tecknobit.glider.localUser
import com.tecknobit.glidercore.LAST_LOGIN_KEY
import com.tecknobit.glidercore.enums.ConnectedDeviceType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The `ConnectedDevice` data class represents the information of a connected device
 *
 * @property id The identifier of the device
 * @property brand The brand of the device
 * @property model The model of the device
 * @property browser The browser used to connect to a session
 * @property lastLogin The last time the device connected to the session
 * @property type The type of the device
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 */
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

    /**
     * Method used to check whether the current device matches the [id] property
     *
     * @return whether the current device matches the [id] property as [Boolean]
     */
    fun isTheCurrentDevice(): Boolean {
        return localUser.deviceId == id
    }

}