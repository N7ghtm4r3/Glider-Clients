package com.tecknobit.glider.helpers

import com.tecknobit.equinoxcompose.session.EquinoxLocalUser
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.helpers.IDENTIFIER_KEY
import com.tecknobit.equinoxcore.json.treatsAsString
import com.tecknobit.glider.requester
import com.tecknobit.glidercore.DEVICE_IDENTIFIER_KEY
import kotlinx.serialization.json.JsonObject

/**
 * The `GliderLocalUser` class is useful to represent a user in the client application
 *
 * @author N7ghtm4r3 - Tecknobit
 */
class GliderLocalUser : EquinoxLocalUser(
    localStoragePath = "Glider"
) {

    /**
     * `deviceId` the identifier of the current device
     */
    var deviceId: String? = null
        set(value) {
            if (field != value) {
                setPreference(
                    key = DEVICE_IDENTIFIER_KEY,
                    value = value
                )
                field = value
            }
        }

    @RequiresSuperCall
    override fun insertNewUser(
        hostAddress: String,
        name: String,
        surname: String,
        email: String,
        password: String,
        language: String,
        response: JsonObject,
        vararg custom: Any?,
    ) {
        super.insertNewUser(
            hostAddress,
            name,
            surname,
            email,
            password,
            language,
            response,
            *custom
        )
        val device: JsonObject = custom.extractsCustomValue(
            itemPosition = 0
        )
        deviceId = device[IDENTIFIER_KEY].treatsAsString()
        requester.setLocalUserDeviceId()
    }

    @RequiresSuperCall
    override fun initLocalUser() {
        super.initLocalUser()
        deviceId = getPreference(DEVICE_IDENTIFIER_KEY)
    }

}