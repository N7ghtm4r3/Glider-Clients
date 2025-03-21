package com.tecknobit.glider.ui.shared.presenters

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.tecknobit.equinoxcompose.session.screens.EquinoxScreen
import com.tecknobit.equinoxcompose.viewmodels.EquinoxViewModel
import com.tecknobit.equinoxcore.annotations.Structure
import com.tecknobit.glider.ui.theme.AppTypography
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Structure
abstract class GliderScreen<V : EquinoxViewModel>(
    viewModel: V,
    private val title: StringResource,
) : EquinoxScreen<V>(
    viewModel = viewModel
) {

    @Composable
    override fun ArrangeScreenContent() {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(title),
            style = AppTypography.displayMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }

    @Composable
    protected abstract fun ScreenContent()

}