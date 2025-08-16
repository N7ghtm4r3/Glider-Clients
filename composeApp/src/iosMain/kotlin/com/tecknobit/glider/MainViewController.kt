package com.tecknobit.glider

import androidx.compose.ui.window.ComposeUIViewController

/**
 * Method to start the of `Glider` iOs application
 */
fun MainViewController() {
    // AmetistaEngine.intake()
    ComposeUIViewController {
        App()
    }
}