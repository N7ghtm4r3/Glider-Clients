package com.tecknobit.glider.ui.screens.splashscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecknobit.equinoxcompose.session.screens.EquinoxNoModelScreen
import com.tecknobit.glider.CheckForUpdatesAndLaunch
import com.tecknobit.glider.ui.theme.AppTypography
import com.tecknobit.glider.ui.theme.GliderTheme
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource

class Splashscreen : EquinoxNoModelScreen() {

    /**
     * Method to arrange the content of the screen to display
     */
    @Composable
    override fun ArrangeScreenContent() {
        GliderTheme {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = stringResource(Res.string.app_name),
                        style = AppTypography.displayLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            bottom = 16.dp
                        ),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = "by Tecknobit",
                        style = AppTypography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
        CheckForUpdatesAndLaunch()
    }

    /**
     * Method to collect or instantiate the states of the screen
     */
    @Composable
    override fun CollectStates() {
    }

}