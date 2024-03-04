package com.route.newsapp_c39_gmonthu.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.route.newsapp_c39_gmonthu.R
import com.route.newsapp_c39_gmonthu.ui.theme.green
import com.route.newsapp_c39_gmonthu.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsAppBar(onNavigationIconClick: () -> Unit) {
    TopAppBar(title = {
        Text(
            text = stringResource(R.string.news_app),
            style = TextStyle(color = white, fontSize = 22.sp, textAlign = TextAlign.Center),
            modifier = Modifier.fillMaxWidth(),
        )
    }, actions = {
        Image(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = stringResource(
                R.string.search_icon
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = green), navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = stringResource(R.string.icon_side_menu),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        onNavigationIconClick()
                    }
            )
        }, modifier = Modifier.clip(
            RoundedCornerShape(
                topEnd = 0.dp,
                topStart = 0.dp,
                bottomEnd = 30.dp,
                bottomStart = 30.dp
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
fun NewsAppBarPreview() {
    NewsAppBar {}
}