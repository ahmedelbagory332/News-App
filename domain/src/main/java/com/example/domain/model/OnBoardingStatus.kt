package com.example.domain.model



data class OnBoardingStatus(
    var firstTime: Boolean = true,
    var country: String = "",
    var categories: Set<String> = setOf()
)
