package com.example.todoapp.presentation.screen.user

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.todoapp.R
import com.example.todoapp.core.util.CommonUtil
import com.example.todoapp.data.room.entity.ToDoData
import com.example.todoapp.presentation.compose.components.TopBarApp
import com.example.todoapp.presentation.compose.custom.AddToDosCard
import com.example.todoapp.presentation.compose.custom.ItemToDosCard
import com.example.todoapp.presentation.compose.custom.ProgressBar
import com.example.todoapp.presentation.compose.custom.StatusTypeLegend
import com.example.todoapp.presentation.compose.custom.UpdateToDosCard
import com.example.todoapp.ui.theme.Dimension
import com.example.todoapp.ui.theme.GreenApp
import com.example.todoapp.ui.theme.Typography
import com.example.todoapp.ui.theme.VioletApp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun UserView(
    navHostController: NavHostController,
    viewModel: UserViewModel = hiltViewModel(),
    userId:String
){

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var completedListSize by remember {
        mutableIntStateOf(0)
    }
    var inCompletedListSize by remember {
        mutableIntStateOf(0)
    }
    val toDoListByUserId = remember {
        mutableStateListOf<ToDoData>()
    }


    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }

    val toDosByUserId by viewModel.loadToDosByUserId.collectAsState()


    val statusItems = remember {
        mutableMapOf(
            Pair("completed", completedListSize),
            Pair("Pending", inCompletedListSize)
        )

    }
    var todoUpdate by remember {
        mutableStateOf<ToDoData>(
            ToDoData(
                serialNumber = 0,
                id = 0,
                todo = "",
                completed = false,
                userId = 0,
                isDeleted = false,
                deletedOn = ""
            )
        )
    }
    var bottomSheetState by remember {
        mutableStateOf(0)
    }
    val isInterNetAvailable = CommonUtil.hasInternetConnection(context =context)

    LaunchedEffect(Unit){
        viewModel.getToDoListByUserId(inNetwork = isInterNetAvailable,userId = userId.toInt())

    }


    LaunchedEffect(key1 = toDosByUserId, block = {
        println("ToDo Test -->  userIdListResponse  $toDosByUserId")
        toDoListByUserId.apply { toDosByUserId.data?.let { addAll(it) } }
        completedListSize = toDosByUserId.data?.filter { it.completed }?.size ?: 0
        inCompletedListSize = toDosByUserId.data?.filter { it.completed.not() }?.size ?: 0
    })

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopBarApp(
                title = stringResource(id = R.string.title_dashboard)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                bottomSheetState = 0
                scope.launch {
                    println("ToDo Test bottomSheetState ----->  ${sheetState.isVisible}")
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        showBottomSheet = false
                    }
                    scope.launch { sheetState.show() }.invokeOnCompletion {
                        showBottomSheet = true
                    }
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add Button"
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(Dimension.containerPadding)
                .background(color = if (isSystemInDarkTheme()) Color.Black else Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .weight(2f)
                    .padding(Dimension.textPadding),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(Dimension.cardCornerRadius),
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = Dimension.textPadding,
                                end = Dimension.textPadding
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(Dimension.textPadding),
                            text = "Total ${toDoListByUserId.size}",
                            style = Typography.bodySmall,
                            color = if (isSystemInDarkTheme()) Color.White else Color.Black
                        )
                        Text(
                            modifier = Modifier
                                .padding(Dimension.textPadding),
                            text = "$completedListSize of ${toDoListByUserId.size}Completed",
                            style = Typography.bodySmall,
                            color = if (isSystemInDarkTheme()) Color.White else Color.Black
                        )
                    }
                    ProgressBar(
                        modifier = Modifier
                            .padding(0.dp)
                            .padding(Dimension.progressBarPadding),
                        values = listOf(
                            Pair(completedListSize, GreenApp),
                            Pair(inCompletedListSize, VioletApp)
                        ),
                        lineOrBar = 1
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        //maxItemsInEachRow = 2,
                        // verticalArrangement = Arrangement.Center,
                        horizontalArrangement = Arrangement.Center
                    ) {


                        statusItems.forEach {
                            StatusTypeLegend(
                                status = it.key,
                                value = it.value
                            )
                        }

                    }


                }
            }
            Divider()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(8f)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimension.profileCardPadding)
                ) {
                    items(toDoListByUserId.size) {
                        ItemToDosCard(
                            data = ToDoData(
                                id = toDoListByUserId[it].id,
                                todo = toDoListByUserId[it].todo,
                                completed = toDoListByUserId[it].completed,
                                userId = toDoListByUserId[it].userId,
                                serialNumber = toDoListByUserId[it].serialNumber,
                                isDeleted = toDoListByUserId[it].isDeleted,
                                deletedOn = toDoListByUserId[it].deletedOn
                            ), onClick = {
                                bottomSheetState = 1
                                todoUpdate = it
                                showBottomSheet = true

                            }
                        )
                    }

                }

            }


        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ){
                when(bottomSheetState){
                    0->{
                        AddToDosCard(onClickClose ={showBottomSheet=false},
                            onClickAdd = {
                                showBottomSheet = false
                                viewModel.saveToDo(inNetwork = isInterNetAvailable, todo = it)
                                Toast.makeText(
                                    context,
                                    "ToDo Saved",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            userId = todoUpdate.userId

                        )

                    }
                    1->{
                        UpdateToDosCard(onClickClose ={showBottomSheet=false},
                            data = todoUpdate,
                            onClickUpdate = {
                                showBottomSheet = false
                                viewModel.updateToDo(inNetwork = isInterNetAvailable, todo = it)
                                Toast.makeText(
                                    context,
                                    "ToDo Updated",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onClickDelete = {
                                showBottomSheet = false
                                viewModel.deleteToDo(inNetwork = isInterNetAvailable, todo = it)
                                Toast.makeText(
                                    context,
                                    "ToDo Deleted",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        )

                    }
                }
            }
        }

    }

}