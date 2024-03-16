package com.route.newsapp_c39_gmonthu.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.route.newsapp_c39_gmonthu.R
import com.route.newsapp_c39_gmonthu.fragments.SearchScreen
import com.route.newsapp_c39_gmonthu.ui.theme.green
import com.route.newsapp_c39_gmonthu.ui.theme.white
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopAppBar(titleResourceId:Int?=null, titleString:String?=null, shouldDisplaySearchIcon:Boolean, shouldDisplayMenuIcon:Boolean, scope: CoroutineScope, drawerState: DrawerState,
                  onSearchClick:(()->Unit)?=null) {

    TopAppBar(title = {
        Text(
            text = if (titleResourceId!=null)stringResource(titleResourceId) else titleString ?:"",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }, actions = {
        if (shouldDisplaySearchIcon)
            AppBarIconButton(R.drawable.ic_search){
                if (onSearchClick != null) {
                    onSearchClick()
                }
            }
        else
            Spacer(modifier = Modifier
                .padding(10.dp)
                .size(25.dp) )
    }, colors = TopAppBarDefaults.topAppBarColors(containerColor = green),
        navigationIcon = {
            if (shouldDisplayMenuIcon)
                AppBarIconButton(R.drawable.ic_menu, onClick = {
                    scope.launch {
                        drawerState.open()
                    } })
            else
                Spacer(modifier = Modifier
                    .padding(10.dp)
                    .size(25.dp) )
        },
        modifier = Modifier.clip(
            RoundedCornerShape(
                bottomEnd = 30.dp, bottomStart =
                30.dp
            )
        )
    )
}

@Composable
private fun AppBarIconButton(icon: Int, onClick: () -> Unit = {}) {
    Image(
        painter = painterResource(icon),
        contentDescription = stringResource(R.string.search_icon),
        modifier = Modifier
            .padding(10.dp)
            .size(25.dp)
            .clickable(onClick = onClick)
    )
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewNewsTopAppBar() {
    NewsTopAppBar(titleResourceId = R.string.app_name, shouldDisplaySearchIcon = true, shouldDisplayMenuIcon = true, scope = rememberCoroutineScope(), drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    ))

}
