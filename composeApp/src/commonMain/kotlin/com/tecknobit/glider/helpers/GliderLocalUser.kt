package com.tecknobit.glider.helpers

import com.tecknobit.equinoxcompose.session.EquinoxLocalUser
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.helpers.IDENTIFIER_KEY
import com.tecknobit.equinoxcore.helpers.THEME_KEY
import com.tecknobit.equinoxcore.json.treatsAsString
import com.tecknobit.glider.GliderConfig.LOCAL_STORAGE_PATH
import com.tecknobit.glider.requester
import com.tecknobit.glidercore.DEVICE_IDENTIFIER_KEY
import kotlinx.serialization.json.JsonObject

/**
 * The `GliderLocalUser` class is useful to represent a user in the client application
 *
 * @author N7ghtm4r3 - Tecknobit
 */
class GliderLocalUser : EquinoxLocalUser(
    localStoragePath = LOCAL_STORAGE_PATH,
    observableKeys = setOf(THEME_KEY)
) {

    /**
     * `deviceId` The identifier of the current device
     */
    var deviceId: String? = null
        private set

    /**
     * Method to init the local user session
     */
    @RequiresSuperCall
    override fun initLocalUser() {
        super.initLocalUser()
        setPreference<String>(
            key = DEVICE_IDENTIFIER_KEY,
            prefInit = { deviceId ->
                this.deviceId = deviceId
            }
        )
    }

    /**
     * Method used to insert a new user and save locally his/her properties
     *
     * @param hostAddress The host address with which the user communicates
     * @param userId The identifier of the user
     * @param userToken The token of the user
     * @param profilePic The profile picture of the user
     * @param name The name of the user
     * @param surname The surname of the user
     * @param email The email of the user
     * @param language The language of the user
     * @param custom The custom parameters added during the customization of the [EquinoxLocalUser]
     */
    override fun insertNewUser(
        hostAddress: String,
        userId: String,
        userToken: String,
        profilePic: String,
        name: String,
        surname: String,
        email: String,
        language: String,
        vararg custom: Any?,
    ) {
        super.insertNewUser(
            hostAddress,
            userId,
            userToken,
            profilePic,
            name,
            surname,
            email,
            language,
            *custom
        )
        val device: JsonObject = custom.extractsCustomValue(
            itemPosition = 0
        )
        val deviceId = device[IDENTIFIER_KEY].treatsAsString()
        initDeviceId(
            deviceId = deviceId
        )
        requester.setLocalUserDeviceId()
    }

    /**
     * Method used to initialize the [deviceId] property and locally save
     *
     * @param deviceId The identifier of the current device
     */
    private fun initDeviceId(
        deviceId: String,
    ) {
        this.deviceId = deviceId
        savePreference(
            key = DEVICE_IDENTIFIER_KEY,
            value = deviceId
        )
    }

}