package com.example.presentation.screens.headlines.component



import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.presentation.R
import com.example.presentation.screens.headlines.HeadLinesViewModel
import com.example.presentation.utils.Chip
import java.util.*


@SuppressLint("UnrememberedMutableState")
@Composable
fun UserFavCategories(
    headLinesViewModel: HeadLinesViewModel,
      selectedCategory:(category:String)-> Unit
    ) {

        Text(
            modifier = Modifier.padding(PaddingValues(8.dp)),
            text = stringResource(R.string.your_category),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        LazyRow {
            items(headLinesViewModel.onBoardingStatus.value.categories.toList()) { categoryItem ->
                Chip(
                    category = categoryItem,
                    selected = headLinesViewModel.selectedCategory.value == categoryItem,
                    onSelected = {
                        headLinesViewModel.setSelectedCategory(categoryItem)
                        selectedCategory(categoryItem)
                    },
                    modifier = Modifier
                )
            }
        }
}