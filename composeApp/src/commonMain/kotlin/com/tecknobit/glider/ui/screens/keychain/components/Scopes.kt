package com.tecknobit.glider.ui.screens.keychain.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import com.tecknobit.glider.ui.screens.keychain.data.Password
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.no_scopes
import org.jetbrains.compose.resources.stringResource

@Composable
@NonRestartableComposable
fun Scopes(
    modifier: Modifier = Modifier,
    password: Password,
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 12.dp
            )
            .height(38.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        if (password.hasScopes()) {
            items(
                items = password.scopes.toList(),
                key = { scope -> scope }
            ) { scope ->
                Scope(
                    scope = scope
                )
            }
        } else {
            item {
                Scope(
                    scope = stringResource(Res.string.no_scopes),
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }
    }
}

@Composable
@NonRestartableComposable
private fun Scope(
    scope: String,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
) {
    Text(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    size = 8.dp
                )
            )
            .background(containerColor)
            .padding(
                vertical = 1.dp,
                horizontal = 4.dp
            ),
        text = scope,
        color = contentColor,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}