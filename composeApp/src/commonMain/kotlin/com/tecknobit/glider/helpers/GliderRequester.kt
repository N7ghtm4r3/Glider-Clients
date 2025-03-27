package com.tecknobit.glider.helpers

import com.tecknobit.equinoxcompose.network.EquinoxRequester

class GliderRequester(
    host: String,
    userId: String?,
    userToken: String?,
    debugMode: Boolean = false,
) : EquinoxRequester(
    host = host,
    userId = userId,
    userToken = userToken,
    debugMode = debugMode,
    byPassSSLValidation = true,
    connectionErrorMessage = "Server temporary unavailable"
)