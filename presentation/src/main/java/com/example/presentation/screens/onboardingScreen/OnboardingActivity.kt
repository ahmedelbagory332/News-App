package com.example.presentation.screens.onboardingScreen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.example.presentation.screens.onboardingScreen.component.Categories
import com.example.presentation.screens.onboardingScreen.component.Countries
import com.example.presentation.theme.NewsAppTheme
import com.example.presentation.theme.grey
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OnboardingActivity : ComponentActivity() {
    private val viewModel: OnboardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = grey
                ) {
                    OnboardingScreen(viewModel) {
                        if (viewModel.favoriteCategories.size > 3 || viewModel.favoriteCategories.size < 3 || viewModel.selectedCountry.isEmpty()) {
                            Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "navigate", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun OnboardingScreen(viewModel: OnboardingViewModel, onboardingComplete: () -> Unit) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.drawable.onboarding_image),
            contentDescription = "Onboarding Image",
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .size(200.dp)
                .clip(CircleShape)

        )

        Spacer(modifier = Modifier.height(16.dp))
        Countries(viewModel)

        Spacer(modifier = Modifier.height(16.dp))
        Categories(viewModel)


        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onboardingComplete,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Get Started",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

    }
}



