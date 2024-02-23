package com.route.newsapp_c39_gmonthu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.route.newsapp_c39_gmonthu.model.NewsData
import com.route.newsapp_c39_gmonthu.ui.theme.NewsAppC39GMonThuTheme
import com.route.newsapp_c39_gmonthu.ui.theme.black
import com.route.newsapp_c39_gmonthu.ui.theme.gray
import com.route.newsapp_c39_gmonthu.ui.theme.green
import com.route.newsapp_c39_gmonthu.ui.theme.white
import kotlinx.coroutines.launch

class NewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppC39GMonThuTheme {
                // A surface container using the 'background' color from the theme
                NewsScreen()
            }
        }
    }
}

@Composable
fun NewsScreen() {
    // Coordinator Layout
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalNavigationDrawer(drawerContent = {
        NewsDrawerSheet()
    }, drawerState = drawerState) {
        Scaffold(topBar = {
            NewsAppBar {
                scope.launch {
                    drawerState.open()
                }
            }
        }) { paddingValues ->
            NewsFragmentContent(Modifier.padding(top = paddingValues.calculateTopPadding()))
        }
    }

}


@Composable
fun NewsFragmentContent(modifier: Modifier = Modifier) {
    val selectedIndexState = remember {
        mutableIntStateOf(0)
    }
    val newsSources = listOf(
        "ABC News", // 0
        "ABC News", // 1
        "ABC News", // 2
        "ABC News", // 3
        "ABC News", // 4

    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.pattern),
                contentScale = ContentScale.Crop
            )
    ) {
        ScrollableTabRow(
            selectedTabIndex = selectedIndexState.intValue,
            divider = {},
            indicator = {},
            edgePadding = 12.dp
        ) {
            newsSources.forEachIndexed { index, item ->
                Tab(selected = selectedIndexState.intValue == index, onClick = {
                    selectedIndexState.intValue = index
                }, selectedContentColor = white, unselectedContentColor = green) {
                    Text(
                        text = item, modifier = if (selectedIndexState.intValue == index)
                            Modifier
                                .padding(2.dp)
                                .background(green, CircleShape)
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        else
                            Modifier
                                .padding(2.dp)
                                .border(2.dp, green, CircleShape)
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }

        }
        NewsList()
    }
}

@Composable
fun NewsList() {
    val list = mutableListOf(
        NewsData(
            "Why are football's biggest clubs starting a new \n" +
                    "tournament?", "BBC News", "3 Hours Ago",
            R.drawable.ic_sports_news_image
        ),
        NewsData(
            "Why are football's biggest clubs starting a new \n" +
                    "tournament?", "BBC News", "3 Hours Ago",
            R.drawable.ic_sports_news_image
        ),
        NewsData(
            "Why are football's biggest clubs starting a new \n" +
                    "tournament?", "BBC News", "3 Hours Ago",
            R.drawable.ic_sports_news_image
        ),
        NewsData(
            "Why are football's biggest clubs starting a new \n" +
                    "tournament?", "BBC News", "3 Hours Ago",
            R.drawable.ic_sports_news_image
        ),
        NewsData(
            "Why are football's biggest clubs starting a new \n" +
                    "tournament?", "BBC News", "3 Hours Ago",
            R.drawable.ic_sports_news_image
        ),
    )
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        items(list.size) { position ->
            NewsCard(list[position])
        }
    }
}

@Composable
fun NewsCard(newsData: NewsData) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Image(
            painter = painterResource(id = newsData.image ?: R.drawable.ic_sports_news_image),
            contentDescription = stringResource(R.string.news_image),
            contentScale = ContentScale.Crop,

            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2F) // Width = 2*Height
                .clip(RoundedCornerShape(8.dp))
        )
        Text(text = newsData.source ?: "", style = TextStyle(color = gray, fontSize = 10.sp))
        Text(
            text = newsData.title ?: "",
            style = TextStyle(color = gray, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        )
        Text(
            text = newsData.time ?: "",
            style = TextStyle(color = gray, fontSize = 10.sp),
            modifier = Modifier.align(Alignment.End)
        )

    }
}

@Preview
@Composable
fun NewsListPreview() {
    NewsList()
}

@Preview
@Composable
fun NewsCardPreview() {
    NewsCard(
        NewsData(
            "Why are football's biggest clubs starting a new \n" +
                    "tournament?", "BBC News", "3 Hours Ago",
            R.drawable.ic_sports_news_image
        ),
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewsFragmentContentPreview() {
    NewsFragmentContent()
}

@Composable
fun NewsDrawerSheet() {
    ModalDrawerSheet(modifier = Modifier.fillMaxWidth(0.7F)) {
        Column(
            modifier = Modifier
                .background(
                    green
                )
                .fillMaxWidth()
                .fillMaxHeight(0.2F)
                .align(Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Text(
                text = stringResource(id = R.string.news_app),
                style = TextStyle(
                    color = white,
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold

                ),
            )

        }
        DrawerSheetItemTextWithIcon(
            textResId = R.string.categories,
            icon = R.drawable.ic_categories,
            onItemClick = {

            }
        )
        DrawerSheetItemTextWithIcon(
            textResId = R.string.settings,
            icon = R.drawable.ic_settings,
            onItemClick = {

            }
        )
    }
}

@Composable
fun DrawerSheetItemTextWithIcon(textResId: Int, icon: Int, onItemClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 2.dp)
            .clickable { onItemClick() }
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = stringResource(R.string.drawer_sheet_item_icon)
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = stringResource(id = textResId),
            style = TextStyle(color = black, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        )
    }
}

@Preview
@Composable
fun DrawerSheetItemTextWithIconPreview() {
    DrawerSheetItemTextWithIcon(
        textResId = R.string.news_app,
        icon = R.drawable.ic_categories,
        onItemClick = {})
}

@Preview
@Composable
fun NewsDrawerSheetPreview() {
    NewsDrawerSheet()
}

@Preview
@Composable
fun NewsScreenPreview() {
    NewsScreen()
}

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
