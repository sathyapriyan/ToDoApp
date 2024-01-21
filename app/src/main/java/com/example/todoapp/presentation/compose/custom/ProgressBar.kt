package com.example.todoapp.presentation.compose.custom

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.R
import com.example.todoapp.ui.theme.Dimension
import com.example.todoapp.ui.theme.GreenApp
import com.example.todoapp.ui.theme.LightBlueApp
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.ui.theme.VioletApp

@Composable
fun ProgressBar(
    modifier: Modifier,
    values: List<Pair<Int, Color>>
) {

    println("Inside ProgressBar")

    val animateFloat = remember { Animatable(0f) }
    LaunchedEffect(animateFloat) {
        animateFloat.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
        )
    }

    Box(
        modifier = modifier
    ) {

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimension.progressBarHeight),
            contentDescription = stringResource(id = R.string.desc_status_progress_bar),
            onDraw = {

                val width = size.width
                var occupiedWidth = 0F

                repeat(values.size) {

                    val currentWidth = (
                            values[it].first.toDouble() / values.sumOf { it.first } * width
                            ).toFloat()

                    if (it == 0 || it == values.size.minus(1)){

                        val path = Path().apply {
                            addRoundRect(
                                RoundRect(
                                    rect = Rect(
                                        offset = Offset(occupiedWidth, 0F),
                                        size = Size(
                                            animateFloat.value * currentWidth,
                                            Dimension.progressBarHeight.toPx()
                                        )
                                    ),
                                    topLeft = if(it == 0) CornerRadius(Dimension.cornerRadius, Dimension.cornerRadius) else CornerRadius.Zero,
                                    bottomLeft = if(it == 0) CornerRadius(Dimension.cornerRadius, Dimension.cornerRadius) else CornerRadius.Zero,
                                    topRight = if(it == 0) CornerRadius.Zero else CornerRadius(Dimension.cornerRadius, Dimension.cornerRadius),
                                    bottomRight = if(it == 0) CornerRadius.Zero else CornerRadius(Dimension.cornerRadius, Dimension.cornerRadius)
                                )
                            )
                        }

                        drawPath(
                            path = path,
                            color = values[it].second
                        )

                    } else {

                        drawRect(
                            color = values[it].second,
                            topLeft = Offset(
                                occupiedWidth,
                                0F
                            ),
                            size = Size(
                                animateFloat.value * currentWidth,
                                Dimension.progressBarHeight.toPx()
                            )
                        )

                    }

                    occupiedWidth += currentWidth

                }

            }
        )

    }

}

@Preview
@Composable
fun ProgressBarPreview() {
    ToDoAppTheme {
        ProgressBar(
            modifier = Modifier,
            values = listOf(
                Pair(2, GreenApp),
                Pair(2, VioletApp)
                )
        )
    }
}