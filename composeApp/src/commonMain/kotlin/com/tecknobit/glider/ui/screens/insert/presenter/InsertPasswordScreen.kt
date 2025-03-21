package com.tecknobit.glider.ui.screens.insert.presenter

import androidx.compose.runtime.Composable
import com.tecknobit.glider.ui.screens.insert.presentation.KeychainScreenViewModel
import com.tecknobit.glider.ui.shared.presenters.GliderScreen
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.insert

class InsertPasswordScreen : GliderScreen<KeychainScreenViewModel>(
    viewModel = KeychainScreenViewModel(),
    title = Res.string.insert
) {

    @Composable
    override fun ScreenContent() {
    }

    /**
     * Method to collect or instantiate the states of the screen
     */
    @Composable
    override fun CollectStates() {
    }

}