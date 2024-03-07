package com.route.newsapp_c39_gmonthu.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.route.newsapp_c39_gmonthu.api.ApiManager
import com.route.newsapp_c39_gmonthu.model.Constants
import com.route.newsapp_c39_gmonthu.model.SourcesItem
import com.route.newsapp_c39_gmonthu.model.SourcesResponse
import com.route.newsapp_c39_gmonthu.ui.theme.green
import com.route.newsapp_c39_gmonthu.ui.theme.white
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 1- create an interface
interface OnNewsSourcesTabSelectedListener {
    fun onNewsSourcesTabSelected(sourceId: String)

}

@Composable
fun NewsSourcesTabRow(
    categoryId: String,
    onTabSelected: (sourceId: String) -> Unit,
//    onNewsSourcesTabSelectedListener: OnNewsSourcesTabSelectedListener
) {
    val selectedIndexState = remember {
        mutableIntStateOf(0)
    }
    val newsSources = remember {
        mutableStateListOf<SourcesItem>()
    }
    LaunchedEffect(Unit) {
        ApiManager.getNewsServices()
            .getNewsSources(Constants.API_KEY, categoryId)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    val sources = response.body()?.sources
                    if (sources?.isNotEmpty() == true) {
                        newsSources.addAll(sources)
                    }
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }


            })  // enqueue -> background Thread
//            .execute()   get Called on Main Thread -> User Clicks and handle user navigation

    }

    ScrollableTabRow(
        selectedTabIndex = selectedIndexState.intValue,
        divider = {},
        indicator = {},
        edgePadding = 12.dp
    ) {
        newsSources.forEachIndexed { index, item ->
            LaunchedEffect(Unit) {
                if (newsSources.isNotEmpty())
                    onTabSelected(newsSources.get(0).id ?: "")
            }
            Tab(selected = selectedIndexState.intValue == index, onClick = {
                onTabSelected(item.id ?: "")
//                onNewsSourcesTabSelectedListener.onNewsSourcesTabSelected(item.id ?: "")
                selectedIndexState.intValue = index
            }, selectedContentColor = white, unselectedContentColor = green) {
                Text(
                    text = item.name ?: "", modifier = if (selectedIndexState.intValue == index)
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
}
