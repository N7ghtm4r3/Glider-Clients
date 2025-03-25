package com.tecknobit.glider.ui.screens.account.data

import com.tecknobit.glidercore.enums.ConnectedDeviceType
import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class ConnectedDevice(
    val id: String,
    val brand: String,
    val model: String,
    val browser: String? = null,
    val lastLogin: Long,
    val type: ConnectedDeviceType,
) {

    fun isTheCurrentDevice(): Boolean {
        // TODO: TO USE KINFO INSTEAD
        return Random.nextBoolean()
    }

}