package com.route.newsapp_c39_gmonthu.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.route.newsapp_c39_gmonthu.NewsFragmentScreen
import com.route.newsapp_c39_gmonthu.R
import com.route.newsapp_c39_gmonthu.model.Category
import com.route.newsapp_c39_gmonthu.model.Constants
import com.route.newsapp_c39_gmonthu.ui.theme.gray2

@Composable
fun CategoriesFragment(modifier: Modifier = Modifier, navHostController: NavHostController) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "${stringResource(id = R.string.pick_your_category)}${stringResource(id = R.string.of_interest)} ",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = gray2,
        )
        CategoriesGrid(navHostController = navHostController)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CategoriesFragmentPreview() {
    CategoriesFragment(navHostController = rememberNavController())
}

@Composable
fun CategoriesGrid(navHostController: NavHostController) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(Constants.categories.size) { position ->
            CategoryCard(
                Constants.categories.get(position),
                position,
                navHostController = navHostController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(category: Category, position: Int, navHostController: NavHostController) {

// Handle it by ourselves
    // Navigation Component

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = category.backgroundColor)),
        shape = RoundedCornerShape(
            topEnd = 24.dp,
            topStart = 24.dp,
            bottomEnd = if (position % 2 == 0) 24.dp else 0.dp,
            bottomStart = if (position % 2 == 1) 24.dp else 0.dp
        ),
        onClick = {
            navHostController.navigate("${NewsFragmentScreen.ROUTE_NAME}/${category.apiID}")
        }
    ) {
        Image(
            painter = painterResource(id = category.drawableResId),
            contentDescription = stringResource(R.string.category_image),
            modifier = Modifier
                .height(100.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop

        )
        Text(
            text = stringResource(id = category.titleResID),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 22.sp,
            color = Color.White,
            fontWeight = FontWeight.Normal,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CategoriesGridPreview() {
    CategoriesGrid(rememberNavController())
}

