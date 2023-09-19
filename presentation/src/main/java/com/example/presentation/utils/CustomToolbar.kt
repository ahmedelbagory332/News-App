package com.example.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun  CustomToolbar(
    modifier: Modifier = Modifier,
    label: String = "",
    toolbarColor: Color = Color.Transparent,
    textStyle: TextStyle = TextStyle(),
    actions: @Composable () -> Unit = {},
) {
    Box(
        modifier
            .background(toolbarColor)
            .padding(vertical = 20.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {


            Text(
                modifier = Modifier.align(Alignment.Center),
                text = label,
                style = textStyle,
            )

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 20.dp)
        ) {
            actions.invoke()
        }
    }
}