package zuper.dev.android.dashboard.presentation.screens.homePage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.presentation.compose.custom.ProgressBar
import com.example.todoapp.presentation.compose.custom.StatusTypeLegend
import com.example.todoapp.ui.theme.Dimension
import com.example.todoapp.ui.theme.GreenApp
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.ui.theme.VioletApp
import zuper.dev.android.dashboard.presentation.compose.components.TabRowApp
import zuper.dev.android.dashboard.presentation.compose.components.TopBarApp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePageView(){

    val context = LocalContext.current
    var pageCount by remember {
        mutableIntStateOf(4)
    }
    val pagerState = rememberPagerState(
        pageCount = { pageCount }
    )
    var totalValue by remember {
        mutableIntStateOf(0)
    }

   // val newStoriesResponse = viewModel.loadNewStoriesResponse.observeAsState().value


    val statusItems  = mapOf(
    "completed" to 50,
    "Pending" to 100,
    )

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopBarApp(
                title = stringResource(id = R.string.title_dashboard)
            )
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(Dimension.containerPadding)
                .background(color = if (isSystemInDarkTheme()) Color.Black else Color.White)
                .verticalScroll(rememberScrollState())
        ){
            Card(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(Dimension.textPadding),
                shape = RoundedCornerShape(Dimension.cardCornerRadius),
                ){
                Column() {
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
                            text = "Total $totalValue",
                          //  style = Typography.bodySmall,
                            color = if (isSystemInDarkTheme()) Color.White else Color.Black
                        )
                        Text(
                            modifier = Modifier
                                .padding(Dimension.textPadding),
                            text = "50 of 150 Complited",
                            //style = Typography.bodySmall,
                            color = if (isSystemInDarkTheme()) Color.White else Color.Black
                        )
                    }


                    ProgressBar(
                        modifier = Modifier
                            .padding(0.dp)
                            .padding(Dimension.progressBarPadding),
                        values = listOf(
                            Pair(2, GreenApp),
                            Pair(2, VioletApp)
                        )
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
            Column(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()) {
/*
                TabRowApp(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    pagerState = pagerState,
                    tabItems = tabItems
                )
*/

            }


        }
    }
}



@Preview
@Composable
fun HomePagePreview() {
    ToDoAppTheme {
        HomePageView(
            /*modifier = Modifier,
            values = listOf(
                Pair(2, VioletApp),
                Pair(2, LightBlueApp),
                Pair(2, YellowApp),
                Pair(2, GreenApp),
                Pair(2, RedApp)
            )*/
        )
    }
}