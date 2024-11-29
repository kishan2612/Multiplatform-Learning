package org.kmp.learning

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MoonRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    size: Dp = 24.dp,
) {
    // Animated color transitions for smooth state changes
    val currentColor by animateColorAsState(
        targetValue = if (selected) Color.Blue else Color.Gray
    )

    val density = LocalDensity.current
    val strokeWidthPx = with(density) { 1.5.dp.toPx() }

    Box(
        modifier = Modifier
            .size(size)
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton,

                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    bounded = true,
                    radius = 4.dp,
                    color = Color.Blue.copy(alpha = 0.4f)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Draw the outer circle (border)
            drawCircle(
                color = if (selected) Color.Blue else Color.Gray,
                radius = size.toPx() / 2,
                center = Offset(size.toPx() / 2, size.toPx() / 2),
                style = Stroke(width = strokeWidthPx)
            )
            if (selected) {
                // Draw the inner filled circle
                drawCircle(
                    color = currentColor,
                    radius = (size.toPx() / 2) * 0.6f,
                    center = Offset(size.toPx() / 2, size.toPx() / 2)
                )
            }
        }
    }
}
