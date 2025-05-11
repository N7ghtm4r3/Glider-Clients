@file:OptIn(ExperimentalComposeApi::class)

package com.tecknobit.glider.ui.screens.home.presenter

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tecknobit.equinoxcompose.session.screens.EquinoxNoModelScreen
import com.tecknobit.equinoxcompose.utilities.ResponsiveClass.EXPANDED_CONTENT
import com.tecknobit.equinoxcompose.utilities.ResponsiveClass.MEDIUM_CONTENT
import com.tecknobit.equinoxcompose.utilities.ResponsiveClassComponent
import com.tecknobit.equinoxnavigation.I18nNavigationTab
import com.tecknobit.equinoxnavigation.NavigatorScreen
import com.tecknobit.glider.CloseApplicationOnNavBack
import com.tecknobit.glider.ui.screens.account.presenter.AccountScreenTab
import com.tecknobit.glider.ui.screens.generate.presenter.GenerateScreenTab
import com.tecknobit.glider.ui.screens.insert.presenter.InsertPasswordScreenTab
import com.tecknobit.glider.ui.screens.keychain.presenter.KeychainScreenTab
import com.tecknobit.glider.ui.shared.presenters.GliderScreenTab
import com.tecknobit.glider.ui.theme.AppTypography
import com.tecknobit.glider.ui.theme.GliderTheme
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.account
import glider.composeapp.generated.resources.app_name
import glider.composeapp.generated.resources.app_version
import glider.composeapp.generated.resources.generate
import glider.composeapp.generated.resources.insert
import glider.composeapp.generated.resources.keychain
import org.jetbrains.compose.resources.stringResource

/**
 * The [HomeScreen] class is used to display the specific tab and handle the navigation in app
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see EquinoxNoModelScreen
 * @see NavigatorScreen
 */
class HomeScreen : NavigatorScreen<I18nNavigationTab>() {

    /**
     * Method used to arrange the content of the screen to display
     */
    @Composable
    override fun ArrangeScreenContent() {
        CloseApplicationOnNavBack()
        GliderTheme {
            NavigationContent(
                sideBarWidth = 185.dp,
                navigationBarColor = MaterialTheme.colorScheme.surface,
                backgroundTab = MaterialTheme.colorScheme.inverseOnSurface
            )
        }
    }

    /**
     * Method used to retrieve the tabs to assign to the [tabs] array
     *
     * @return the tabs used by the [NavigatorScreen] as [Array] of [I18nNavigationTab]
     */
    override fun navigationTabs(): Array<I18nNavigationTab> {
        return arrayOf(
            I18nNavigationTab(
                title = Res.string.generate,
                contentDescription = "Generate password",
                icon = Icons.Default.Password
            ),
            I18nNavigationTab(
                title = Res.string.insert,
                contentDescription = "Insert password",
                icon = Icons.Default.Keyboard
            ),
            I18nNavigationTab(
                title = Res.string.keychain,
                contentDescription = "Keychain",
                icon = Icons.Default.Key
            ),
            I18nNavigationTab(
                title = Res.string.account,
                contentDescription = "Account",
                icon = Icons.Default.ManageAccounts
            )
        )
    }

    /**
     * Custom header content to display on the [SideNavigationArrangement] bar
     */
    @Composable
    @ResponsiveClassComponent(
        classes = [EXPANDED_CONTENT, MEDIUM_CONTENT]
    )
    override fun ColumnScope.SideNavigationHeaderContent() {
        Text(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 35.dp
                )
                .fillMaxWidth(),
            text = stringResource(Res.string.app_name),
            style = AppTypography.displaySmall,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
    }

    /**
     * Custom footer content to display on the [SideNavigationArrangement] bar
     */
    @Composable
    @ResponsiveClassComponent(
        classes = [EXPANDED_CONTENT, MEDIUM_CONTENT]
    )
    override fun ColumnScope.SideNavigationFooterContent() {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "v${stringResource(Res.string.app_version)}",
            style = AppTypography.labelMedium,
            textAlign = TextAlign.Center
        )
    }

    /**
     * Method used to instantiate the related screen based on the current [activeNavigationTabIndex]
     *
     * @return the screen as [EquinoxNoModelScreen]
     */
    override fun Int.tabContent(): GliderScreenTab<*> {
        return when (this) {
            0 -> GenerateScreenTab()
            1 -> InsertPasswordScreenTab()
            2 -> KeychainScreenTab()
            else -> AccountScreenTab()
        }
    }

}