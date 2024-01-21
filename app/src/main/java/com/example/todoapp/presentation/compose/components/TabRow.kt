package zuper.dev.android.dashboard.presentation.compose.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabRowApp(
    modifier: Modifier,
    pagerState: PagerState,
    tabItems: List<Pair<String, Int>>
) {

    val scope = rememberCoroutineScope()

    ScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = pagerState.currentPage,
        edgePadding = 0.dp
    ) {

       tabItems.forEachIndexed { index, tabItem ->

           Tab(
               selected = pagerState.currentPage == index,
               onClick = {
                   scope.launch {
                       pagerState.animateScrollToPage(index)
                   }
               },
               text = {
                   Text(
                       text = "${tabItem.first} (${tabItem.second})",
                      // style = body,
                       color = when(isSystemInDarkTheme()) {
                           true -> {
                               if (pagerState.currentPage == index) Color.White
                               else Color.Gray
                           }
                           false -> {
                               if (pagerState.currentPage == index) Color.Black
                               else Color.Gray
                           }
                       }
                   )
               }
           )

       }

    }

}