@file:OptIn(ExperimentalMultiplatform::class)

package com.tecknobit.glider.ui.screens.keychain.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecknobit.equinoxcompose.components.EmptyState
import com.tecknobit.equinoxcompose.utilities.ResponsiveClass.COMPACT_CONTENT
import com.tecknobit.equinoxcompose.utilities.ResponsiveClass.EXPANDED_CONTENT
import com.tecknobit.equinoxcompose.utilities.ResponsiveClass.MEDIUM_CONTENT
import com.tecknobit.equinoxcompose.utilities.ResponsiveClass.MEDIUM_EXPANDED_CONTENT
import com.tecknobit.equinoxcompose.utilities.ResponsiveClassComponent
import com.tecknobit.equinoxcompose.utilities.ResponsiveContent
import com.tecknobit.glider.ui.components.FirstPageProgressIndicator
import com.tecknobit.glider.ui.components.NewPageProgressIndicator
import com.tecknobit.glider.ui.screens.keychain.presentation.KeychainScreenViewModel
import com.tecknobit.glider.ui.theme.AppTypography
import com.tecknobit.glider.ui.theme.applyDarkTheme
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.no_passwords_available
import glider.composeapp.generated.resources.no_passwords_dark
import glider.composeapp.generated.resources.no_passwords_light
import io.github.ahmad_hamwi.compose.pagination.PaginatedLazyColumn
import io.github.ahmad_hamwi.compose.pagination.PaginatedLazyVerticalStaggeredGrid
import org.jetbrains.compose.resources.stringResource

/**
 * Layout used to display the passwords owned by the [com.tecknobit.glider.localUser]
 *
 * @param viewModel The support viewmodel of the screen
 */
@Composable
@NonRestartableComposable
fun Passwords(
    viewModel: KeychainScreenViewModel,
) {
    ResponsiveContent(
        onExpandedSizeClass = {
            PasswordsGrid(
                viewModel = viewModel
            )
        },
        onMediumSizeClass = {
            PasswordsGrid(
                viewModel = viewModel
            )
        },
        onMediumWidthExpandedHeight = {
            PasswordsList(
                viewModel = viewModel
            )
        },
        onCompactSizeClass = {
            PasswordsList(
                viewModel = viewModel
            )
        }
    )
}

/**
 * Custom [PaginatedLazyVerticalStaggeredGrid] used to display the passwords owned by the
 * [com.tecknobit.glider.localUser]
 *
 * @param viewModel The support viewmodel of the screen
 */
@Composable
@NonRestartableComposable
@ResponsiveClassComponent(
    classes = [EXPANDED_CONTENT, MEDIUM_CONTENT]
)
private fun PasswordsGrid(
    viewModel: KeychainScreenViewModel,
) {
    PaginatedLazyVerticalStaggeredGrid(
        modifier = Modifier
            .navigationBarsPadding()
            .animateContentSize(),
        columns = StaggeredGridCells.Adaptive(
            minSize = 400.dp
        ),
        paginationState = viewModel.passwordsState,
        firstPageEmptyIndicator = { NoPasswords() },
        firstPageProgressIndicator = { FirstPageProgressIndicator() },
        newPageErrorIndicator = { NewPageProgressIndicator() },
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalItemSpacing = 10.dp,
        contentPadding = PaddingValues(
            vertical = 16.dp
        )
    ) {
        items(
            items = viewModel.passwordsState.allItems!!,
            key = { password -> password.id }
        ) { password ->
            PasswordCard(
                viewModel = viewModel,
                password = password
            )
        }
    }
}

/**
 * Custom [PaginatedLazyColumn] used to display the passwords owned by the
 * [com.tecknobit.glider.localUser]
 *
 * @param viewModel The support viewmodel of the screen
 */
@Composable
@NonRestartableComposable
@ResponsiveClassComponent(
    classes = [MEDIUM_EXPANDED_CONTENT, COMPACT_CONTENT]
)
private fun PasswordsList(
    viewModel: KeychainScreenViewModel,
) {
    PaginatedLazyColumn(
        modifier = Modifier
            .navigationBarsPadding()
            .animateContentSize(),
        paginationState = viewModel.passwordsState,
        firstPageEmptyIndicator = { NoPasswords() },
        firstPageProgressIndicator = { FirstPageProgressIndicator() },
        newPageErrorIndicator = { NewPageProgressIndicator() },
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(
            items = viewModel.passwordsState.allItems!!,
            key = { password -> password.id }
        ) { password ->
            PasswordCard(
                viewModel = viewModel,
                password = password
            )
        }
    }
}

/**
 * Custom [EmptyState] displayed when the are no passwords available
 */
@Composable
@NonRestartableComposable
private fun NoPasswords() {
    EmptyState(
        resource = if (applyDarkTheme())
            Res.drawable.no_passwords_dark
        else
            Res.drawable.no_passwords_light,
        resourceSize = 250.dp,
        contentDescription = "No passwords available",
        title = stringResource(Res.string.no_passwords_available),
        titleStyle = AppTypography.titleMedium.copy(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    )
}