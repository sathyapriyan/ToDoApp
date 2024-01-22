package com.example.todoapp.presentation.compose.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R
import com.example.todoapp.ui.theme.LightBlueApp
import com.example.todoapp.ui.theme.RedApp

///////////////////////////////////////////////////////////////////////////////////////////////

// Created by Srinivasan Jayakumar on 10.January.2023:11:10

///////////////////////////////////////////////////////////////////////////////////////////////

@Composable
fun TextFieldCustom(
    modifier: Modifier,
    fieldName: String,
    isTextFieldEditable: () -> Boolean,
    textColor: Color = Color.Black,
    isError: () -> Boolean = { false },
    errorText: String = "Test error",
    value: () -> TextFieldValue,
    maxChar: Int = 100,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    onValueChange: (newValue: TextFieldValue) -> Unit,
    onDone: () -> Unit = {},
    onNext: () -> Unit = {}
) {

    var isTextFieldFocused by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .onFocusEvent {
                    isTextFieldFocused = it.isFocused
                },
            value = value(),
            onValueChange = {
                if (isTextFieldEditable()
                    && maxChar >= it.text.length
                ) {
                    onValueChange(it)
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            textStyle = TextStyle.Default.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            ),
            label = { Text(text = fieldName) },
            placeholder = { Text(text = "Enter $fieldName") },
            enabled = isTextFieldEditable(),
            colors = TextFieldDefaults.textFieldColors(
                textColor = textColor,
                backgroundColor = Color.White,
                unfocusedIndicatorColor = RedApp,
                focusedIndicatorColor = LightBlueApp,
                focusedLabelColor = LightBlueApp,
                cursorColor = LightBlueApp,
                disabledIndicatorColor = Color.Gray,
                disabledTextColor = textColor
            ),
            keyboardActions = KeyboardActions(
                onDone = { onDone() },
                onNext = { onNext() }
            )
        )

        Box(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()) {

            if (isError()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(5.dp)
                        .align(Alignment.CenterStart),
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
                            .padding(5.dp),
                        text = errorText,
                        fontSize = 14.sp,
                        color = MaterialTheme.colors.error
                    )

                }
            }

            if (maxChar != 100
                && value().text.isNotEmpty()
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {

                    Text(
                        modifier = Modifier
                            .wrapContentSize(),
                        text = value().text.length.toString(),
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Text(
                        modifier = Modifier
                            .wrapContentSize(),
                        text = " / $maxChar",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                }
            }

        }

    }

}