package com.tecknobit.glider.ui.screens.generate.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tecknobit.equinoxcore.annotations.FutureEquinoxApi

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
    quantityIndicatorStyle: TextStyle = TextStyle.Default,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
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
    icon: ImageVector = Icons.Default.ExpandLess,
) {
    QuantityButton(
        modifier = modifier,
        shape = shape,
        size = size,
        icon = icon,
        quantityAction = {
            state.decrement()
        }
    )
}

@Composable
@NonRestartableComposable
private fun IncrementButton(
    state: QuantityPickerState,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    size: Dp = 30.dp,
    icon: ImageVector = Icons.Default.ExpandLess,
) {
    QuantityButton(
        modifier = modifier,
        shape = shape,
        size = size,
        icon = icon,
        quantityAction = {
            state.increment()
        }
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
) {
    Button(
        modifier = modifier
            .size(size),
        shape = shape,
        onClick = quantityAction
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
    }
}

@Composable
fun rememberQuantityPickerState(
    initialQuantity: Int = 0,
    negativeValues: Boolean = true,
    minQuantity: Int = Int.MIN_VALUE,
    maxQuantity: Int = Int.MAX_VALUE,
): QuantityPickerState {
    val quantityPickerState = rememberSaveable(
        stateSaver = QuantityPickerSaver
    ) {
        mutableStateOf(
            QuantityPickerState(
                initialQuantity = initialQuantity
            )
        )
    }
    return quantityPickerState.value
}

class QuantityPickerState internal constructor(
    val initialQuantity: Int,
    val negativeValues: Boolean = true,
    val minQuantity: Int = Int.MIN_VALUE,
    val maxQuantity: Int = Int.MAX_VALUE,
) {

    val quantity = mutableStateOf(initialQuantity)

    fun increment() {
        if ((quantity.value + 1) <= maxQuantity)
            quantity.value++
    }

    fun decrement() {
        val decrementedQuantity = quantity.value - 1
        if (decrementedQuantity >= minQuantity || (!negativeValues && decrementedQuantity >= 0))
            quantity.value--
    }

}

internal object QuantityPickerSaver : Saver<QuantityPickerState, Array<Any>> {

    /**
     * Convert the restored value back to the original Class. If null is returned the value will
     * not be restored and would be initialized again instead.
     */
    override fun restore(
        value: Array<Any>,
    ): QuantityPickerState {
        return QuantityPickerState(
            value[0] as Int,
            value[1] as Boolean,
            value[2] as Int,
            value[3] as Int
        )
    }

    /**
     * Convert the value into a saveable one. If null is returned the value will not be saved.
     */
    override fun SaverScope.save(
        value: QuantityPickerState,
    ): Array<Any> {
        return arrayOf(
            value.initialQuantity,
            value.negativeValues,
            value.minQuantity,
            value.maxQuantity
        )
    }

}