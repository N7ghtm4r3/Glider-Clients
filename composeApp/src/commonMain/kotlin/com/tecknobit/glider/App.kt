package com.tecknobit.glider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.text.font.FontFamily
import com.tecknobit.equinoxcore.helpers.IDENTIFIER_KEY
import com.tecknobit.equinoxcore.network.Requester.Companion.sendRequest
import com.tecknobit.equinoxcore.network.Requester.Companion.toResponseData
import com.tecknobit.glider.helpers.GliderLocalUser
import com.tecknobit.glider.helpers.GliderRequester
import com.tecknobit.glider.ui.screens.auth.presenter.AuthScreen
import com.tecknobit.glider.ui.screens.editgeneratedpassword.presenter.EditGeneratedPasswordScreen
import com.tecknobit.glider.ui.screens.editinsertedpassword.presenter.EditInsertedPasswordScreen
import com.tecknobit.glider.ui.screens.home.presenter.HomeScreen
import com.tecknobit.glider.ui.screens.splashscreen.Splashscreen
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.inter
import glider.composeapp.generated.resources.josefinsans
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.ui.tooling.preview.Preview

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
lateinit var navigator: Navigator

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

@Composable
@Preview
fun App() {
    displayFontFamily = FontFamily(Font(Res.font.josefinsans))
    bodyFontFamily = FontFamily(Font(Res.font.inter))
    
    PreComposeApp {
        navigator = rememberNavigator()
        NavHost(
            navigator = navigator,
            initialRoute = SPLASHSCREEN
        ) {
            scene(
                route = SPLASHSCREEN
            ) {
                Splashscreen().ShowContent()
            }
            scene(
                route = AUTH_SCREEN
            ) {
                AuthScreen().ShowContent()
            }
            scene(
                route = HOME_SCREEN
            ) {
                HomeScreen().ShowContent()
            }
            scene(
                route = "$EDIT_GENERATED_PASSWORD_SCREEN/{${IDENTIFIER_KEY}}"
            ) { backstackEntry ->
                val passwordId = backstackEntry.path<String>(IDENTIFIER_KEY)!!
                EditGeneratedPasswordScreen(
                    passwordId = passwordId
                ).ShowContent()
            }
            scene(
                route = "$EDIT_INSERTED_PASSWORD_SCREEN/{${IDENTIFIER_KEY}}"
            ) { backstackEntry ->
                val passwordId = backstackEntry.path<String>(IDENTIFIER_KEY)!!
                EditInsertedPasswordScreen(
                    passwordId = passwordId
                ).ShowContent()
            }
        }
    }
}

/**
 * Method used to initialize the Ametista system
 */
@Composable
private fun InitAmetista() {
    /*LaunchedEffect(Unit) {
        val ametistaEngine = AmetistaEngine.ametistaEngine
        ametistaEngine.fireUp(
            configData = Res.readBytes(FILES_AMETISTA_CONFIG_PATHNAME),
            host = AmetistaConfig.HOST,
            serverSecret = AmetistaConfig.SERVER_SECRET!!,
            applicationId = AmetistaConfig.APPLICATION_IDENTIFIER!!,
            bypassSslValidation = AmetistaConfig.BYPASS_SSL_VALIDATION,
            debugMode = false
        )
    }*/
}

/**
 * Method to check whether are available any updates for each platform and then launch the application
 * which the correct first screen to display
 *
 */
@Composable
@NonRestartableComposable
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
        debugMode = true // TODO: TO REMOVE
    )
    val route = if (localUser.isAuthenticated) {
        MainScope().launch {
            requester.sendRequest(
                request = { getDynamicAccountData() },
                onSuccess = { response ->
                    localUser.updateDynamicAccountData(
                        dynamicData = response.toResponseData()
                    )
                },
                onFailure = {}
            )
        }
        HOME_SCREEN
    } else
        AUTH_SCREEN
    setUserLanguage()
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
@NonRestartableComposable
expect fun CloseApplicationOnNavBack()