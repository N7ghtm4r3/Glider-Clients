package com.tecknobit.glider.helpers

import com.tecknobit.ametistaengine.AmetistaEngine.Companion.ametistaEngine
import com.tecknobit.equinoxcompose.network.EquinoxRequester
import com.tecknobit.equinoxcore.annotations.Assembler
import com.tecknobit.equinoxcore.annotations.CustomParametersOrder
import com.tecknobit.equinoxcore.annotations.RequestPath
import com.tecknobit.equinoxcore.helpers.KEYWORDS_KEY
import com.tecknobit.equinoxcore.helpers.PASSWORD_KEY
import com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.Companion.DYNAMIC_ACCOUNT_DATA_ENDPOINT
import com.tecknobit.equinoxcore.network.RequestMethod.*
import com.tecknobit.equinoxcore.pagination.PaginatedResponse.Companion.DEFAULT_PAGE_SIZE
import com.tecknobit.equinoxcore.pagination.PaginatedResponse.Companion.PAGE_KEY
import com.tecknobit.equinoxcore.pagination.PaginatedResponse.Companion.PAGE_SIZE_KEY
import com.tecknobit.glider.localUser
import com.tecknobit.glider.ui.screens.account.data.ConnectedDevice
import com.tecknobit.glider.ui.screens.keychain.data.Password
import com.tecknobit.glidercore.DEVICES_KEY
import com.tecknobit.glidercore.DEVICE_IDENTIFIER_KEY
import com.tecknobit.glidercore.DEVICE_KEY
import com.tecknobit.glidercore.INCLUDE_NUMBERS_KEY
import com.tecknobit.glidercore.INCLUDE_SPECIAL_CHARACTERS_KEY
import com.tecknobit.glidercore.INCLUDE_UPPERCASE_LETTERS_KEY
import com.tecknobit.glidercore.PASSWORDS_KEY
import com.tecknobit.glidercore.PASSWORD_LENGTH_KEY
import com.tecknobit.glidercore.SCOPES_KEY
import com.tecknobit.glidercore.TAIL_KEY
import com.tecknobit.glidercore.TYPE_KEY
import com.tecknobit.glidercore.enums.PasswordType
import com.tecknobit.glidercore.helpers.GliderEndpointsSet.KEYCHAIN_ENDPOINT
import com.tecknobit.glidercore.helpers.GliderEndpointsSet.REFRESH_ENDPOINT
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.put

/**
 * The `GliderRequester` class is useful to communicate with Glider's backend
 *
 * @param host The host address where is running the backend
 * @param userId The user identifier
 * @param deviceId The identifier of the current device
 * @param userToken The user token
 * @param debugMode Whether the requester is still in development and who is developing needs the log
 * of the requester's workflow, if it is enabled all the details of the requests sent and the errors
 * occurred will be printed in the console
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @see EquinoxRequester
 */
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

    init {
        attachInterceptorOnRequest {
            ametistaEngine.notifyNetworkRequest()
        }
    }

    /**
     * `deviceIdHeader` the default headers map with the current [deviceId]
     */
    private val deviceIdHeader: MutableMap<String, String> = mutableMapOf()

    init {
        deviceId?.let {
            deviceIdHeader[DEVICE_IDENTIFIER_KEY] = deviceId!!
        }
    }

    /**
     * Method used to load the [deviceIdHeader] map with the current [localUser.deviceId]
     */
    fun setLocalUserDeviceId() {
        deviceIdHeader[DEVICE_IDENTIFIER_KEY] = localUser.deviceId!!
    }

    /**
     * Method to create the payload for the [signUp] request
     *
     * @param serverSecret The secret of the personal Equinox's backend
     * @param name The name of the user
     * @param surname The surname of the user
     * @param email The email of the user
     * @param password The password of the user
     * @param language The language of the user
     * @param custom The custom parameters added in a customization of the equinox user to execute a customized sign-up
     *
     * @return the payload for the request as [JsonObject]
     *
     */
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

    /**
     * Method to create the payload for the [signIn] request
     *
     * @param email The email of the user
     * @param password The password of the user
     * @param custom The custom parameters added in a customization of the equinox user to execute a customized sign-in
     *
     * @return the payload for the request as [JsonObject]
     *
     */
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
     * Request the dynamic data of the user
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

    /**
     * Request get the connected device of the user
     *
     * @param page     The page requested
     * @param pageSize The size of the items to insert in the page
     *
     * @return the result of the request as [JsonObject]
     */
    @RequestPath(path = "/api/v1/users/{id}/devices", method = GET)
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

    /**
     * Request to disconnect a device from the current session
     *
     * @param device The device to disconnect
     *
     * @return the result of the request as [JsonObject]
     */
    @RequestPath(path = "/api/v1/users/{id}/devices/{device_id}", method = DELETE)
    suspend fun disconnectDevice(
        device: ConnectedDevice,
    ): JsonObject {
        return disconnectDevice(
            deviceId = device.id
        )
    }

    /**
     * Request to disconnect a device from the current session
     *
     * @param deviceId The identifier of the device to disconnect
     *
     * @return the result of the request as [JsonObject]
     */
    @RequestPath(path = "/api/v1/users/{id}/devices/{device_id}", method = DELETE)
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

    /**
     * Request to generate a new password
     *
     * @param length The length of the password
     * @param tail The tail of the password
     * @param scopes The scopes of the password
     * @param length The length of the password
     * @param length The length of the password
     * @param includeNumbers           Whether the generated password must include the numbers
     * @param includeUppercaseLetters  Whether the generated password must include the uppercase letters
     * @param includeSpecialCharacters Whether the generated password must include the special characters
     *
     * @return the result of the request as [JsonObject]
     */
    @RequestPath(path = "/api/v1/users/{id}/passwords", method = PUT)
    suspend fun generatePassword(
        length: Int,
        tail: String,
        scopes: String,
        includeNumbers: Boolean,
        includeUppercaseLetters: Boolean,
        includeSpecialCharacters: Boolean,
    ): JsonObject {
        val payload = buildJsonObject {
            put(PASSWORD_LENGTH_KEY, length)
            put(TAIL_KEY, tail)
            put(SCOPES_KEY, scopes)
            put(INCLUDE_NUMBERS_KEY, includeNumbers)
            put(INCLUDE_UPPERCASE_LETTERS_KEY, includeUppercaseLetters)
            put(INCLUDE_SPECIAL_CHARACTERS_KEY, includeSpecialCharacters)
        }
        return execPut(
            endpoint = assemblePasswordsEndpoint(),
            headers = deviceIdHeader,
            payload = payload
        )
    }

    /**
     * Request to insert a new password
     *
     * @param tail The tail of the password
     * @param scopes The scopes of the password
     * @param password The value of the password to insert
     *
     * @return the result of the request as [JsonObject]
     */
    @RequestPath(path = "/api/v1/users/{id}/passwords", method = POST)
    suspend fun insertPassword(
        tail: String,
        scopes: String,
        password: String,
    ): JsonObject {
        val payload = buildJsonObject {
            put(TAIL_KEY, tail)
            put(SCOPES_KEY, scopes)
            put(PASSWORD_KEY, password)
        }
        return execPost(
            endpoint = assemblePasswordsEndpoint(),
            headers = deviceIdHeader,
            payload = payload
        )
    }

    /**
     * Request to retrieve the keychain owned by the user
     *
     * @param page      The page requested
     * @param pageSize  The size of the items to insert in the page
     * @param keywords The filter keywords
     * @param types The types of the passwords to retrieve
     *
     * @return the result of the request as [JsonObject]
     */
    @RequestPath(path = "/api/v1/users/{id}/passwords/keychain", method = GET)
    suspend fun getKeychain(
        page: Int,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        keywords: String,
        types: List<PasswordType>,
    ): JsonObject {
        val query = buildJsonObject {
            put(PAGE_KEY, page)
            put(PAGE_SIZE_KEY, pageSize)
            put(KEYWORDS_KEY, keywords)
            put(TYPE_KEY, types.joinToString())
        }
        return execGet(
            endpoint = assemblePasswordsEndpoint(
                subEndpoint = KEYCHAIN_ENDPOINT
            ),
            headers = deviceIdHeader,
            query = query
        )
    }

    /**
     * Request to retrieve a password owned by the user
     *
     * @param passwordId The identifier of the password
     *
     * @return the result of the request as [JsonObject]
     */
    @RequestPath(path = "/api/v1/users/{id}/passwords/{password_id}", method = GET)
    suspend fun getPassword(
        passwordId: String,
    ): JsonObject {
        return execGet(
            endpoint = assemblePasswordsEndpoint(
                subEndpoint = passwordId
            ),
            headers = deviceIdHeader
        )
    }

    /**
     * Request to edit an existing password
     *
     * @param passwordId The identifier of the password
     * @param tail The tail of the password
     * @param scopes The scopes of the password
     * @param password The value of the password to insert
     *
     * @return the result of the request as [JsonObject]
     */
    @RequestPath(path = "/api/v1/users/{id}/passwords/{password_id}", method = PATCH)
    suspend fun editPassword(
        passwordId: String,
        tail: String,
        scopes: String,
        password: String? = null,
    ): JsonObject {
        val payload = buildJsonObject {
            put(TAIL_KEY, tail)
            put(SCOPES_KEY, scopes)
            password?.let {
                put(PASSWORD_KEY, password)
            }
        }
        return execPatch(
            endpoint = assemblePasswordsEndpoint(
                subEndpoint = passwordId
            ),
            headers = deviceIdHeader,
            payload = payload
        )
    }

    /**
     * Request to notify the copy of a password
     *
     * @param password The copied password
     *
     * @return the result of the request as [JsonObject]
     */
    @RequestPath(path = "/api/v1/users/{id}/passwords/{password_id}", method = PUT)
    suspend fun copyPassword(
        password: Password,
    ): JsonObject {
        return execPut(
            endpoint = assemblePasswordsEndpoint(
                subEndpoint = password.id
            ),
            headers = deviceIdHeader
        )
    }

    /**
     * Request to refresh a password
     *
     * @param password The password to refresh
     *
     * @return the result of the request as [JsonObject]
     */
    @RequestPath(path = "/api/v1/users/{id}/passwords/{password_id}/refresh", method = PATCH)
    suspend fun refreshPassword(
        password: Password,
    ): JsonObject {
        return execPatch(
            endpoint = assemblePasswordsEndpoint(
                subEndpoint = password.id + REFRESH_ENDPOINT
            ),
            headers = deviceIdHeader
        )
    }

    /**
     * Request to delete a password owned by the user
     *
     * @param password The password to delete
     *
     * @return the result of the request as [JsonObject]
     */
    @RequestPath(path = "/api/v1/users/{id}/passwords/{password_id}", method = DELETE)
    suspend fun deletePassword(
        password: Password,
    ): JsonObject {
        return execDelete(
            endpoint = assemblePasswordsEndpoint(
                subEndpoint = password.id
            ),
            headers = deviceIdHeader
        )
    }

    /**
     * Method to assemble the endpoint to make the request to the password related controller
     *
     * @param subEndpoint The sub-endpoint path of the url
     *
     * @return an endpoint to make the requests as [String]
     */
    @Assembler
    private fun assemblePasswordsEndpoint(
        subEndpoint: String = "",
    ): String {
        return assembleCustomEndpointPath(
            customEndpoint = PASSWORDS_KEY,
            subEndpoint = subEndpoint
        )
    }

}