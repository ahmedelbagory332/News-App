package com.example.presentation.screens.onboardingScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


data class Category(val id: Int, val name: String)


@HiltViewModel
class OnboardingViewModel  @Inject constructor() : ViewModel(){

      val allCategories = listOf(
        Category(1, "Category 1"),
        Category(2, "Category 2"),
        Category(3, "Category 3"),
        Category(4, "Category 4"),
        Category(5, "Category 5"),

    )

    val countries = listOf(
        "Country 1",
        "Country 2",
        "Country 3",
        "Country 4",
        "Country 5",
        "Country 6",
        "Country 7",
        "Country 8",
        "Country 9"
    )

    private val _favoriteCategories = mutableStateListOf<Category>()
    val favoriteCategories: List<Category> get() = _favoriteCategories

    private val _selectedCountry = mutableStateOf<String>("")
    val selectedCountry: String get() = _selectedCountry.value

    fun setSelectedCountry(selectedCountry: String) {
        _selectedCountry.value = selectedCountry
        _selectedCountry.value = _selectedCountry.value
    }

    fun toggleFavorite(category: Category) {
        if (_favoriteCategories.contains(category)) {
            _favoriteCategories.remove(category)
        } else {
            _favoriteCategories.add(category)
        }
    }
}