@file:OptIn(ExperimentalComposeApi::class, ExperimentalStdlibApi::class)

package com.tecknobit.glider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tecknobit.ametistaengine.AmetistaEngine
import com.tecknobit.ametistaengine.AmetistaEngine.Companion.FILES_AMETISTA_CONFIG_PATHNAME
import com.tecknobit.biometrik.rememberBiometrikState
import com.tecknobit.equinoxcompose.session.screens.equinoxScreen
import com.tecknobit.equinoxcompose.session.sessionflow.SessionFlowState
import com.tecknobit.equinoxcore.helpers.IDENTIFIER_KEY
import com.tecknobit.equinoxcore.network.Requester.Companion.toResponseData
import com.tecknobit.equinoxcore.network.sendRequest
import com.tecknobit.glider.helpers.GliderLocalUser
import com.tecknobit.glider.helpers.GliderRequester
import com.tecknobit.glider.ui.screens.auth.presenter.AuthScreen
import com.tecknobit.glider.ui.screens.editgeneratedpassword.presenter.EditGeneratedPasswordScreen
import com.tecknobit.glider.ui.screens.editinsertedpassword.presenter.EditInsertedPasswordScreen
import com.tecknobit.glider.ui.screens.home.presenter.HomeScreen
import com.tecknobit.glider.ui.screens.splashscreen.Splashscreen
import com.tecknobit.glider.ui.theme.GliderTheme
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.inter
import glider.composeapp.generated.resources.josefinsans
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font

/**
 * `bodyFontFamily` -> the Glider's body font family
 */
lateinit var bodyFontFamily: FontFamily

/**
 * `displayFontFamily` -> the Glider's font family
 */
lateinit var displayFontFamily: FontFamily

/**
 * `navigator` -> the navigator instance is useful to manage the navigation between the screens of the application
 */
lateinit var navigator: NavHostController

/**
 * `localUser` -> the helper to manage the local sessions stored locally in
 * the device
 */
val localUser = GliderLocalUser()

/**
 * `requester` -> the instance to manage the requests with the backend
 */
lateinit var requester: GliderRequester

/**
 * `SPLASHSCREEN` -> route to navigate to the [com.tecknobit.glider.ui.screens.splashscreen.Splashscreen]
 */
const val SPLASHSCREEN = "Splashscreen"

/**
 * `AUTH_SCREEN` -> route to navigate to the [com.tecknobit.glider.ui.screens.auth.presenter.AuthScreen]
 */
const val AUTH_SCREEN = "AuthScreen"

/**
 * `HOME_SCREEN` -> route to navigate to the [com.tecknobit.glider.ui.screens.home.presenter.HomeScreen]
 */
const val HOME_SCREEN = "HomeScreen"

/**
 * `EDIT_GENERATED_PASSWORD_SCREEN` -> route to navigate to the [com.tecknobit.glider.ui.screens.generate.presenter.GenerateScreenTab]
 */
const val EDIT_GENERATED_PASSWORD_SCREEN = "EditGeneratedPasswordScreen"

/**
 * `EDIT_INSERTED_PASSWORD_SCREEN` -> route to navigate to the [com.tecknobit.glider.ui.screens.insert.presenter.InsertPasswordScreenTab]
 */
const val EDIT_INSERTED_PASSWORD_SCREEN = "EditInsertedPasswordScreen"

/**
 * Common entry point of the **Glider** application
 */
@Composable
fun App() {
    // InitAmetista()
    val biometrikState = rememberBiometrikState()
    displayFontFamily = FontFamily(Font(Res.font.josefinsans))
    bodyFontFamily = FontFamily(Font(Res.font.inter))
    navigator = rememberNavController()
    // TODO: TO USE THIS UNIQUE THEME CALL
    // GliderTheme {
        NavHost(
            navController = navigator,
            startDestination = SPLASHSCREEN
        ) {
            composable(
                route = SPLASHSCREEN
            ) {
                // TODO: TO REMOVE THIS THEME CALL
                GliderTheme {
                    val splashscreen = equinoxScreen {
                        Splashscreen(
                            biometrikState = biometrikState
                        )
                    }
                    splashscreen.ShowContent()
                }
            }
            composable(
                route = AUTH_SCREEN
            ) {
                // TODO: TO REMOVE THIS THEME CALL
                GliderTheme {
                    val authScreen = equinoxScreen { AuthScreen() }
                    authScreen.ShowContent()
                }
            }
            composable(
                route = HOME_SCREEN
            ) {
                // TODO: TO REMOVE THIS THEME CALL
                GliderTheme {
                    val homeScreen = equinoxScreen { HomeScreen() }
                    homeScreen.ShowContent()
                }
            }
            composable(
                route = EDIT_GENERATED_PASSWORD_SCREEN
            ) {
                // TODO: TO REMOVE THIS THEME CALL
                GliderTheme {
                    val savedStateHandle = navigator.previousBackStackEntry!!.savedStateHandle
                    val passwordId: String? = savedStateHandle[IDENTIFIER_KEY]
                    passwordId?.let {
                        val editGeneratedPasswordScreen = equinoxScreen {
                            EditGeneratedPasswordScreen(
                                passwordId = passwordId
                            )
                        }
                        editGeneratedPasswordScreen.ShowContent()
                    }
                }
            }
            composable(
                route = EDIT_INSERTED_PASSWORD_SCREEN
            ) {
                // TODO: TO REMOVE THIS THEME CALL
                GliderTheme {
                    val savedStateHandle = navigator.previousBackStackEntry!!.savedStateHandle
                    val passwordId: String? = savedStateHandle[IDENTIFIER_KEY]
                    passwordId?.let {
                        val editInsertedPasswordScreen = equinoxScreen {
                            EditInsertedPasswordScreen(
                                passwordId = passwordId
                            )
                        }
                        editInsertedPasswordScreen.ShowContent()
                    }
                }
            }
        }
    // }
    SessionFlowState.invokeOnUserDisconnected {
        localUser.clear()
        navigator.navigate(AUTH_SCREEN)
    }
}

/**
 * Method used to initialize the Ametista system
 */
@Composable
// TODO: TO REIMPLEMENT WHEN NECESSARY
private fun InitAmetista() {
    LaunchedEffect(Unit) {
        val ametistaEngine = AmetistaEngine.ametistaEngine
        ametistaEngine.fireUp(
            configData = Res.readBytes(FILES_AMETISTA_CONFIG_PATHNAME),
            host = AmetistaConfig.HOST,
            serverSecret = AmetistaConfig.SERVER_SECRET!!,
            applicationId = AmetistaConfig.APPLICATION_IDENTIFIER!!,
            bypassSslValidation = AmetistaConfig.BYPASS_SSL_VALIDATION,
            debugMode = false
        )
    }
}

/**
 * Method to check whether are available any updates for each platform and then launch the application
 * which the correct first screen to display
 *
 */
@Composable
expect fun CheckForUpdatesAndLaunch()

/**
 * Method to init the local session and the related instances then start the user session
 *
 */
fun startSession() {
    requester = GliderRequester(
        host = localUser.hostAddress,
        userId = localUser.userId,
        userToken = localUser.userToken,
        deviceId = localUser.deviceId
    )
    setUserLanguage()
    val route = if (localUser.isAuthenticated) {
        MainScope().launch {
            requester.sendRequest(
                request = { getCustomDynamicAccountData() },
                onSuccess = { response ->
                    localUser.updateDynamicAccountData(
                        dynamicData = response.toResponseData()
                    )
                    setUserLanguage()
                },
                onFailure = {
                    localUser.clear()
                    requester.clearSession()
                    navigator.navigate(AUTH_SCREEN)
                },
                onConnectionError = { }
            )
        }
        HOME_SCREEN
    } else
        AUTH_SCREEN
    navigator.navigate(route)
}

/**
 * Method to set locale language for the application
 *
 */
expect fun setUserLanguage()

/**
 * Method to manage correctly the back navigation from the current screen
 *
 */
@Composable
expect fun CloseApplicationOnNavBack()