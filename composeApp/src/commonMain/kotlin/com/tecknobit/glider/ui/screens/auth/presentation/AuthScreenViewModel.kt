package com.tecknobit.glider.ui.screens.auth.presentation

import androidx.compose.material3.SnackbarHostState
import com.tecknobit.equinoxcompose.viewmodels.EquinoxAuthViewModel
import com.tecknobit.equinoxcore.annotations.Assembler
import com.tecknobit.equinoxcore.annotations.CustomParametersOrder
import com.tecknobit.equinoxcore.annotations.RequiresSuperCall
import com.tecknobit.equinoxcore.helpers.IDENTIFIER_KEY
import com.tecknobit.glider.HOME_SCREEN
import com.tecknobit.glider.localUser
import com.tecknobit.glider.navigator
import com.tecknobit.glider.requester
import com.tecknobit.glidercore.BRAND_KEY
import com.tecknobit.glidercore.BROWSER_KEY
import com.tecknobit.glidercore.DEVICE_KEY
import com.tecknobit.glidercore.MODEL_KEY
import com.tecknobit.glidercore.TYPE_KEY
import com.tecknobit.glidercore.enums.ConnectedDeviceType
import com.tecknobit.glidercore.enums.ConnectedDeviceType.MOBILE
import com.tecknobit.kinfo.DevicePlatform.ANDROID
import com.tecknobit.kinfo.DevicePlatform.DESKTOP
import com.tecknobit.kinfo.DevicePlatform.IOS
import com.tecknobit.kinfo.DevicePlatform.WEB
import com.tecknobit.kinfo.KInfoState
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

/**
 * The **AuthScreenViewModel** class is the support class used to execute the authentication requests
 * to the backend
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see androidx.lifecycle.ViewModel
 * @see com.tecknobit.equinoxcompose.session.Retriever.RetrieverWrapper
 * @see EquinoxViewModel
 * @see EquinoxAuthViewModel
 */
class AuthScreenViewModel : EquinoxAuthViewModel(
    snackbarHostState = SnackbarHostState(),
    requester = requester,
    localUser = localUser
) {

    /**
     * Method used to get the list of the custom parameters to use in the [signUp] request
     *
     * The order of the custom parameters must be the same of that specified in your customization of the
     * [getSignUpValuesKeys()](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/java/com/tecknobit/equinox/environment/helpers/services/EquinoxUsersHelper.java#L133)
     * method
     */
    @CustomParametersOrder(DEVICE_KEY)
    override fun getSignUpCustomParameters(): Array<out Any?> {
        return arrayOf(getCurrentDeviceInformation())
    }

    /**
     * Method used to get the list of the custom parameters to use in the [signIn] request.
     *
     * The order of the custom parameters must be the same of that specified in your customization of the
     * [getSignUpValuesKeys()](https://github.com/N7ghtm4r3/Equinox/blob/main/src/main/java/com/tecknobit/equinox/environment/helpers/services/EquinoxUsersHelper.java#L133)
     * method
     *
     **/
    @CustomParametersOrder(DEVICE_KEY)
    override fun getSignInCustomParameters(): Array<out Any?> {
        return arrayOf(getCurrentDeviceInformation())
    }

    /**
     * Method used to retrieve the information of the current device
     *
     * @return the device information as [JsonObject]
     */
    private fun getCurrentDeviceInformation(): JsonObject {
        val state = KInfoState()
        return when (state.devicePlatform) {
            ANDROID -> {
                val info = state.androidInfo
                assembleDeviceInformationPayload(
                    type = MOBILE,
                    info.id, info.brand, info.model
                )
            }

            IOS -> {
                val info = state.iosInfo
                assembleDeviceInformationPayload(
                    type = MOBILE,
                    info.identifierForVendor, info.name, info.model
                )
            }

            DESKTOP -> {
                val info = state.desktopInfo
                val computerSystem = info.hardware.computerSystem
                // TODO: TO USE THE BUILT-IN CONSTANT
                val TO_BE_FILLED_BY_O_E_M = "To Be Filled By O.E.M."
                val operatingSystem = info.operatingSystem
                var brand = computerSystem.manufacturer
                if (brand == TO_BE_FILLED_BY_O_E_M)
                    brand = operatingSystem.manufacturer
                var model = computerSystem.model
                if (model == TO_BE_FILLED_BY_O_E_M)
                    model = operatingSystem.family
                assembleDeviceInformationPayload(
                    type = ConnectedDeviceType.DESKTOP,
                    computerSystem.hardwareUUID, brand, model
                )
            }

            WEB -> {
                val info = state.webInfo
                val device = info.device
                assembleDeviceInformationPayload(
                    type = ConnectedDeviceType.WEB,
                    info.userAgent, device.vendor, device.model, info.browser.name
                )
            }
        }
    }

    /**
     * Method used to assemble the payload with the device information
     *
     * @param type The type of the device
     * @param deviceInfo The retrieved information of the device
     */
    @Assembler
    @CustomParametersOrder(
        order = [IDENTIFIER_KEY, BRAND_KEY, MODEL_KEY, BROWSER_KEY]
    )
    private fun assembleDeviceInformationPayload(
        type: ConnectedDeviceType,
        vararg deviceInfo: String?,
    ): JsonObject {
        return buildJsonObject {
            put(IDENTIFIER_KEY, deviceInfo[0])
            put(BRAND_KEY, deviceInfo[1])
            put(MODEL_KEY, deviceInfo[2])
            if (deviceInfo.size > 3)
                put(BROWSER_KEY, deviceInfo[3])
            put(TYPE_KEY, type.name)
        }
    }

    /**
     * Method to launch the application after the authentication request, will be instantiated with the user details
     * both the [requester] and the [localUser]
     *
     * @param response The response of the authentication request
     * @param name The name of the user
     * @param surname The surname of the user
     * @param language The language of the user
     * @param custom The custom parameters added in a customization of the equinox user
     */
    @RequiresSuperCall
    override fun launchApp(
        response: JsonObject,
        name: String,
        surname: String,
        language: String,
        vararg custom: Any?,
    ) {
        super.launchApp(response, name, surname, language, *custom)
        navigator.navigate(HOME_SCREEN)
    }

}