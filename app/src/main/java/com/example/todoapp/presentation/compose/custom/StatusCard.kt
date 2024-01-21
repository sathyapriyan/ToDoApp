package com.example.todoapp.presentation.compose.custom

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.todoapp.ui.theme.Dimension
import com.example.todoapp.ui.theme.GreenApp
import com.example.todoapp.ui.theme.VioletApp


@Composable
fun StatusTypeLegend(
    status: String,
    value: Int
) {

    Row(
        modifier = Modifier
            .padding(Dimension.legendPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(Dimension.legendSquareSize)
                .background(color = getColor(status = status))
        )

        Text(
            modifier = Modifier
                .padding(start = Dimension.legendPadding),
            text = status,
            //style = Typography.labelSmall,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black
        )

        Text(
            modifier = Modifier
                .padding(start = Dimension.legendPadding),
            text = " ($value)",
            //style = Typography.labelSmall,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black
        )

    }

}

fun getColor(status: String): Color {
    return when (status) {
        "Pending" -> VioletApp
        "completed" -> GreenApp
        else -> Color.Black
    }
}