package com.tecknobit.glider.ui.screens.keychain.presenter

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import com.tecknobit.glider.ui.screens.keychain.components.Passwords
import com.tecknobit.glider.ui.screens.keychain.presentation.KeychainScreenViewModel
import com.tecknobit.glider.ui.shared.presenters.GliderScreenTab
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.keychain

class KeychainScreenTab : GliderScreenTab<KeychainScreenViewModel>(
    viewModel = KeychainScreenViewModel(),
    title = Res.string.keychain
) {

    @Composable
    override fun ColumnScope.ScreenContent() {
        Passwords(
            viewModel = viewModel
        )
    }

    /**
     * Method to collect or instantiate the states of the screen
     */
    @Composable
    override fun CollectStates() {
    }

}