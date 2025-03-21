package com.tecknobit.glider

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.inter
import glider.composeapp.generated.resources.josefinsans
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * `bodyFontFamily` -> the Pandoro's body font family
 */
lateinit var bodyFontFamily: FontFamily

/**
 * `displayFontFamily` -> the Pandoro's font family
 */
lateinit var displayFontFamily: FontFamily

@Composable
@Preview
fun App() {
    displayFontFamily = FontFamily(Font(Res.font.josefinsans))
    bodyFontFamily = FontFamily(Font(Res.font.inter))
}