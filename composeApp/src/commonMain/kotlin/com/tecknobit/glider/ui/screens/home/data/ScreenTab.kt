package com.tecknobit.glider.ui.screens.home.data

import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource

/**
 * The `ScreenTab` data class represents the information about a tab
 *
 * @param tabTitle The title of the tab
 * @param contentDescription The content description for the accessibility
 * @param icon The representative icon of the tab
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 */
data class ScreenTab(
    val tabTitle: StringResource,
    val contentDescription: String,
    val icon: ImageVector,
)
