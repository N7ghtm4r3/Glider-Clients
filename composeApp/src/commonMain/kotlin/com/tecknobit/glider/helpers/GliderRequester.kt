package com.tecknobit.glider.helpers

import com.tecknobit.equinoxcompose.network.EquinoxRequester
import com.tecknobit.equinoxcore.annotations.CustomParametersOrder
import com.tecknobit.equinoxcore.annotations.RequestPath
import com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.Companion.DYNAMIC_ACCOUNT_DATA_ENDPOINT
import com.tecknobit.equinoxcore.network.RequestMethod.GET
import com.tecknobit.equinoxcore.pagination.PaginatedResponse.Companion.DEFAULT_PAGE_SIZE
import com.tecknobit.glider.localUser
import com.tecknobit.glider.ui.screens.account.data.ConnectedDevice
import com.tecknobit.glidercore.DEVICES_KEY
import com.tecknobit.glidercore.DEVICE_IDENTIFIER_KEY
import com.tecknobit.glidercore.DEVICE_KEY
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject

class GliderRequester(
    host: String,
    userId: String?,
    var deviceId: String?,
    userToken: String?,
    debugMode: Boolean = false,
) : EquinoxRequester(
    host = host,
    userId = userId,
    userToken = userToken,
    debugMode = debugMode,
    byPassSSLValidation = true,
    connectionErrorMessage = "Server temporary unavailable"
) {

    private val deviceIdHeader: MutableMap<String, String> = mutableMapOf()

    init {
        deviceId?.let {
            deviceIdHeader[DEVICE_IDENTIFIER_KEY] = deviceId!!
        }
    }

    fun setLocalUserDeviceId() {
        deviceIdHeader[DEVICE_IDENTIFIER_KEY] = localUser.deviceId!!
    }

    @CustomParametersOrder(DEVICE_KEY)
    override fun getSignUpPayload(
        serverSecret: String,
        name: String,
        surname: String,
        email: String,
        password: String,
        language: String,
        vararg custom: Any?,
    ): JsonObject {
        val payload = super.getSignUpPayload(
            serverSecret = serverSecret,
            name = name,
            surname = surname,
            email = email,
            password = password,
            language = language,
            custom = custom
        ).toMutableMap()
        // TODO: USE DIRECTLY THE JsonObject WHEN FIXED
        payload[DEVICE_KEY] = Json.encodeToJsonElement((custom[0] as JsonObject).toString())
        return Json.encodeToJsonElement(payload).jsonObject
    }

    @CustomParametersOrder(DEVICE_KEY)
    override fun getSignInPayload(
        email: String,
        password: String,
        vararg custom: Any?,
    ): JsonObject {
        val payload = super.getSignInPayload(
            email = email,
            password = password,
            custom = custom
        ).toMutableMap()
        // TODO: USE DIRECTLY THE JsonObject WHEN FIXED
        payload[DEVICE_KEY] = Json.encodeToJsonElement((custom[0] as JsonObject).toString())
        return Json.encodeToJsonElement(payload).jsonObject
    }

    /**
     * Method to request the dynamic data of the user
     *
     * @return the result of the request as [JsonObject]
     */
    @RequestPath(path = "/api/v1/users/{id}/dynamicAccountData", method = GET)
    suspend fun getCustomDynamicAccountData(): JsonObject {
        return execGet(
            endpoint = assembleUsersEndpointPath(
                endpoint = DYNAMIC_ACCOUNT_DATA_ENDPOINT
            ),
            headers = deviceIdHeader
        )
    }

    suspend fun getConnectedDevices(
        page: Int,
        pageSize: Int = DEFAULT_PAGE_SIZE,
    ): JsonObject {
        return execGet(
            endpoint = assembleUsersEndpointPath(
                endpoint = "/$DEVICES_KEY"
            ),
            headers = deviceIdHeader,
            query = createPaginationQuery(
                page = page,
                pageSize = pageSize
            )
        )
    }

    suspend fun disconnectDevice(
        device: ConnectedDevice,
    ): JsonObject {
        return disconnectDevice(
            deviceId = device.id
        )
    }

    suspend fun disconnectDevice(
        deviceId: String,
    ): JsonObject {
        return execDelete(
            endpoint = assembleUsersEndpointPath(
                endpoint = "/$DEVICES_KEY/$deviceId"
            ),
            headers = deviceIdHeader
        )
    }

}