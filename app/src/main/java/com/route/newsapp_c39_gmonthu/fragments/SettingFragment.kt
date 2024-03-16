package com.route.newsapp_c39_gmonthu.fragments

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import com.route.newsapp_c39_gmonthu.NewsActivity
import com.route.newsapp_c39_gmonthu.R
import com.route.newsapp_c39_gmonthu.ui.theme.gray2
import com.route.newsapp_c39_gmonthu.ui.theme.green
import com.route.newsapp_c39_gmonthu.ui.theme.white
import com.route.newsapp_c39_gmonthu.utils.NewsTopAppBar
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsFragment(scope: CoroutineScope, drawerState: DrawerState) {

    val shouldDisplaySearchIcon by rememberSaveable {
        mutableStateOf(false)
    }
    val shouldDisplayMenuIcon by rememberSaveable {
        mutableStateOf(true)
    }


    Scaffold(topBar = {
      NewsTopAppBar(
           shouldDisplaySearchIcon = shouldDisplaySearchIcon,
           shouldDisplayMenuIcon = shouldDisplayMenuIcon,
           titleResourceId = R.string.settings,
           scope = scope,
         drawerState = drawerState
       )
    })
    { paddingValues ->paddingValues

        Column(
            Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .paint(
                    painterResource(id = R.drawable.pattern), contentScale = ContentScale.Crop
                )
                .padding(30.dp)
        ) {

            Text(
                text = stringResource(R.string.language),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,

                color = gray2
            )

            Spacer(Modifier.height(18.dp))
            var isExpanded by rememberSaveable {
                mutableStateOf(false)
            }
            val systemLanguage=when(Locale.current.language){
                "en"->"English"
                "ar"->"العربيه"
                else -> {
                    "English"
                }

            }

            val currentLanguage=AppCompatDelegate.getApplicationLocales()[0]?.displayLanguage?:systemLanguage
            var selectedLanguage by rememberSaveable {
                mutableStateOf(currentLanguage)
            }
            val activty=(LocalContext.current) as NewsActivity
            ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange ={isExpanded=it},
                modifier = Modifier.fillMaxSize()) {
                OutlinedTextField(value = selectedLanguage, onValueChange ={

                }, readOnly = true, trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                }, colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = green, unfocusedBorderColor = Color.Black, focusedTextColor = green, unfocusedTextColor = Color.Black, focusedContainerColor = white, unfocusedContainerColor = white),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth())
                ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded=false }) {
                    DropdownMenuItem(text = {
                                            Text(text = stringResource(id = R.string.English))
                    }, onClick = {
                        selectedLanguage="English"
                        isExpanded=false
                        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
                        activty.finish()
                        activty.startActivity(activty.intent)

                    }, contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding)

                    DropdownMenuItem(text = {
                        Text(text = stringResource(id = R.string.Arabic))
                    }, onClick = {
                        "العربيه"
                        isExpanded=false
                        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ar"))
                        activty.finish()
                        activty.startActivity(activty.intent)

                    }, contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding)
                    
                }
                
            }
        }

    }


}



@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewSettingsFragment() {
    SettingsFragment(
        rememberCoroutineScope(),
        rememberDrawerState(initialValue = DrawerValue.Closed)
    )
}
