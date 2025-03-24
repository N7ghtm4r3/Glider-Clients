package com.tecknobit.glider.ui.screens.keychain.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecknobit.glider.ui.screens.keychain.presentation.KeychainScreenViewModel
import com.tecknobit.glidercore.enums.PasswordType

@Composable
@NonRestartableComposable
fun PasswordTypeFiltersMenu(
    expanded: MutableState<Boolean>,
    viewModel: KeychainScreenViewModel,
) {
    DropdownMenu(
        expanded = expanded.value,
        shape = RoundedCornerShape(
            size = 10.dp
        ),
        onDismissRequest = {
            expanded.value = false
            viewModel.passwordsState.refresh()
        }
    ) {
        PasswordType.entries.forEach { type ->
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = viewModel.typeFilterApplied(
                        type = type
                    ),
                    onCheckedChange = {
                        viewModel.applyTypeFilters(
                            type = type
                        )
                    }
                )
                PasswordTypeBadge(
                    type = type
                )
            }
        }
    }
}