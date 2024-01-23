package com.example.todoapp.presentation.compose.custom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.room.entity.ToDoData
import com.example.todoapp.presentation.compose.components.ButtonWithIconCustom
import com.example.todoapp.presentation.compose.components.TextFieldCustom
import com.example.todoapp.ui.theme.Dimension
import com.example.todoapp.ui.theme.Typography


@Composable
fun AddToDosCard(
    userId:Int?= null,
    onClickClose: () -> Unit,
    onClickAdd: (ToDoData) -> Unit

    ) {

    var textFieldUserId by remember {
        mutableStateOf(TextFieldValue(userId?.toString() ?: ""))
    }

    var textFieldToDo by remember {
        mutableStateOf(TextFieldValue())
    }

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(Dimension.legendPadding),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimension.legendPadding),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                modifier = Modifier
                    .padding(start = Dimension.legendPadding),
                text = "ADD TODO",
                style = Typography.titleLarge,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
            Icon(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(5.dp)
                    .clickable {
                        onClickClose()
                    },
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "close Btn",
                tint = MaterialTheme.colors.error
            )
        }

        TextFieldCustom(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            fieldName = "User Id",
            isTextFieldEditable = { userId == null },
            value = { textFieldUserId },
            onValueChange = {
                textFieldUserId =it
            }
        )
        TextFieldCustom(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            fieldName = "ToDo",
            isTextFieldEditable = { true },
            value = { textFieldToDo },
            onValueChange = {
                textFieldToDo = it
            }
        )
        ButtonWithIconCustom(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            text = "Add",
            isEnabled = { true }) {
            if (textFieldToDo.text.isNotBlank() && textFieldUserId.text.isNotBlank()
            ) {
                onClickAdd(
                    ToDoData(
                        serialNumber = 0,
                        id = 1,
                        todo = textFieldToDo.text,
                        completed = false,
                        userId = textFieldUserId.text.toInt(),
                        isDeleted = false,
                        deletedOn = ""
                    )

                )
            }
        }
    }

}
