package com.route.newsapp_c39_gmonthu.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.route.newsapp_c39_gmonthu.R
import com.route.newsapp_c39_gmonthu.ui.theme.black
import com.route.newsapp_c39_gmonthu.ui.theme.green
import com.route.newsapp_c39_gmonthu.ui.theme.white

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

