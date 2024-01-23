package com.example.todoapp.presentation.compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R
import com.example.todoapp.ui.theme.ToDoAppTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RadioGroup(
    modifier: Modifier,
    groupTitle: String,
    isMandatory: Boolean = true,
    contentColor: Color,
    items: List<String>,
    isError: () -> Boolean = { false },
    errorText: String = "Test error",
    selected: () -> String,
    onSelected: (selected: String) -> Unit
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val indication = rememberRipple(
        bounded = false,
        color = if (isSystemInDarkTheme()) Color.Black else Color.White
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(
                        top = 2.dp,
                        start = 2.dp,
                        end = 2.dp,
                        bottom = 0.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                Text(
                    modifier = Modifier
                        .wrapContentSize(),
                    text = groupTitle,
                    textAlign = TextAlign.Start,
                    color = contentColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                if (isMandatory.not()) {
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 5.dp),
                        text = "( Optional )",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

            }

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.Start,
            ) {

                items.forEachIndexed { _, item ->

                    Row(
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(5.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .indication(
                                interactionSource,
                                indication = indication
                            )
                            .clickable {
                                onSelected(item)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        RadioButton(
                            selected = selected() == item,
                            onClick = null,
                            enabled = true,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = contentColor
                            )
                        )

                        Text(
                            text = item,
                            fontSize = 14.sp,
                            color = contentColor,
                            modifier = Modifier.padding(start = 2.dp, end = 2.dp)
                        )

                    }

                }

            }

            if (isError()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {

                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.ic_info),
                        contentDescription = "Warning Icon",
                        tint = MaterialTheme.colors.error
                    )

                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(vertical = 5.dp),
                        text = errorText,
                        fontSize = 14.sp,
                        color = MaterialTheme.colors.error
                    )

                }

            }

        }

    }

}

@Preview
@Composable
fun RadioGroupPreview() {

    ToDoAppTheme {
        RadioGroup(
            modifier = Modifier.fillMaxWidth(),
            items = listOf("Running", "Maximum", "Running2", "Maximum2"),
            groupTitle = "Test Type",
            contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
            selected = { "Running" },
            onSelected = {})
    }

}