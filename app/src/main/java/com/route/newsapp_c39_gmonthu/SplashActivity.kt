package com.route.newsapp_c39_gmonthu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.route.newsapp_c39_gmonthu.ui.theme.NewsAppC39GMonThuTheme

// To do app Assignment -> Swipe to delete - Edit Task(Todo) - Settings Fragment
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppC39GMonThuTheme {
                // A surface container using the 'background' color from the theme
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this@SplashActivity, NewsActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 2500)
                SplashScreen()
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.pattern),
                contentScale = ContentScale.Crop,
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1F))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.app_logo_image),
            modifier = Modifier.fillMaxHeight(0.35F),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.weight(1F))
        Image(
            painter = painterResource(id = R.drawable.signature),
            contentDescription = stringResource(R.string.app_development_signature),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth(0.4F)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}

data class SettingsItem(
    val id: String? = null
)

// State -> Data ->
@Composable
fun CounterScreen() {
    // 1
    var counter = remember {
        mutableIntStateOf(0)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Counter              // 0
        Text(text = "you pressed ${counter.intValue} times")
        Button(onClick = { counter.intValue += 1 }) {
            Text(text = "Click Me !")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CounterScreenPreview() {
    CounterScreen()
}