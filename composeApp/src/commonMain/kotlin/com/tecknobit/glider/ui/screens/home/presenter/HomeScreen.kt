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
import androidx.compose.ui.unit.dp
import com.tecknobit.equinoxcompose.session.screens.EquinoxNoModelScreen
import com.tecknobit.equinoxcompose.utilities.CompactClassComponent
import com.tecknobit.equinoxcompose.utilities.ResponsiveClass.EXPANDED_CONTENT
import com.tecknobit.equinoxcompose.utilities.ResponsiveClass.MEDIUM_CONTENT
import com.tecknobit.equinoxcompose.utilities.ResponsiveClassComponent
import com.tecknobit.equinoxcompose.utilities.ResponsiveContent
import com.tecknobit.glider.ui.screens.generate.GenerateScreen
import com.tecknobit.glider.ui.screens.home.data.ScreenTab
import com.tecknobit.glider.ui.screens.insert.presenter.InsertPasswordScreen
import com.tecknobit.glider.ui.screens.keychain.presenter.KeychainScreen
import com.tecknobit.glider.ui.shared.presenters.GliderScreen
import com.tecknobit.glider.ui.theme.AppTypography
import com.tecknobit.glider.ui.theme.GliderTheme
import glider.composeapp.generated.resources.Res
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
                                text = stringResource(tab.tabTitle)
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
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
            ) {
                ScreenTabContent(
                    paddingValues = PaddingValues(
                        all = 16.dp
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
                .background(MaterialTheme.colorScheme.onPrimaryContainer)
        ) {
            ScreenTabContent(
                paddingValues = PaddingValues(
                    top = 16.dp,
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
                                text = stringResource(tab.tabTitle)
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
                .fillMaxSize()
        ) {
            AnimatedContent(
                targetState = activeScreenTabIndex.value
            ) { activeIndex ->
                activeIndex.relatedScreenTab().ShowContent()
            }
        }
    }

    private fun Int.relatedScreenTab(): GliderScreen<*> {
        return when (this) {
            0 -> GenerateScreen()
            1 -> InsertPasswordScreen()
            else -> KeychainScreen()
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