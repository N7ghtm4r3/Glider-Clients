package com.tecknobit.glider.ui.screens.generate

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.tecknobit.glider.ui.shared.presenters.GliderScreen
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.generate

class GenerateScreen : GliderScreen<GenerateScreenViewModel>(
    viewModel = GenerateScreenViewModel(),
    title = Res.string.generate
) {

    @Composable
    override fun ScreenContent() {
        Text(
            text = "gagaga"
        )
    }


    /**
     * Method to collect or instantiate the states of the screen
     */
    @Composable
    override fun CollectStates() {
    }

}