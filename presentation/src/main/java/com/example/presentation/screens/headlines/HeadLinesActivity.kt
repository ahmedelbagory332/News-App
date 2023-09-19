package com.example.presentation.screens.headlines

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.presentation.theme.NewsAppTheme
import com.example.presentation.theme.grey
import com.example.presentation.utils.CustomToolbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HeadLinesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = grey
                ) {
                    Column {
                        CustomToolbar(
                            label = "head lines",

                            textStyle = TextStyle(color = Color.Black),
                            actions = {
                                Row {
                                    IconButton(
                                        onClick = {

                                        },
                                    ) {
                                        Icon(Icons.Filled.Search, "Search")

                                    }
                                    // icon for localization later
                                }
                            },
                        )
                        Text(text = "welcome to headline")

                    }
                }
            }
        }
    }
}


 
