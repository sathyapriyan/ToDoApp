package zuper.dev.android.dashboard.presentation.compose.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerApp(
    pagerState: PagerState,
    content: @Composable () -> Unit
) {

    HorizontalPager(
        state = pagerState
    ) {
        content()
    }

}