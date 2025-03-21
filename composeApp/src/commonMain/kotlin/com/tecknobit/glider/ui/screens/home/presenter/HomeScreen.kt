package com.tecknobit.glider.ui.screens.home.presenter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecknobit.equinoxcompose.session.screens.EquinoxNoModelScreen
import com.tecknobit.equinoxcompose.utilities.CompactClassComponent
import com.tecknobit.equinoxcompose.utilities.ResponsiveClass.EXPANDED_CONTENT
import com.tecknobit.equinoxcompose.utilities.ResponsiveClass.MEDIUM_CONTENT
import com.tecknobit.equinoxcompose.utilities.ResponsiveClassComponent
import com.tecknobit.equinoxcompose.utilities.ResponsiveContent
import com.tecknobit.glider.ui.screens.generate.GenerateScreen
import com.tecknobit.glider.ui.theme.GliderTheme

class HomeScreen : EquinoxNoModelScreen() {

    /**
     * Method to arrange the content of the screen to display
     */
    @Composable
    override fun ArrangeScreenContent() {
        GliderTheme {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
            ) {
                NavigatorScaffold()
            }
        }
    }

    @Composable
    @NonRestartableComposable
    private fun NavigatorScaffold() {
        ResponsiveContent(
            onExpandedSizeClass = { SideNavigationBar() },
            onMediumSizeClass = { SideNavigationBar() },
            onCompactSizeClass = { BottomNavigationBar() }
        )
    }

    @Composable
    @NonRestartableComposable
    @ResponsiveClassComponent(
        classes = [EXPANDED_CONTENT, MEDIUM_CONTENT]
    )
    private fun SideNavigationBar() {
        Row {
            Column {
                NavigationRail(
                    modifier = Modifier
                        .width(185.dp)
                ) {

                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                GenerateScreen().ShowContent()
            }
        }
    }

    @Composable
    @CompactClassComponent
    @NonRestartableComposable
    private fun BottomNavigationBar() {
        Box {
            GenerateScreen().ShowContent()
            BottomAppBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {

            }
        }
    }

    /**
     * Method to collect or instantiate the states of the screen
     */
    @Composable
    override fun CollectStates() {
    }

}