package com.tecknobit.glider.ui.screens.generate

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.tecknobit.glider.ui.shared.presenters.GliderScreenTab
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.generate

class GenerateScreenTab : GliderScreenTab<GenerateScreenViewModel>(
    viewModel = GenerateScreenViewModel(),
    title = Res.string.generate
) {

    @Composable
    override fun ColumnScope.ScreenContent() {
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