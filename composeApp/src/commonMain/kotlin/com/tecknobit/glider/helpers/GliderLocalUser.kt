package com.tecknobit.glider.helpers

import com.tecknobit.equinoxcompose.session.EquinoxLocalUser
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.glidercore.DEVICE_IDENTIFIER_KEY
import kotlinx.serialization.json.JsonObject

class GliderLocalUser : EquinoxLocalUser(
    localStoragePath = "Glider"
) {

    var deviceId: String = ""
        set(value) {
            if (field != value) {
                setPreference(
                    key = DEVICE_IDENTIFIER_KEY,
                    value = value
                )
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
        deviceId = custom.extractsCustomValue(
            itemPosition = 0
        )
    }

    @RequiresSuperCall
    override fun initLocalUser() {
        super.initLocalUser()
        deviceId = getNullSafePreference(DEVICE_IDENTIFIER_KEY)
    }

}