@file:OptIn(ExperimentalStdlibApi::class)

package com.tecknobit.glider.helpers

import androidx.navigation.NavHostController
import com.tecknobit.equinoxcompose.annotations.DestinationScreen
import com.tecknobit.equinoxcore.annotations.Wrapper
import com.tecknobit.equinoxcore.helpers.IDENTIFIER_KEY
import com.tecknobit.equinoxmisc.navigationcomposeutil.navWithData
import com.tecknobit.glider.ui.screens.editgeneratedpassword.presenter.EditGeneratedPasswordScreen
import com.tecknobit.glider.ui.screens.editinsertedpassword.presenter.EditInsertedPasswordScreen
import com.tecknobit.glider.ui.screens.home.presenter.HomeScreen
import com.tecknobit.glider.ui.screens.keychain.data.Password
import com.tecknobit.glider.ui.screens.splashscreen.Splashscreen
import com.tecknobit.glidercore.enums.PasswordType.GENERATED

/**
 * `navigator` -> the navigator instance is useful to manage the navigation between the screens of the application
 */
lateinit var navigator: NavHostController

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

@DestinationScreen(Splashscreen::class)
fun navToSplashscreen() {
    navigator.navigate(SPLASHSCREEN)
}

@DestinationScreen(HomeScreen::class)
fun navToHome() {
    navigator.navigate(HOME_SCREEN)
}

@Wrapper
fun navToEditPassword(
    password: Password,
) {
    val navData = buildMap<String, Any> {
        put(IDENTIFIER_KEY, password.id)
    }
    if (password.type == GENERATED) {
        navToEditGeneratedPassword(
            navData = navData
        )
    } else {
        navToEditInsertedPassword(
            navData = navData
        )
    }
}

@DestinationScreen(EditGeneratedPasswordScreen::class)
private fun navToEditGeneratedPassword(
    navData: Map<String, Any>,
) {
    navigator.navWithData(
        route = EDIT_GENERATED_PASSWORD_SCREEN,
        data = navData
    )
}

@DestinationScreen(EditInsertedPasswordScreen::class)
private fun navToEditInsertedPassword(
    navData: Map<String, Any>,
) {
    navigator.navWithData(
        route = EDIT_INSERTED_PASSWORD_SCREEN,
        data = navData
    )
}