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
import com.tecknobit.equinoxcore.annotations.Wrapper
import com.tecknobit.glider.ui.theme.applyDarkTheme
import com.tecknobit.glider.ui.theme.copiedDark
import com.tecknobit.glider.ui.theme.copiedLight
import com.tecknobit.glider.ui.theme.editedDark
import com.tecknobit.glider.ui.theme.editedLight
import com.tecknobit.glider.ui.theme.generatedColorDark
import com.tecknobit.glider.ui.theme.generatedColorLight
import com.tecknobit.glider.ui.theme.insertedColorDark
import com.tecknobit.glider.ui.theme.insertedColorLight
import com.tecknobit.glider.ui.theme.refreshedDark
import com.tecknobit.glider.ui.theme.refreshedLight
import com.tecknobit.glidercore.enums.PasswordEventType
import com.tecknobit.glidercore.enums.PasswordType
import com.tecknobit.glidercore.enums.PasswordType.GENERATED
import com.tecknobit.glidercore.enums.PasswordType.INSERTED
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.copied
import glider.composeapp.generated.resources.edited
import glider.composeapp.generated.resources.generated
import glider.composeapp.generated.resources.inserted
import glider.composeapp.generated.resources.refreshed
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * Badge used to display a [PasswordType] entry
 */
@Wrapper
@Composable
@NonRestartableComposable
fun PasswordTypeBadge(
    type: PasswordType,
) {
    EnumBadge(
        color = type.color(),
        badgeText = type.text()
    )
}

/**
 * Method used to convert the [PasswordType] entry as internationalized [StringResource]
 *
 * @return the entry as [StringResource]
 */
@Composable
private fun PasswordType.text(): StringResource {
    return when (this) {
        GENERATED -> Res.string.generated
        INSERTED -> Res.string.inserted
    }
}

/**
 * Method used to associate a custom color for a [PasswordType] entry
 *
 * @return the associated color as [Color]
 */
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

/**
 * Badge used to display a [PasswordEventType] entry
 */
@Wrapper
@Composable
@NonRestartableComposable
fun PasswordEventTypeBadge(
    type: PasswordEventType,
) {
    EnumBadge(
        color = type.color(),
        badgeText = type.text()
    )
}

/**
 * Method used to convert the [PasswordEventType] entry as internationalized [StringResource]
 *
 * @return the entry as [StringResource]
 */
@Composable
private fun PasswordEventType.text(): StringResource {
    return when (this) {
        PasswordEventType.GENERATED -> Res.string.generated
        PasswordEventType.INSERTED -> Res.string.inserted
        PasswordEventType.COPIED -> Res.string.copied
        PasswordEventType.EDITED -> Res.string.edited
        PasswordEventType.REFRESHED -> Res.string.refreshed
    }
}

/**
 * Method used to associate a custom color for a [PasswordEventType] entry
 *
 * @return the associated color as [Color]
 */
@Composable
private fun PasswordEventType.color(): Color {
    return if (applyDarkTheme()) {
        when (this) {
            PasswordEventType.GENERATED -> generatedColorDark
            PasswordEventType.INSERTED -> insertedColorDark
            PasswordEventType.COPIED -> copiedLight
            PasswordEventType.EDITED -> editedLight
            PasswordEventType.REFRESHED -> refreshedLight
        }
    } else {
        when (this) {
            PasswordEventType.GENERATED -> generatedColorLight
            PasswordEventType.INSERTED -> insertedColorLight
            PasswordEventType.COPIED -> copiedDark
            PasswordEventType.EDITED -> editedDark
            PasswordEventType.REFRESHED -> refreshedDark
        }
    }
}

/**
 * Custom text used as badge to display an [Enum] entry as badge
 *
 * @param color The color of the badge
 * @param badgeText The text of the badge
 */
@Composable
@NonRestartableComposable
private fun EnumBadge(
    color: Color,
    badgeText: StringResource,
) {
    Text(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    size = 8.dp
                )
            )
            .background(color)
            .padding(
                vertical = 1.dp,
                horizontal = 4.dp
            ),
        text = stringResource(badgeText),
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}