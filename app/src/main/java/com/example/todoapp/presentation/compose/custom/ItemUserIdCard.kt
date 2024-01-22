package com.example.todoapp.presentation.compose.custom

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.data.room.entity.ToDoData
import com.example.todoapp.ui.theme.Dimension
import com.example.todoapp.ui.theme.GreenApp
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.ui.theme.VioletApp
import com.example.todoapp.ui.theme.Typography


@Composable
fun ItemUserIdCard(
    data: ToDoData,
    totalListSize: Int,
    completedListSize: Int,
    inCompletedListSize: Int
) {


    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(Dimension.containerPadding)
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
                text = "User Id ${data.userId}",
                style = Typography.bodyMedium,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
            Text(
                modifier = Modifier
                    .padding(start = Dimension.legendPadding),
                text = "Id ${data.id}",
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
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = Dimension.textPadding,
                        end = Dimension.textPadding
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    modifier = Modifier
                        .padding(Dimension.textPadding),
                    text = "Total $totalListSize",
                    style = Typography.bodySmall,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black
                )
                Text(
                    modifier = Modifier
                        .padding(Dimension.textPadding),
                    text = "$completedListSize of $inCompletedListSize Completed",
                    style = Typography.bodySmall,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black
                )
            }
            ProgressBar(
                modifier = Modifier,
                values = listOf(
                    Pair(completedListSize, GreenApp),
                    Pair(inCompletedListSize, VioletApp)
                ),
                lineOrBar=0
            )

        }



    }

}
@Preview(showBackground = true)
@Composable
fun ItemUserIdCardPreview() {
    ToDoAppTheme {
        ItemUserIdCard(
            data = ToDoData(
                id = 15,
                todo = "o something nice for someone I care about",
                completed = false,
                userId = 5,
                serialNumber = 0,
                isDeleted = false,
                deletedOn = ""
            ),
            totalListSize = 10,
            completedListSize = 3,
            inCompletedListSize = 7
        )
    }
}