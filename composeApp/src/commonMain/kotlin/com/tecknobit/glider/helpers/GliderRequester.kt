package com.tecknobit.glider.helpers

import com.tecknobit.equinoxcompose.network.EquinoxRequester
import com.tecknobit.equinoxcore.annotations.Assembler
import com.tecknobit.equinoxcore.annotations.CustomParametersOrder
import com.tecknobit.equinoxcore.annotations.RequestPath
import com.tecknobit.equinoxcore.helpers.KEYWORDS_KEY
import com.tecknobit.equinoxcore.helpers.PASSWORD_KEY
import com.tecknobit.equinoxcore.network.EquinoxBaseEndpointsSet.Companion.DYNAMIC_ACCOUNT_DATA_ENDPOINT
import com.tecknobit.equinoxcore.network.RequestMethod.GET
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

    suspend fun getKeychain(
        page: Int,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        keywords: String,
        passwordTypes: List<PasswordType>,
    ): JsonObject {
        val query = buildJsonObject {
            put(PAGE_KEY, page)
            put(PAGE_SIZE_KEY, pageSize)
            put(KEYWORDS_KEY, keywords)
            put(TYPE_KEY, passwordTypes.joinToString())
        }
        return execGet(
            endpoint = assemblePasswordsEndpoint(
                subEndpoint = KEYCHAIN_ENDPOINT
            ),
            headers = deviceIdHeader,
            query = query
        )
    }

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