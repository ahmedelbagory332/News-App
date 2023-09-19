package com.example.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.presentation.screens.onboardingScreen.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this,OnboardingActivity::class.java))
    }
}
