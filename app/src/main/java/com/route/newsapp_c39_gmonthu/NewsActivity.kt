package com.route.newsapp_c39_gmonthu

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.route.newsapp_c39_gmonthu.api.ApiManager
import com.route.newsapp_c39_gmonthu.fragments.CategoriesFragment
import com.route.newsapp_c39_gmonthu.fragments.NewsDetailsScreen
import com.route.newsapp_c39_gmonthu.fragments.NewsFragmentContent
import com.route.newsapp_c39_gmonthu.fragments.SearchScreen
import com.route.newsapp_c39_gmonthu.fragments.SettingsFragment
import com.route.newsapp_c39_gmonthu.model.ArticlesItem
import com.route.newsapp_c39_gmonthu.model.ArticlesResponse
import com.route.newsapp_c39_gmonthu.model.Constants
import com.route.newsapp_c39_gmonthu.model.NewsData
import com.route.newsapp_c39_gmonthu.ui.theme.NewsAppC39GMonThuTheme

import com.route.newsapp_c39_gmonthu.utils.NewsCard
import com.route.newsapp_c39_gmonthu.utils.NewsDrawerSheet
import com.route.newsapp_c39_gmonthu.utils.NewsSourcesTabRow
import com.route.newsapp_c39_gmonthu.utils.NewsTopAppBar
import com.route.newsapp_c39_gmonthu.utils.OnNewsSourcesTabSelectedListener
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// CTRL + ALT + o
class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppC39GMonThuTheme {
                // A surface container using the 'background' color from the theme
                NewsScreen({

                })
            }
        }
    }
    // 30 FPS <->  Slack <-> Discord
}
// 1- Refactoring
// 2- APIs & Networking
// Categories Fragment +Navigation Component +HTTP Logging Interceptor

//Search + Settings + News Details Activity
// Sun & Wed ->Categories Fragment +Navigation Component + Logging Interceptor
@SuppressLint("SuspiciousIndentation")
@Composable
fun NewsScreen(onSearchClick:()->Unit) {
    val shouldDisplaySearchIcon by rememberSaveable {
        mutableStateOf(true)
    }
    val shouldDisplayMenuIcon by rememberSaveable {
        mutableStateOf(true)
    }


    // Coordinator Layout
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()
    ModalNavigationDrawer(drawerContent = {
        NewsDrawerSheet(onSettingsClick = {
       navController.popBackStack()
            if(navController.currentDestination?.route!=SettingsFragmentScreen.ROUTE_NAME)
                navController.navigate((SettingsFragmentScreen.ROUTE_NAME))
            scope.launch {
                drawerState.close()
            }

        }, onCategoriesClick = {
            navController.popBackStack()
            if (navController.currentDestination?.route != CategoriesFragmentScreen.ROUTE_NAME)
                navController.navigate(CategoriesFragmentScreen.ROUTE_NAME)
            scope.launch {
                drawerState.close()
            }
        })
    }, drawerState = drawerState)
    {
        Scaffold(topBar = {
//            NewsTopAppBar(
//                shouldDisplaySearchIcon = shouldDisplaySearchIcon,
//                shouldDisplayMenuIcon =shouldDisplayMenuIcon ,
//                scope = scope,
//
//                drawerState =drawerState,


           // )
            {
                scope.launch {
                    drawerState.open()

                }
            }

        }
        ) { paddingValues ->paddingValues
 //           NewsFragmentContent(Modifier.padding(top = paddingValues.calculateTopPadding()))
            NavHost(
                navController = navController,
                startDestination = CategoriesFragmentScreen.ROUTE_NAME,
                modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
            ) {
                composable(CategoriesFragmentScreen.ROUTE_NAME) {
                    CategoriesFragment(navHostController = navController,scope = scope, drawerState =drawerState )
                }
                composable(
                    "${NewsFragmentScreen.ROUTE_NAME}/{category_id}",
                    arguments = listOf(navArgument("category_id") {
                        type = NavType.StringType
                    })
                ) { navBackStackEntry ->
                    val categoryId = navBackStackEntry.arguments?.getString("category_id")
                    NewsFragmentContent(categoryId = categoryId ?: "",
                        onNewsClick = {title->navController
                            .navigate("newsDetails/$title")},onSearchClick = {
                            navController.navigate("search")
                        },scope = scope, drawerState =drawerState)

                }
                composable(
                    route = "newsDetails/{title}",
                    arguments = listOf(navArgument("title") {
                        type = NavType.StringType
                    })
                ) { navBackStackEntry ->

                    val title = navBackStackEntry.arguments?.getString("title") ?: ""
                    NewsDetailsScreen(
                        title,
                        scope,
                        drawerState
                    )
                }
                composable(route = "search") {
                    SearchScreen { title ->
                        navController.navigate("newsDetails/$title")
                    }
                }

                composable(route="settings"){
                    SettingsFragment(scope = scope, drawerState =drawerState )
                }
            }


                }
            }
        }










@Preview
@Composable
fun NewsScreenPreview() {
    NewsScreen(){

    }
}

