@file:OptIn(ExperimentalFoundationApi::class)

package com.tecknobit.glider.ui.screens.generate.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi
import com.tecknobit.equinoxcore.annotations.Wrapper

@Composable
@NonRestartableComposable
@FutureEquinoxApi(
    protoBehavior = """
        Actually the component is scoped to password length, but will be generalize for general purpose
    """,
    releaseVersion = "1.10.0",
    additionalNotes = """
        - Allow more customization than the actually implemented (if needed)
        - Allow more customization than the actually implemented also for the related components 
          such QuantityButton, etc... (if needed)
        - Allow a wrong quantity scenario with error text or similar  
        - Allow negative values
        - Allow to insert a max value
        - Instead using quantityButtonModifier think about a QuantityButtonDefault etc..
    """
)
fun QuantityPicker(
    modifier: Modifier = Modifier,
    quantityButtonModifier: Modifier = Modifier,
    state: QuantityPickerState,
    informativeText: String? = null,
    informativeTextStyle: TextStyle = TextStyle.Default,
    quantityIndicatorStyle: TextStyle = TextStyle.Default,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        informativeText?.let {
            Text(
                text = informativeText,
                style = informativeTextStyle
            )
        }
        DecrementButton(
            state = state,
            modifier = quantityButtonModifier
        )
        Text(
            text = state.quantity.value.toString(),
            style = quantityIndicatorStyle
        )
        IncrementButton(
            state = state,
            modifier = quantityButtonModifier
        )
    }
}

@Composable
@NonRestartableComposable
private fun DecrementButton(
    state: QuantityPickerState,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    size: Dp = 30.dp,
    icon: ImageVector = Icons.Default.Remove,
) {
    QuantityButton(
        modifier = modifier,
        shape = shape,
        size = size,
        icon = icon,
        quantityAction = {
            state.simpleDecrement()
        },
        longPressQuantityAction = if (state.longPressEnabled) {
            {
                state.longDecrement()
            }
        } else
            null
    )
}

@Composable
@NonRestartableComposable
private fun IncrementButton(
    state: QuantityPickerState,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    size: Dp = 30.dp,
    icon: ImageVector = Icons.Default.Add,
) {
    QuantityButton(
        modifier = modifier,
        shape = shape,
        size = size,
        icon = icon,
        quantityAction = {
            state.simpleIncrement()
        },
        longPressQuantityAction = if (state.longPressEnabled) {
            {
                state.longIncrement()
            }
        } else
            null
    )
}

@Composable
@NonRestartableComposable
private fun QuantityButton(
    modifier: Modifier,
    shape: Shape,
    size: Dp,
    icon: ImageVector,
    quantityAction: () -> Unit,
    longPressQuantityAction: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(MaterialTheme.colorScheme.primary)
            .combinedClickable(
                onClick = quantityAction,
                onDoubleClick = longPressQuantityAction,
                onLongClick = longPressQuantityAction
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun rememberQuantityPickerState(
    initialQuantity: Int = 0,
    minQuantity: Int = Int.MIN_VALUE,
    maxQuantity: Int = Int.MAX_VALUE,
    longPressQuantity: Int? = null,
): QuantityPickerState {
    val quantityPickerState = rememberSaveable(
        stateSaver = QuantityPickerSaver
    ) {
        mutableStateOf(
            QuantityPickerState(
                initialQuantity = initialQuantity,
                minQuantity = minQuantity,
                maxQuantity = maxQuantity,
                longPressQuantity = longPressQuantity
            )
        )
    }
    return quantityPickerState.value
}

class QuantityPickerState internal constructor(
    val initialQuantity: Int,
    val minQuantity: Int = Int.MIN_VALUE,
    val maxQuantity: Int = Int.MAX_VALUE,
    val longPressQuantity: Int? = null,
) {

    val quantity = mutableStateOf(initialQuantity)

    val longPressEnabled = longPressQuantity != null && longPressQuantity > 1

    @Wrapper
    fun longIncrement() {
        require(longPressQuantity != null && longPressQuantity > 1)
        increment(
            incrementValue = longPressQuantity
        )
    }

    @Wrapper
    fun simpleIncrement() {
        increment(
            incrementValue = 1
        )
    }

    private fun increment(
        incrementValue: Int,
    ) {
        val incrementedQuantity = quantity.value + incrementValue
        if (incrementedQuantity <= maxQuantity)
            quantity.value += incrementValue
    }

    @Wrapper
    fun longDecrement() {
        require(longPressQuantity != null && longPressQuantity > 1)
        decrement(
            decrementValue = longPressQuantity
        )
    }

    @Wrapper
    fun simpleDecrement() {
        decrement(
            decrementValue = 1
        )
    }

    private fun decrement(
        decrementValue: Int,
    ) {
        val decrementedQuantity = quantity.value - decrementValue
        if (decrementedQuantity >= minQuantity)
            quantity.value -= decrementValue
    }

}

internal object QuantityPickerSaver : Saver<QuantityPickerState, Array<Int?>> {

    /**
     * Convert the restored value back to the original Class. If null is returned the value will
     * not be restored and would be initialized again instead.
     */
    override fun restore(
        value: Array<Int?>,
    ): QuantityPickerState {
        return QuantityPickerState(
            initialQuantity = value[0]!!,
            minQuantity = value[1]!!,
            maxQuantity = value[2]!!,
            longPressQuantity = value[3]
        )
    }

    /**
     * Convert the value into a saveable one. If null is returned the value will not be saved.
     */
    override fun SaverScope.save(
        value: QuantityPickerState,
    ): Array<Int?> {
        return arrayOf(
            value.initialQuantity,
            value.minQuantity,
            value.maxQuantity,
            value.longPressQuantity
        )
    }

}