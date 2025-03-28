@file:OptIn(ExperimentalMaterial3Api::class)

package com.tecknobit.glider.ui.screens.keychain.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pushpal.jetlime.EventPosition
import com.pushpal.jetlime.ItemsList
import com.pushpal.jetlime.JetLimeColumn
import com.pushpal.jetlime.JetLimeEvent
import com.pushpal.jetlime.JetLimeEventDefaults
import com.tecknobit.equinoxcompose.utilities.ResponsiveClass.COMPACT_CONTENT
import com.tecknobit.equinoxcompose.utilities.ResponsiveClass.EXPANDED_CONTENT
import com.tecknobit.equinoxcompose.utilities.ResponsiveClass.MEDIUM_CONTENT
import com.tecknobit.equinoxcompose.utilities.ResponsiveClass.MEDIUM_EXPANDED_CONTENT
import com.tecknobit.equinoxcompose.utilities.ResponsiveClassComponent
import com.tecknobit.equinoxcompose.utilities.ResponsiveContent
import com.tecknobit.equinoxcore.time.TimeFormatter.EUROPEAN_DATE_PATTERN
import com.tecknobit.equinoxcore.time.TimeFormatter.H24_HOURS_MINUTES_SECONDS_PATTERN
import com.tecknobit.equinoxcore.time.TimeFormatter.toDateString
import com.tecknobit.glider.ui.screens.keychain.data.Password
import com.tecknobit.glider.ui.screens.keychain.data.PasswordEvent
import com.tecknobit.glider.ui.theme.AppTypography
import com.tecknobit.glidercore.enums.PasswordEventType
import com.tecknobit.glidercore.enums.PasswordEventType.COPIED
import com.tecknobit.glidercore.enums.PasswordEventType.EDITED
import com.tecknobit.glidercore.enums.PasswordEventType.GENERATED
import com.tecknobit.glidercore.enums.PasswordEventType.INSERTED
import com.tecknobit.glidercore.enums.PasswordEventType.REFRESHED
import glider.composeapp.generated.resources.Res
import glider.composeapp.generated.resources.password_copied_event
import glider.composeapp.generated.resources.password_edited_event
import glider.composeapp.generated.resources.password_generated_event
import glider.composeapp.generated.resources.password_inserted_event
import glider.composeapp.generated.resources.password_refreshed_event
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
@NonRestartableComposable
fun PasswordTimeline(
    show: MutableState<Boolean>,
    password: Password,
) {
    ResponsiveContent(
        onExpandedSizeClass = {
            ContainedTimeline(
                show = show,
                password = password
            )
        },
        onMediumWidthExpandedHeight = {
            ModalBottomSheetTimeline(
                show = show,
                password = password
            )
        },
        onMediumSizeClass = {
            ContainedTimeline(
                show = show,
                password = password
            )
        },
        onCompactSizeClass = {
            ModalBottomSheetTimeline(
                show = show,
                password = password
            )
        }
    )
}

@Composable
@NonRestartableComposable
@ResponsiveClassComponent(
    classes = [EXPANDED_CONTENT, MEDIUM_CONTENT]
)
private fun ContainedTimeline(
    show: MutableState<Boolean>,
    password: Password,
) {
    AnimatedVisibility(
        visible = show.value
    ) {
        PasswordTimelineContent(
            password = password
        )
    }
}

@Composable
@NonRestartableComposable
@ResponsiveClassComponent(
    classes = [MEDIUM_EXPANDED_CONTENT, COMPACT_CONTENT]
)
private fun ModalBottomSheetTimeline(
    show: MutableState<Boolean>,
    password: Password,
) {
    val state = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()
    if (show.value) {
        scope.launch {
            state.show()
        }
    }
    if (state.isVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
                    show.value = false
                    state.hide()
                }
            }
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        all = 16.dp
                    ),
                text = password.tail,
                style = AppTypography.titleLarge
            )
            PasswordTimelineContent(
                password = password
            )
        }
    }
}

@Composable
@NonRestartableComposable
private fun PasswordTimelineContent(
    password: Password,
) {
    val events = remember { password.events }
    HorizontalDivider()
    JetLimeColumn(
        modifier = Modifier
            .heightIn(
                max = 500.dp
            ),
        itemsList = ItemsList(events),
        key = { _, event -> event.id },
        contentPadding = PaddingValues(
            all = 16.dp
        )
    ) { _, event, position ->
        PasswordEvent(
            event = event,
            position = position
        )
    }
}

@Composable
@NonRestartableComposable
private fun PasswordEvent(
    event: PasswordEvent,
    position: EventPosition,
) {
    JetLimeEvent(
        style = JetLimeEventDefaults.eventStyle(
            position = position
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 10.dp
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        all = 12.dp
                    )
            ) {
                PasswordEventTypeBadge(
                    type = event.type
                )
                Text(
                    modifier = Modifier
                        .padding(
                            top = 5.dp
                        ),
                    text = stringResource(
                        resource = event.type.text(),
                        event.eventDate.toDateString(
                            pattern = EUROPEAN_DATE_PATTERN
                        ),
                        event.eventDate.toDateString(
                            pattern = H24_HOURS_MINUTES_SECONDS_PATTERN
                        )
                    ),
                    style = AppTypography.labelLarge
                )
            }
        }
    }
}

@Composable
private fun PasswordEventType.text(): StringResource {
    return when (this) {
        GENERATED -> Res.string.password_generated_event
        INSERTED -> Res.string.password_inserted_event
        COPIED -> Res.string.password_copied_event
        EDITED -> Res.string.password_edited_event
        REFRESHED -> Res.string.password_refreshed_event
    }
}