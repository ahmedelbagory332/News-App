package com.example.presentation.screens.onboardingScreen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.screens.onboardingScreen.Category
import com.example.presentation.screens.onboardingScreen.OnboardingViewModel
import com.example.presentation.theme.White

@Composable
 fun Categories(viewModel: OnboardingViewModel) {
    Text(
        "Choose your favorite categories:",
        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
        modifier = Modifier.padding(10.dp)
    )
    viewModel.allCategories.forEach { category ->
        Card(
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp),
            colors = CardDefaults.cardColors(
                containerColor = White,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            ),

            ) {
            CategoryItem(
                category = category,
                isFavorite = viewModel.favoriteCategories.contains(category),
                onToggleFavorite = { viewModel.toggleFavorite(category) }
            )
        }
    }
}


@Composable
fun CategoryItem(category: Category, isFavorite: Boolean, onToggleFavorite: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggleFavorite)
            .padding(16.dp)
    ) {
        Text(category.name)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favorite",
            tint = if (isFavorite) Color.Red else Color.Gray
        )
    }
}