package com.billion_dollor_company.notesapp.ui.screen.tasks.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CircleDot(
    color: Color,
    modifier: Modifier = Modifier
) {
    val dotSize = 14

    Canvas(
        modifier = modifier
            .padding(top = 5.dp)
            .size(dotSize.dp)
            ,
        onDraw = {
            val size = dotSize.dp.toPx()
            drawCircle(
                color = color,
                radius = size / 2f
            )
        })
}