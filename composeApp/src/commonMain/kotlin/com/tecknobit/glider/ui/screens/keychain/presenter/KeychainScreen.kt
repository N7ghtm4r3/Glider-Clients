package com.tecknobit.glider.ui.screens.keychain.presenter

import androidx.compose.runtime.Composable
import com.tecknobit.glider.ui.screens.insert.presentation.KeychainScreenViewModel
import com.tecknobit.glider.ui.shared.presenters.GliderScreen
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.keychain

class KeychainScreen : GliderScreen<KeychainScreenViewModel>(
    viewModel = KeychainScreenViewModel(),
    title = Res.string.keychain
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