package com.tecknobit.glider.ui.screens.home.presenter

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tecknobit.equinoxcompose.session.screens.EquinoxNoModelScreen
import com.tecknobit.equinoxcompose.utilities.CompactClassComponent
import com.tecknobit.equinoxcompose.utilities.ResponsiveClass.EXPANDED_CONTENT
import com.tecknobit.equinoxcompose.utilities.ResponsiveClass.MEDIUM_CONTENT
import com.tecknobit.equinoxcompose.utilities.ResponsiveClassComponent
import com.tecknobit.equinoxcompose.utilities.ResponsiveContent
import com.tecknobit.glider.ui.screens.account.presenter.AccountScreenTab
import com.tecknobit.glider.ui.screens.generate.presenter.GenerateScreenTab
import com.tecknobit.glider.ui.screens.home.data.ScreenTab
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

class HomeScreen : EquinoxNoModelScreen() {

    companion object {

        private val tabs = arrayOf(
            ScreenTab(
                tabTitle = Res.string.generate,
                contentDescription = "Generate password",
                icon = Icons.Default.Password
            ),
            ScreenTab(
                tabTitle = Res.string.insert,
                contentDescription = "Insert password",
                icon = Icons.Default.Keyboard
            ),
            ScreenTab(
                tabTitle = Res.string.keychain,
                contentDescription = "Keychain",
                icon = Icons.Default.Key
            ),
            ScreenTab(
                tabTitle = Res.string.account,
                contentDescription = "Account",
                icon = Icons.Default.ManageAccounts
            )
        )

    }

    private lateinit var activeScreenTabIndex: MutableState<Int>

    /**
     * Method to arrange the content of the screen to display
     */
    @Composable
    override fun ArrangeScreenContent() {
        GliderTheme {
            ResponsiveContent(
                onExpandedSizeClass = { SideNavigationContent() },
                onMediumSizeClass = { SideNavigationContent() },
                onMediumWidthExpandedHeight = { BottomNavigationContent() },
                onCompactSizeClass = { BottomNavigationContent() }
            )
        }
    }

    @Composable
    @NonRestartableComposable
    @ResponsiveClassComponent(
        classes = [EXPANDED_CONTENT, MEDIUM_CONTENT]
    )
    private fun SideNavigationContent() {
        Row {
            NavigationRail(
                modifier = Modifier
                    .width(185.dp),
                header = {
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
            ) {
                tabs.forEachIndexed { index, tab ->
                    NavigationDrawerItem(
                        modifier = Modifier
                            .padding(
                                end = 10.dp
                            ),
                        selected = index == activeScreenTabIndex.value,
                        onClick = { activeScreenTabIndex.value = index },
                        shape = RoundedCornerShape(
                            topEnd = 10.dp,
                            bottomEnd = 10.dp
                        ),
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.contentDescription
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(tab.tabTitle),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            bottom = 16.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = "v${stringResource(Res.string.app_version)}",
                        style = AppTypography.labelMedium
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
            ) {
                ScreenTabContent(
                    paddingValues = PaddingValues(
                        top = 35.dp,
                        start = 35.dp,
                        end = 35.dp,
                        bottom = 16.dp
                    )
                )
            }
        }
    }

    @Composable
    @CompactClassComponent
    @NonRestartableComposable
    private fun BottomNavigationContent() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            ScreenTabContent(
                paddingValues = PaddingValues(
                    top = 25.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 96.dp
                )
            )
            BottomAppBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        selected = index == activeScreenTabIndex.value,
                        onClick = { activeScreenTabIndex.value = index },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.contentDescription
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(tab.tabTitle),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                }
            }
        }
    }

    @Composable
    @NonRestartableComposable
    private fun ScreenTabContent(
        paddingValues: PaddingValues,
    ) {
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            Column {
                AnimatedContent(
                    targetState = activeScreenTabIndex.value
                ) { activeIndex ->
                    activeIndex.relatedScreenTab().ShowContent()
                }
            }
        }
    }

    private fun Int.relatedScreenTab(): GliderScreenTab<*> {
        return when (this) {
            0 -> GenerateScreenTab()
            1 -> InsertPasswordScreenTab()
            2 -> KeychainScreenTab()
            else -> AccountScreenTab()
        }
    }

    /**
     * Method to collect or instantiate the states of the screen
     */
    @Composable
    override fun CollectStates() {
        activeScreenTabIndex = remember { mutableStateOf(0) }
    }

}