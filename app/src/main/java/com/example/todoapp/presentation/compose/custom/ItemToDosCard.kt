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
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.room.entity.ToDoData
import com.example.todoapp.ui.theme.Dimension
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.ui.theme.Typography


@Composable
fun ItemToDosCard(
    data: ToDoData
) {


    Row {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(Dimension.legendPadding)
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
                    text = "User Id  ${data.userId}",
                    style = Typography.bodyMedium,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black
                )
                Text(
                    modifier = Modifier
                        .padding(start = Dimension.legendPadding),
                    text = "Id  ${data.id}",
                    style = Typography.bodyMedium,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black
                )
            }

            Text(
                modifier = Modifier
                    .padding(start = Dimension.profileCardPadding),
                text = data.todo,
                style = Typography.bodyMedium,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )

        }

    }

}
@Preview(showBackground = true)
@Composable
fun ItemToDosCardPreview() {
    ToDoAppTheme {
        ItemToDosCard(
            data = ToDoData(
                id = 15,
                todo = "o something nice for someone I care about",
                completed = false,
                userId = 5,
                serialNumber = 0,
                isDeleted = false,
                deletedOn = ""
            )
        )
    }
}