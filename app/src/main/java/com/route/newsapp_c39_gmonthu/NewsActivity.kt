package com.route.newsapp_c39_gmonthu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.route.newsapp_c39_gmonthu.api.ApiManager
import com.route.newsapp_c39_gmonthu.model.ArticlesItem
import com.route.newsapp_c39_gmonthu.model.ArticlesResponse
import com.route.newsapp_c39_gmonthu.model.Constants
import com.route.newsapp_c39_gmonthu.model.NewsData
import com.route.newsapp_c39_gmonthu.ui.theme.NewsAppC39GMonThuTheme
import com.route.newsapp_c39_gmonthu.utils.NewsAppBar
import com.route.newsapp_c39_gmonthu.utils.NewsCard
import com.route.newsapp_c39_gmonthu.utils.NewsDrawerSheet
import com.route.newsapp_c39_gmonthu.utils.NewsSourcesTabRow
import com.route.newsapp_c39_gmonthu.utils.OnNewsSourcesTabSelectedListener
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// CTRL + ALT + o
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
    // 30 FPS <->  Slack <-> Discord
}
// 1- Refactoring
// 2- APIs & Networking

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
    val newsStatesItems = remember {
        mutableStateListOf<ArticlesItem>()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.pattern),
                contentScale = ContentScale.Crop
            )
    ) {//4-
        NewsSourcesTabRow { sourceId ->

            ApiManager.getNewsServices()
                .getNewsBySource(Constants.API_KEY, sourceId)
//                    .execute() // main Thread
                .enqueue(object : Callback<ArticlesResponse> {
                    override fun onResponse(
                        call: Call<ArticlesResponse>,
                        response: Response<ArticlesResponse>
                    ) {
                        newsStatesItems.clear()
                        val newsList = response.body()?.articles
                        if (newsList?.isNotEmpty() == true) {
                            newsStatesItems.addAll(newsList)
                        }
                        // Documentation -> Thread -> Room
                    }
                    // Main Thread ->  handle button clicks => UserNavigation
                    // Background Thread -> Networking -> Local Database  <-> Heavy loading task
                    // Instagram -> APIs Sign up


                    override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {

                    }

                })

        } //{ sourceId ->
        // 1- Serialization -> Convert kotlin to json ,,,
        //    Deserialization -> Convert json to kotlin
        // 2-  interface callback ->
        //     A) RecyclerView on Clicks ->
        //     B) 2 Fragments -> interface callbacks -> To Do App (BottomSheet Fragment )
        //                      Activity ->   taskListFragment=             TasksListFragment()
        //                                   taskListFragment.getTodosFromDatabase()
        //
        NewsList(newsStatesItems.toList())
    }
}

@Composable
fun NewsList(list: List<ArticlesItem>) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        items(list.size) { position ->
            NewsCard(list[position])
        }
    }
}

//@Preview
//@Composable
//fun NewsListPreview() {
//    NewsList()
//}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewsFragmentContentPreview() {
    NewsFragmentContent()
}

@Preview
@Composable
fun NewsScreenPreview() {
    NewsScreen()
}
