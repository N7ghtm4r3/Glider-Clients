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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecknobit.glider.ui.screens.keychain.data.Password

@Composable
@NonRestartableComposable
fun Scopes(
    password: Password,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 12.dp
            )
            .height(35.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(
            items = password.scopes.toList(),
            key = { scope -> scope }
        ) { scope ->
            Scope(
                scope = scope
            )
        }
    }
}

@Composable
@NonRestartableComposable
private fun Scope(
    scope: String,
) {
    Text(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    size = 8.dp
                )
            )
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(
                vertical = 1.dp,
                horizontal = 4.dp
            ),
        text = scope,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}