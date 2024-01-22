package com.example.todoapp.presentation.compose.custom

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.ui.theme.Dimension
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.ui.theme.Typography


@Composable
fun AddToDosCard() {


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
                style = Typography.bodyMedium,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
            Text(
                modifier = Modifier
                    .padding(start = Dimension.legendPadding),
                text = "clse",
                style = Typography.bodyMedium,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
        }

        Text(
            modifier = Modifier
                .padding(start = Dimension.profileCardPadding),
            text = "Enter User Id",
            style = Typography.bodyMedium,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black
        )
        Text(
            modifier = Modifier
                .padding(start = Dimension.profileCardPadding),
            text = "Add ToDo",
            style = Typography.bodyMedium,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black
        )
        Text(
            modifier = Modifier
                .padding(start = Dimension.profileCardPadding),
            text = "select",
            style = Typography.bodyMedium,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black
        )
        Button(onClick = { }) {
            Text("Add")
        }
    }

}
@Preview(showBackground = true)
@Composable
fun AddToDosCardPreview() {
    ToDoAppTheme {
        AddToDosCard()
    }
}