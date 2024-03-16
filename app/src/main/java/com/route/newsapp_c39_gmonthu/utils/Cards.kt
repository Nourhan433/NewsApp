package com.route.newsapp_c39_gmonthu.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.route.newsapp_c39_gmonthu.R
import com.route.newsapp_c39_gmonthu.model.ArticlesItem
import com.route.newsapp_c39_gmonthu.ui.theme.gray

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsCard(newsData: ArticlesItem,onNewsClick:((String)->Unit)?=null) {
    Card(
        modifier = Modifier.clickable {
            if (onNewsClick != null) {
                onNewsClick(newsData.title?:"")
            }
        }.padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ){
        GlideImage(                          //         URL = "https://www.google.com/image.png"
            model = newsData.urlToImage,
            contentDescription = stringResource(R.string.news_image),
            contentScale = ContentScale.Crop,

            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2F) // Width = 2*Height
                .clip(RoundedCornerShape(8.dp))
        )
        Text(text = newsData.source?.name ?: "", style = TextStyle(color = gray, fontSize = 10.sp))
        Text(
            text = newsData.title ?: "",
            style = TextStyle(color = gray, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        )
        Text(
            text = newsData.publishedAt ?: "",
            style = TextStyle(color = gray, fontSize = 10.sp),
            modifier = Modifier.align(Alignment.End)
        )

    }
}


//@Preview
//@Composable
//fun NewsCardPreview() {
//    NewsCard(
//        NewsData(
//            "Why are football's biggest clubs starting a new \n" +
//                    "tournament?", "BBC News", "3 Hours Ago",
//            R.drawable.ic_sports_news_image
//        ),
//    )
//}
