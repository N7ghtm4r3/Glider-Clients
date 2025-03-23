package com.tecknobit.glider.ui.screens.keychain.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tecknobit.glider.ui.screens.keychain.data.Password
import com.tecknobit.glider.ui.screens.keychain.presentation.KeychainScreenViewModel

@Composable
@NonRestartableComposable
fun PasswordCard(
    viewModel: KeychainScreenViewModel,
    password: Password,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = Color.Transparent
            ),
            headlineContent = {
                Text(
                    text = password.password
                )
            }
        )
    }
}