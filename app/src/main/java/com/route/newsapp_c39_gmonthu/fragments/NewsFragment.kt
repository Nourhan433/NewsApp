package com.route.newsapp_c39_gmonthu.fragments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.route.newsapp_c39_gmonthu.R
import com.route.newsapp_c39_gmonthu.api.ApiManager
import com.route.newsapp_c39_gmonthu.model.ArticlesItem
import com.route.newsapp_c39_gmonthu.model.ArticlesResponse
import com.route.newsapp_c39_gmonthu.model.Constants
import com.route.newsapp_c39_gmonthu.utils.NewsCard
import com.route.newsapp_c39_gmonthu.utils.NewsSourcesTabRow
import com.route.newsapp_c39_gmonthu.utils.NewsTopAppBar
import kotlinx.coroutines.CoroutineScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun NewsFragmentContent(modifier: Modifier = Modifier, categoryId: String, onNewsClick: (String) -> Unit, onSearchClick:()->Unit, scope: CoroutineScope, drawerState: DrawerState) {
    val newsStatesItems = remember {
        mutableStateListOf<ArticlesItem>()

    }
    val shouldDisplaySearchIcon by rememberSaveable {
        mutableStateOf(true)
    }
    val shouldDisplayMenuIcon by rememberSaveable {
        mutableStateOf(true)
    }



    Scaffold(topBar = {
        NewsTopAppBar(
            shouldDisplaySearchIcon = shouldDisplaySearchIcon,
            shouldDisplayMenuIcon = shouldDisplayMenuIcon,
            titleResourceId = R.string.news_app,
            scope = scope,
            drawerState = drawerState
        )
    })
    { paddingValues ->
        paddingValues


        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .paint(
                    painter = painterResource(id = R.drawable.pattern),
                    contentScale = ContentScale.Crop
                )

        )

        {//4-
            NewsSourcesTabRow(categoryId) { sourceId ->

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


            }
            //{ sourceId ->
            // 1- Serialization -> Convert kotlin to json ,,,
            //    Deserialization -> Convert json to kotlin
            // 2-  interface callback ->
            //     A) RecyclerView on Clicks ->
            //     B) 2 Fragments -> interface callbacks -> To Do App (BottomSheet Fragment )
            //                      Activity ->   taskListFragment=             TasksListFragment()
            //                                   taskListFragment.getTodosFromDatabase()
            //
            NewsList(newsStatesItems.toList()) {
                onNewsClick(it)


            }
        }
    }
}

@Composable
fun NewsList(list: List<ArticlesItem>,onNewsClick:(String)->Unit) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        items(list.size) { position ->
            NewsCard(list[position]){
                title->onNewsClick(title)
            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun NewsFragmentContentPreview() {
//   NewsFragmentContent(categoryId = "", onNewsClick = {}) {
//
//   }
//}


