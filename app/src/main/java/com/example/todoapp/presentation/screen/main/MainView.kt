package com.example.todoapp.presentation.screen.main

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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.todoapp.R
import com.example.todoapp.core.navigation.Screen
import com.example.todoapp.core.util.CommonUtil
import com.example.todoapp.core.util.StateHandler
import com.example.todoapp.data.room.entity.ToDoData
import com.example.todoapp.presentation.compose.components.HorizontalPagerApp
import com.example.todoapp.presentation.compose.custom.ProgressBar
import com.example.todoapp.presentation.compose.custom.StatusTypeLegend
import com.example.todoapp.ui.theme.Dimension
import com.example.todoapp.ui.theme.GreenApp
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.ui.theme.VioletApp
import com.example.todoapp.ui.theme.Typography
import com.example.todoapp.presentation.compose.components.TabRowApp
import com.example.todoapp.presentation.compose.components.TopBarApp
import com.example.todoapp.presentation.compose.custom.AddToDosCard
import com.example.todoapp.presentation.compose.custom.ItemToDosCard
import com.example.todoapp.presentation.compose.custom.ItemUserIdCard
import com.example.todoapp.presentation.compose.custom.UpdateToDosCard
import kotlinx.coroutines.launch


@OptIn(
    ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    navHostController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val pageCount by remember {
        mutableIntStateOf(4)
    }
    val pagerState = rememberPagerState(
        pageCount = { pageCount }
    )
    var completedListSize by remember {
        mutableIntStateOf(0)
    }
    var inCompletedListSize by remember {
        mutableIntStateOf(0)
    }
    val todoListStatusCompleted = remember {
        mutableStateListOf<ToDoData>()
    }
    val todoListStatusInCompleted = remember {
        mutableStateListOf<ToDoData>()
    }

    var todoUpdate by remember {
        mutableStateOf(
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
        mutableIntStateOf(0)
    }

    var userIdList by remember {
        mutableIntStateOf(0)
    }
    val toDoListByUserId = remember {
        mutableStateMapOf<Int, List<ToDoData>>()
    }

    val isInterNetAvailable = CommonUtil.isNetworkAvailable(context =context)


    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }

    val completedToDoResponse by viewModel.loadCompliedToDoResponse.collectAsState()
    val inCompletedToDoResponse by viewModel.loadInCompliedToDoResponse.collectAsState()

    val totalListSize by viewModel.totalListSize.collectAsState()
    val toDosByUserId by viewModel.loadToDosByUserId.collectAsState()

    val allToDoRemote = viewModel.getAllToDoRemote.collectAsLazyPagingItems()
    val allToDoLocal = viewModel.getAllToDoLocal.collectAsLazyPagingItems()

    val tabItems =
        listOf(
            Pair("completed", completedListSize),
            Pair("Pending", inCompletedListSize),
            Pair("Usr Id", userIdList),
            Pair("All", totalListSize)

        )

   // val articles = if(isInterNetAvailable) viewModel.getAllToDoRemote().collectAsLazyPagingItems() else viewModel.getAllToDoLocal().collectAsLazyPagingItems()


    LaunchedEffect(Unit){

        todoListStatusCompleted.clear()
        todoListStatusInCompleted.clear()
        toDoListByUserId.clear()
        viewModel.getCompletedToDos(inNetwork = isInterNetAvailable)

    }

    LaunchedEffect(key1 = completedToDoResponse, block = {
        when (completedToDoResponse) {
            is StateHandler.Error -> {}
            is StateHandler.Loading -> {}
            is StateHandler.Success -> {
                todoListStatusCompleted.clear()

                println("ToDo Test -->  completedToDoResponse  $completedToDoResponse")
                completedListSize = completedToDoResponse.data?.size ?: 0
                todoListStatusCompleted.apply { completedToDoResponse.data?.let { addAll(it) } }
            }
        }
    })
    LaunchedEffect(key1 = inCompletedToDoResponse, block = {
        when (inCompletedToDoResponse) {
            is StateHandler.Error -> {}
            is StateHandler.Loading -> {}
            is StateHandler.Success -> {
                todoListStatusInCompleted.clear()
                println("ToDo Test -->  inCompletedToDoResponse  $inCompletedToDoResponse")
                inCompletedListSize = inCompletedToDoResponse.data?.size ?: 0
                todoListStatusInCompleted.apply { inCompletedToDoResponse.data?.let { addAll(it) } }
            }
        }
    })
    LaunchedEffect(key1 = toDosByUserId, block = {
        println("ToDo Test -->  userIdListResponse  $toDosByUserId")
        if(toDosByUserId.isNotEmpty()){
            toDoListByUserId.clear()
            toDosByUserId.entries.forEach {
                if(it.value.isNotEmpty()){
                    toDoListByUserId[it.key] = it.value
                }
            }
            println("ToDo Test -->  toDoListByUserId  ${toDoListByUserId.entries.toList().last().value.last().todo}")

            userIdList = toDosByUserId.values.size //toDosByUserId.values.sumOf { it.size }
        }
    })

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopBarApp(
                title = stringResource(id = R.string.title_todo)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                scope.launch {
                    bottomSheetState = 0

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
                .background(color = if (isSystemInDarkTheme()) Color.Black else Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .weight(2f)
                .padding(Dimension.textPadding),) {
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
                        text = "Total $totalListSize",
                        style = Typography.bodySmall,
                        color = if (isSystemInDarkTheme()) Color.White else Color.Black
                    )
                    Text(
                        modifier = Modifier
                            .padding(Dimension.textPadding),
                        text = "$completedListSize of $totalListSize Completed",
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
                    mutableMapOf(
                        Pair("completed", completedListSize),
                        Pair("Pending", inCompletedListSize)
                    ).forEach {
                        StatusTypeLegend(
                            status = it.key,
                            value = it.value
                        )

                    }
                }


            }

            Divider()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(8f)
            ) {
                TabRowApp(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    pagerState = pagerState,
                    tabItems = tabItems
                )
                HorizontalPagerApp(pagerState = pagerState) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimension.profileCardPadding)
                    ) {
                        when (pagerState.currentPage) {
                            0 -> {
                                items(todoListStatusCompleted.size) { it->
                                    ItemToDosCard(
                                        data = ToDoData(
                                            id = todoListStatusCompleted[it].id,
                                            todo = todoListStatusCompleted[it].todo,
                                            completed = todoListStatusCompleted[it].completed,
                                            userId = todoListStatusCompleted[it].userId,
                                            serialNumber = todoListStatusCompleted[it].serialNumber,
                                            isDeleted = todoListStatusCompleted[it].isDeleted,
                                            deletedOn = todoListStatusCompleted[it].deletedOn
                                        ),
                                        onClick = {
                                            todoUpdate = it
                                            bottomSheetState = 1

                                            showBottomSheet = true

                                        }
                                    )
                                }

                            }

                            1 -> {
                                items(todoListStatusInCompleted.size) { it ->
                                    ItemToDosCard(
                                        data = ToDoData(
                                            id = todoListStatusInCompleted[it].id,
                                            todo = todoListStatusInCompleted[it].todo,
                                            completed = todoListStatusInCompleted[it].completed,
                                            userId = todoListStatusInCompleted[it].userId,
                                            serialNumber = todoListStatusInCompleted[it].serialNumber,
                                            isDeleted = todoListStatusInCompleted[it].isDeleted,
                                            deletedOn = todoListStatusInCompleted[it].deletedOn
                                        ),
                                        onClick = {
                                            todoUpdate = it
                                            bottomSheetState = 1

                                            showBottomSheet = true
                                        }
                                    )
                                }

                            }
                            2 -> {
                                items(toDoListByUserId.toList().size) { it ->
                                    ItemUserIdCard(
                                        data = ToDoData(
                                            id = toDoListByUserId.entries.toList()[it].value.last().id,
                                            todo = toDoListByUserId.entries.toList()[it].value.last().todo,
                                            completed = toDoListByUserId.entries.toList()[it].value.last().completed,
                                            userId = toDoListByUserId.entries.toList()[it].value.last().userId,
                                            serialNumber = 0,
                                            isDeleted = false,
                                            deletedOn = ""
                                        ),
                                        totalListSize = toDoListByUserId.entries.toList()[it].value.size,
                                        completedListSize = toDoListByUserId.entries.toList()[it].value.filter { it.completed }.size,
                                        inCompletedListSize = toDoListByUserId.entries.toList()[it].value.filter { it.completed.not() }.size,
                                        onClick = {
                                            navHostController.navigate(
                                                Screen.User.passUserId(userId = it)
                                            )
                                        }
                                    )

                                }
                            }
                            else ->{
                                if(isInterNetAvailable){
                                    println("test 15 --->result itemCount  ${allToDoRemote.itemCount}")
                                    items(allToDoRemote.itemCount) { it ->
                                        allToDoRemote[it]?.apply {


                                            Divider()
                                            when (allToDoRemote.loadState.refresh) { //FIRST LOAD
                                                is LoadState.Error -> {}
                                                is LoadState.Loading -> { // Loading UI
                                                    this@LazyColumn.item {
                                                        Column(
                                                            modifier = Modifier
                                                                .fillParentMaxSize(),
                                                            horizontalAlignment = Alignment.CenterHorizontally,
                                                            verticalArrangement = Arrangement.Center,
                                                        ) {
                                                            Text(
                                                                modifier = Modifier
                                                                    .padding(8.dp),
                                                                text = "Refresh Loading"
                                                            )

                                                            CircularProgressIndicator(color = Color.Black)
                                                        }
                                                    }
                                                }
                                                else -> {}
                                            }

                                            when (allToDoRemote.loadState.append) { // Pagination
                                                is LoadState.Error -> {}
                                                is LoadState.Loading -> { // Pagination Loading UI
                                                    this@LazyColumn.item {
                                                        Column(
                                                            modifier = Modifier
                                                                .fillMaxWidth(),
                                                            horizontalAlignment = Alignment.CenterHorizontally,
                                                            verticalArrangement = Arrangement.Center,
                                                        ) {
                                                            Text(text = "Pagination Loading")

                                                            CircularProgressIndicator(color = Color.Black)
                                                        }
                                                    }
                                                }
                                                else -> {
                                                    ItemToDosCard(
                                                        data = ToDoData(
                                                            id = id,
                                                            todo = todo,
                                                            completed = completed,
                                                            userId = userId,
                                                            serialNumber = 0,
                                                            isDeleted = false,
                                                            deletedOn = ""
                                                        ),
                                                        onClick = {
                                                            todoUpdate = it
                                                            bottomSheetState = 1

                                                            showBottomSheet = true

                                                        }
                                                    )
                                                }
                                            }
                                        }

                                    }

                                }else{
                                    println("test 15 --->result itemCount  ${allToDoLocal.itemCount}")
                                    items(allToDoLocal.itemCount) { it ->
                                        allToDoLocal[it]?.apply {

                                            ItemToDosCard(
                                                data = ToDoData(
                                                    id = id,
                                                    todo = todo,
                                                    completed = completed,
                                                    userId = userId,
                                                    serialNumber = 0,
                                                    isDeleted = false,
                                                    deletedOn = ""
                                                ),
                                                onClick = {
                                                    todoUpdate = it
                                                    bottomSheetState = 1

                                                    showBottomSheet = true

                                                }
                                            )
                                            Divider()
                                            when (allToDoLocal.loadState.refresh) { //FIRST LOAD
                                                is LoadState.Error -> {}
                                                is LoadState.Loading -> { // Loading UI
                                                    this@LazyColumn.item {
                                                        Column(
                                                            modifier = Modifier
                                                                .fillParentMaxSize(),
                                                            horizontalAlignment = Alignment.CenterHorizontally,
                                                            verticalArrangement = Arrangement.Center,
                                                        ) {
                                                            Text(
                                                                modifier = Modifier
                                                                    .padding(8.dp),
                                                                text = "Refresh Loading"
                                                            )

                                                            CircularProgressIndicator(color = Color.Black)
                                                        }
                                                    }
                                                }
                                                else -> {}
                                            }

                                            when (allToDoLocal.loadState.append) { // Pagination
                                                is LoadState.Error -> {}
                                                is LoadState.Loading -> { // Pagination Loading UI
                                                    this@LazyColumn.item {
                                                        Column(
                                                            modifier = Modifier
                                                                .fillMaxWidth(),
                                                            horizontalAlignment = Alignment.CenterHorizontally,
                                                            verticalArrangement = Arrangement.Center,
                                                        ) {
                                                            Text(text = "Pagination Loading")

                                                            CircularProgressIndicator(color = Color.Black)
                                                        }
                                                    }
                                                }
                                                else -> {}
                                            }
                                        }

                                    }

                                }

                            }
                        }
                    }
                }

            }


        }

        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier,
                    onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
            ){

                when(bottomSheetState){
                    0->{
                        AddToDosCard(
                            id = totalListSize+1,
                            onClickClose ={showBottomSheet=false},
                            onClickAdd = {
                                showBottomSheet = false
                                viewModel.saveToDo(inNetwork = isInterNetAvailable, todo = it)
                                Toast.makeText(
                                    context,
                                    "ToDo Saved",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

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

@Preview
@Composable
fun MainViewPreview() {
    ToDoAppTheme {
/*
        MainView(
            navHostController = rememberNavController()
        )
*/
    }
}