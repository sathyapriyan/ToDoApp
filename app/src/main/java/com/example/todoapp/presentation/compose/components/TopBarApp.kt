package com.example.todoapp.presentation.compose.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.example.todoapp.R
import com.example.todoapp.ui.theme.Dimension
import com.example.todoapp.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarApp(
    title: String = stringResource(id = R.string.title_dashboard),
    elevation: Dp = Dimension.shadowElevation,
    @DrawableRes navigationIcon: Int = R.drawable.ic_nav_back,
    isNavigationVisible: Boolean = false,
    onClickNavigation: () -> Unit = {}
) {

    Surface(
        modifier = Modifier
            .background(Color.Transparent),
        shadowElevation = elevation
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = if (isSystemInDarkTheme()) Color.Black else Color.White
            ),
            title = {
                Text(
                    text = title,
                    style = Typography.titleLarge,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black
                )
            },
            navigationIcon = {
                if (isNavigationVisible) {
                    IconButton(
                        modifier = Modifier
                            .background(color = Color.Transparent, shape = CircleShape),
                        onClick = {
                            onClickNavigation()
                        },
                        interactionSource = remember {
                            MutableInteractionSource()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = navigationIcon),
                            contentDescription = stringResource(id = R.string.desc_nav_icon)
                        )
                    }
                }
            }
        )
    }

}