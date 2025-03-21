package com.tecknobit.glider.ui.shared.presenters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.tecknobit.equinoxcompose.session.screens.EquinoxScreen
import com.tecknobit.equinoxcompose.viewmodels.EquinoxViewModel
import com.tecknobit.equinoxcore.annotations.Structure
import com.tecknobit.glider.ui.theme.AppTypography
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Structure
abstract class GliderScreenTab<V : EquinoxViewModel>(
    viewModel: V,
    private val title: StringResource,
) : EquinoxScreen<V>(
    viewModel = viewModel
) {

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
                            .widthIn(
                                max = MAX_CONTAINER_WIDTH
                            )
                    ) {
                        ScreenContent()
                    }
                }
            }
        }
    }

    @Composable
    protected abstract fun ColumnScope.ScreenContent()

}