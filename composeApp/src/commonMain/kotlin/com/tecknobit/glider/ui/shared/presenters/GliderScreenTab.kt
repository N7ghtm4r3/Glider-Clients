@file:OptIn(ExperimentalComposeApi::class)

package com.tecknobit.glider.ui.shared.presenters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.tecknobit.equinoxcompose.annotations.ScreenCoordinator
import com.tecknobit.equinoxcompose.session.screens.EquinoxNoModelScreen
import com.tecknobit.equinoxcompose.session.screens.EquinoxScreen
import com.tecknobit.equinoxcompose.utilities.responsiveMaxWidth
import com.tecknobit.equinoxcompose.viewmodels.EquinoxViewModel
import com.tecknobit.equinoxcore.annotations.Structure
import com.tecknobit.glider.ui.theme.AppTypography
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * The `GliderScreenTab` class is useful to create and handle a tab with the Glider's structure
 *
 * @property viewModel The support viewmodel of the screen
 * @property title The title of the tab
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 * @param V generic type used to allow the use of own viewmodel in custom screens
 *
 * @see EquinoxNoModelScreen
 * @see EquinoxScreen
 */
@Structure
@ScreenCoordinator
abstract class GliderScreenTab<V : EquinoxViewModel>(
    viewModel: V,
    protected val title: StringResource,
) : EquinoxScreen<V>(
    viewModel = viewModel
) {

    /**
     * Method to arrange the content of the screen to display
     */
    @Composable
    override fun ArrangeScreenContent() {
        Scaffold(
            containerColor = Color.Transparent,
            snackbarHost = {
                SnackbarHost(
                    hostState = viewModel.snackbarHostState!!
                )
            }
        ) {
            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(title),
                    color = contentColorFor(
                        backgroundColor = MaterialTheme.colorScheme.inverseOnSurface
                    ),
                    style = AppTypography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .responsiveMaxWidth()
                    ) {
                        ScreenContent()
                    }
                }
            }
        }
    }

    /**
     * The custom content displayed in the tab
     */
    @Composable
    protected abstract fun ColumnScope.ScreenContent()

}