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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.room.entity.ToDoData
import com.example.todoapp.presentation.compose.components.ButtonWithIconCustom
import com.example.todoapp.presentation.compose.components.RadioGroup
import com.example.todoapp.presentation.compose.components.TextFieldCustom
import com.example.todoapp.ui.theme.Dimension
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.ui.theme.Typography


@Composable
fun UpdateToDosCard(
    data:ToDoData,
    onClickClose: () -> Unit,
    onClickUpdate: (ToDoData) -> Unit,
    onClickDelete: (ToDoData) -> Unit

    ) {
    val context = LocalContext.current

    var textFieldUserId by remember {
        mutableStateOf(TextFieldValue(data.userId.toString()))
    }
    var textFieldToDo by remember {
        mutableStateOf(TextFieldValue(data.todo))
    }
    val isCompletedOptions by remember {
        mutableStateOf(context.resources.getStringArray(R.array.is_completed_options).toList())
    }
    var isCompleted by remember {
        mutableStateOf(data.completed)
    }
    var isCompletedModeSelected by remember {
        mutableStateOf(
            if(isCompleted) context.resources.getString(R.string.completed)
            else context.resources.getString(R.string.pending))
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier
                    .padding(start = Dimension.legendPadding),
                text = "Update",
                style = Typography.titleLarge,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
            Icon(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(10.dp)
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
            isTextFieldEditable = { false },
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
            isTextFieldEditable = { false },
            value = { textFieldToDo },
            onValueChange = {
                textFieldToDo = it
            }
        )
        RadioGroup(
            modifier = Modifier.padding(5.dp),
            groupTitle = "Is Completed",
            contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
            items = isCompletedOptions,
            selected = { isCompletedModeSelected },
            onSelected = { isCompletedModeSelected = it
                when(it){
                    context.getString(R.string.completed)->{
                        isCompleted = true
                    }
                    context.getString(R.string.pending)->{
                        isCompleted = false

                    }
                    else->{

                    }
                }
            }
        )

        Row {
            ButtonWithIconCustom(
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp),
                text = "Update",
                isEnabled = { true }) {
                if (textFieldToDo.text.isNotBlank() && textFieldUserId.text.isNotBlank()
                ) {
                    onClickUpdate(
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
            ButtonWithIconCustom(
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp),
                text = "Delete",
                isEnabled = { true }) {
                if (textFieldToDo.text.isNotBlank() && textFieldUserId.text.isNotBlank()
                ) {
                    onClickDelete(
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

}
@Preview(showBackground = true)
@Composable
fun AddToDosCardPreview() {
    ToDoAppTheme {
      //  AddToDosCard()
    }
}