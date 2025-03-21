package com.tecknobit.glider.ui.screens.account.presenter

import androidx.compose.runtime.Composable
import com.tecknobit.glider.ui.screens.account.presentation.AccountScreenViewModel
import com.tecknobit.glider.ui.shared.presenters.GliderScreen
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.account

class AccountScreen : GliderScreen<AccountScreenViewModel>(
    viewModel = AccountScreenViewModel(),
    title = Res.string.account
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