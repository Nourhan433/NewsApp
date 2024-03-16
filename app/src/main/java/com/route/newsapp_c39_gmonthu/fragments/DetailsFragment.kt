package com.route.newsapp_c39_gmonthu.fragments

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.route.newsapp_c39_gmonthu.CategoriesFragmentScreen
import com.route.newsapp_c39_gmonthu.NewsActivity
import com.route.newsapp_c39_gmonthu.R
import com.route.newsapp_c39_gmonthu.api.ApiManager
import com.route.newsapp_c39_gmonthu.model.ArticlesItem
import com.route.newsapp_c39_gmonthu.model.ArticlesResponse
import com.route.newsapp_c39_gmonthu.model.Constants
import com.route.newsapp_c39_gmonthu.model.NewsData

import com.route.newsapp_c39_gmonthu.utils.NewsCard
import com.route.newsapp_c39_gmonthu.utils.NewsDrawerSheet
import com.route.newsapp_c39_gmonthu.utils.NewsTopAppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun NewsDetailsScreen(title:String,scope: CoroutineScope, drawerState: DrawerState) {
    val  shouldDisplaySearchIcon by rememberSaveable {
        mutableStateOf(false)
    }
    val  shouldDisplayMenuIcon by rememberSaveable {
        mutableStateOf(false)
    }


    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()





    var newsItem by rememberSaveable {
        mutableStateOf<ArticlesItem?>(null)
    }


    LaunchedEffect(key1 =Unit ) {
        ApiManager.newsService.getNewsItem(title = "title",apiKey= Constants.API_KEY)
            .enqueue(object :
            Callback<ArticlesResponse> {
            override fun onResponse(call: Call<ArticlesResponse>, response: Response<ArticlesResponse>) {
                if (response.isSuccessful){
                    response.body()?.articles?.get(0)?.let{
                        newsItem = it
                    }
                }
            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                Log.e("getArticle : onFailure",t.localizedMessage ?:"")
            }

        })

    }
    Scaffold(
        topBar = {
            NewsTopAppBar(
                shouldDisplaySearchIcon = shouldDisplaySearchIcon,
                shouldDisplayMenuIcon = shouldDisplaySearchIcon,         scope = scope,
                drawerState = drawerState,
               titleResourceId = R.string.Details
  )
        }
    ){ paddingValues ->
        paddingValues


        newsItem?.let { NewDetailsContent(paddingValues, it) }
        }





    }

@Composable
fun NewDetailsContent(paddingValues: PaddingValues,newsItem: ArticlesItem) {
   Column(modifier = Modifier
       .padding(top = paddingValues.calculateTopPadding())
       .paint(painterResource(id = R.drawable.pattern), contentScale = ContentScale.Crop)
       .verticalScroll(rememberScrollState())) {
       NewsCard(newsData =newsItem )
       NewsDetailsCard(newsItem)

   }
}

@Composable
fun NewsDetailsCard(newsItem: ArticlesItem) {
    Column (modifier = Modifier
        .padding(10.dp)
        .background(Color.White, RoundedCornerShape(10.dp))
        .padding(8.dp)){
        Text(text = newsItem.content?:"", modifier = Modifier.padding(10.dp))
        Spacer(modifier = Modifier.fillMaxHeight(0.7F))
        Row (modifier = Modifier.fillMaxWidth()){
            Spacer(modifier = Modifier.fillMaxWidth(0.5F))
            Text(
                text = stringResource(R.string.viewFullArticle),
                modifier = Modifier.padding(8.dp),
                fontFamily = FontFamily.Cursive,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Image(painter = painterResource(id = R.drawable.polygon) , contentDescription ="arrow" )
            
        }
    }

}

@Preview
@Composable
fun NewsDetailsScreenPreview(){
    NewsDetailsScreen(title = "NEWS", scope = rememberCoroutineScope() , drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    ) )
}






















