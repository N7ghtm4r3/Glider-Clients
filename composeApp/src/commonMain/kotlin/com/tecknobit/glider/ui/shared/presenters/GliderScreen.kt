package com.tecknobit.glider.ui.shared.presenters

import androidx.compose.runtime.Composable
import com.tecknobit.equinoxcompose.session.screens.EquinoxScreen
import com.tecknobit.equinoxcompose.viewmodels.EquinoxViewModel
import com.tecknobit.equinoxcore.annotations.Structure

@Structure
abstract class GliderScreen<V : EquinoxViewModel>(
    viewModel: V,
) : EquinoxScreen<V>(
    viewModel = viewModel
) {

    @Composable
    override fun ArrangeScreenContent() {

    }

    @Composable
    protected abstract fun ScreenContent()

}