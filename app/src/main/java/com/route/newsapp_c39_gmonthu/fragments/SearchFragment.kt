package com.route.newsapp_c39_gmonthu.fragments

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.route.newsapp_c39_gmonthu.R
import com.route.newsapp_c39_gmonthu.api.ApiManager
import com.route.newsapp_c39_gmonthu.model.ArticlesItem
import com.route.newsapp_c39_gmonthu.model.ArticlesResponse
import com.route.newsapp_c39_gmonthu.model.Constants
import com.route.newsapp_c39_gmonthu.ui.theme.green
import com.route.newsapp_c39_gmonthu.ui.theme.transparent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SearchScreen(onNewsClick:(String)->Unit) {

    var newsList by rememberSaveable {
        mutableStateOf(listOf<ArticlesItem>())
    }

    var newsFoundState by rememberSaveable {
        mutableStateOf(true)
    }

    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }
    var isFocused by remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }
    var getSearchedArticlesCall: Call<ArticlesResponse>? = null
    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .paint(painterResource(id = R.drawable.pattern), contentScale = ContentScale.Crop)
    ) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.1f)
                .clip(
                    RoundedCornerShape(
                        bottomEnd = 30.dp, bottomStart =
                        30.dp
                    )
                )
                .background(green)
                .padding(horizontal = 20.dp), contentAlignment = Alignment.Center
        )
        {
            TextField(
                value = searchQuery,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = green,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = transparent,
                    unfocusedIndicatorColor = transparent
                ),
                onValueChange = {
                    searchQuery = it
                },
                placeholder = {
                    Text(text = stringResource(R.string.search_article))
                },
                leadingIcon = {
                    Image(painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = stringResource(
                            id = R.string.search_icon
                        ),
                        modifier = Modifier.clickable {


                            getSearchedArticlesCall = ApiManager.newsService.getSearchedArticles(searchQuery=searchQuery, apiKey = Constants.API_KEY)
                            getSearchedArticlesCall?.enqueue(object : Callback<ArticlesResponse> {
                                override fun onResponse(
                                    call: Call<ArticlesResponse>,
                                    response: Response<ArticlesResponse>
                                ) {

                                    if(response.isSuccessful){
                                        val news = response.body()?.articles
                                        if (!news.isNullOrEmpty()){
                                            newsList= news
                                            newsFoundState=true
                                        }else{
                                            newsFoundState=false
                                        }
                                    }

                                }

                                override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {


                                    Log.e("getSearchedArticlesCall onFailure",t.localizedMessage?:"")
                                }

                            })
                        })
                }, maxLines = 1,keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = {


                    getSearchedArticlesCall = ApiManager.newsService.getSearchedArticles(searchQuery=searchQuery, apiKey = Constants.API_KEY)
                    getSearchedArticlesCall?.enqueue(object : Callback<ArticlesResponse> {
                        override fun onResponse(
                            call: Call<ArticlesResponse>,
                            response: Response<ArticlesResponse>
                        ) {

                            if(response.isSuccessful){
                                val news = response.body()?.articles
                                if (!news.isNullOrEmpty()){
                                    newsList= news
                                    newsFoundState=true
                                }else{
                                    newsFoundState=false
                                }
                            }

                            searchQuery=""

                        }

                        override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {

                            searchQuery=""

                            Log.e("getSearchedArticlesCall onFailure",t.localizedMessage?:"")
                        }

                    })
                    focusManager.clearFocus()
                }),
                trailingIcon = {
                    if (isFocused) {
                        Image(painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = stringResource(
                                id = R.string.close
                            ),
                            modifier =

                            Modifier.clickable {
                                if (searchQuery.isNotEmpty()) {
                                    searchQuery = ""
                                }else{
                                    Log.e("%%","${focusRequester.freeFocus()}")
                                    focusManager.clearFocus()
                                    isFocused = false
                                }

                            })
                    }
                },
                modifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(22.dp))
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged { isFocused = true })
        }


      NewsList(list = newsList, onNewsClick = {onNewsClick(it)})


      }



}



@Preview(showSystemUi = true)
@Composable
private fun PreviewSearchScreen() {
    SearchScreen(){

    }
}