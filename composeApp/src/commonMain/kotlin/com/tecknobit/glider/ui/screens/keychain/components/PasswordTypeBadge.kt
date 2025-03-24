package com.tecknobit.glider.ui.screens.keychain.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecknobit.glider.ui.theme.applyDarkTheme
import com.tecknobit.glider.ui.theme.generatedColorDark
import com.tecknobit.glider.ui.theme.generatedColorLight
import com.tecknobit.glider.ui.theme.insertedColorDark
import com.tecknobit.glider.ui.theme.insertedColorLight
import com.tecknobit.glidercore.enums.PasswordType
import com.tecknobit.glidercore.enums.PasswordType.GENERATED
import com.tecknobit.glidercore.enums.PasswordType.INSERTED
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.generated
import glider.composeapp.generated.resources.inserted
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
@NonRestartableComposable
fun PasswordTypeBadge(
    type: PasswordType,
) {
    Text(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    size = 8.dp
                )
            )
            .background(type.color())
            .padding(
                all = 4.dp
            ),
        text = stringResource(type.text()),
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun PasswordType.text(): StringResource {
    return when (this) {
        GENERATED -> Res.string.generated
        INSERTED -> Res.string.inserted
    }
}

@Composable
private fun PasswordType.color(): Color {
    return if (applyDarkTheme()) {
        when (this) {
            GENERATED -> generatedColorDark
            INSERTED -> insertedColorDark
        }
    } else {
        when (this) {
            GENERATED -> generatedColorLight
            INSERTED -> insertedColorLight
        }
    }
}