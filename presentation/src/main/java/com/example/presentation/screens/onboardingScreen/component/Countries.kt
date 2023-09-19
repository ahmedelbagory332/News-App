package com.example.presentation.screens.onboardingScreen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.screens.onboardingScreen.OnboardingViewModel

@Composable
 fun Countries(
    viewModel: OnboardingViewModel,
) {
    Text(
        text = "Choose your favorite country",
        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
        modifier = Modifier.padding(10.dp)
    )
    LazyHorizontalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(viewModel.countries) { country ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable(onClick = { viewModel.setSelectedCountry(country) })
            ) {
                RadioButton(
                    selected = viewModel.selectedCountry == country,
                    onClick = { viewModel.setSelectedCountry(country) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = country)
            }

        }
    }
}