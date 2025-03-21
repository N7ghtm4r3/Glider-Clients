package com.tecknobit.glider.ui.screens.keychain.presenter

import androidx.compose.runtime.Composable
import com.tecknobit.glider.ui.screens.insert.presentation.KeychainScreenViewModel
import com.tecknobit.glider.ui.shared.presenters.GliderScreen

class KeychainScreen : GliderScreen<KeychainScreenViewModel>(
    viewModel = KeychainScreenViewModel()
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