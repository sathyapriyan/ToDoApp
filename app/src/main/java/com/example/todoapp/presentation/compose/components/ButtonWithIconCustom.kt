package com.example.todoapp.presentation.compose.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.todoapp.ui.theme.Purple40
import com.example.todoapp.ui.theme.PurpleGrey80

@Composable
fun ButtonWithIconCustom(
    modifier: Modifier,
    text: String,
    icon: Int? = null,
    cornerRoundness: Dp = 30.dp,
    backgroundColor: Color = Purple40,
    textColor: Color = Color.White,
    isEnabled: () -> Boolean,
    onClick: () -> Unit
) {

    Button(
        modifier = modifier,
        onClick = { onClick() },
        shape = RoundedCornerShape(cornerRoundness),
        contentPadding = PaddingValues(2.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            disabledBackgroundColor = backgroundColor.copy(
                alpha = 0.5F
            ),
            disabledContentColor = PurpleGrey80
        ),
        enabled = isEnabled()
    ) {

        Text(
            modifier = Modifier.padding(
                top = 10.dp,
                bottom = 10.dp,
                end = 20.dp,
                start = 20.dp
            ),
            text = text,
            color = textColor
        )

        if (icon != null) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Next Icon",
                tint = textColor
            )
        }

    }

}