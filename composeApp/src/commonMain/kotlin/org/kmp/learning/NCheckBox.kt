package org.kmp.learning

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.launch

enum class CheckboxState {
    SELECTED,
    UNSELECTED,
    PARTIAL
}

@Composable
fun MoonCheckbox(
    state: CheckboxState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
) {
    val borderWidth: Dp = 1.5.dp
    val borderColor: Color = Color.LightGray
    val selectedColor: Color = MaterialTheme.colors.primary
    val unselectedColor: Color = Color.Gray
    // Animated color transitions for smooth state changes

    val currentColor by animateColorAsState(
        targetValue = when (state) {
            CheckboxState.SELECTED -> selectedColor
            CheckboxState.PARTIAL -> Color(0xFFBDBDBD) // Partial color (gray)
            CheckboxState.UNSELECTED -> unselectedColor
        }
    )

    // Animated scale for selection
    val scale by animateFloatAsState(
        targetValue = if (state == CheckboxState.SELECTED) 1.1f else 1f
    )

    val density = LocalDensity.current
    val strokeWidthPx = with(density) { borderWidth.toPx() }

    Box(
        modifier = modifier
            .size(size)
            .scale(scale) // Apply the scale animation
            .selectable(
                selected = state != CheckboxState.UNSELECTED,
                onClick = onClick,
                role = Role.Checkbox,
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
            // Draw the outer square (border)
            drawRoundRect(
                color = borderColor,
                size = Size(size.toPx(), size.toPx()),
                cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
                style = Stroke(width = strokeWidthPx)
            )

            // Handle different states (selected, partial, unselected)
            when (state) {
                CheckboxState.SELECTED -> {
                    // Draw the inner filled area (for selected state)
                    drawRoundRect(
                        color = currentColor,
                        size = Size(size.toPx(), size.toPx()),
                        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                    )
                    // Draw check mark symbol (tick)
                    val tickStrokeWidth = size.toPx() * 0.1f
                    drawLine(
                        color = Color.White,
                        start = Offset(size.toPx() * 0.2f, size.toPx() * 0.5f),
                        end = Offset(size.toPx() * 0.4f, size.toPx() * 0.7f),
                        strokeWidth = tickStrokeWidth
                    )
                    drawLine(
                        color = Color.White,
                        start = Offset(size.toPx() * 0.4f, size.toPx() * 0.7f),
                        end = Offset(size.toPx() * 0.8f, size.toPx() * 0.3f),
                        strokeWidth = tickStrokeWidth
                    )
                }

                CheckboxState.PARTIAL -> {
                    // Draw partial indication: a small horizontal line
                    drawLine(
                        color = Color.Gray,
                        start = Offset(size.toPx() * 0.2f, size.toPx() * 0.5f),
                        end = Offset(size.toPx() * 0.8f, size.toPx() * 0.5f),
                        strokeWidth = size.toPx() * 0.1f
                    )
                }

                CheckboxState.UNSELECTED -> {
                    // No filled area in the unselected state
                }
            }
        }
    }
}
