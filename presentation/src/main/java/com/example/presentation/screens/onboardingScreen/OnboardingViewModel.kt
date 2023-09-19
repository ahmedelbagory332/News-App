package com.example.presentation.screens.onboardingScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.OnBoardingStatus
import com.example.domain.use_cases.SaveOnBoardingStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class Category(val id: Int, val name: String)


@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val saveOnBoardingStatusUseCase: SaveOnBoardingStatusUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(OnBoardingViewState())
    val viewState = _viewState.asStateFlow()




    fun setSelectedCountry(selectedCountry: String) {
        _viewState.update { it.copy(selectedCountry = selectedCountry) }
    }


    fun toggleFavorite(category: Category) {
        if (_viewState.value.favoriteCategories.contains(category)) {
            _viewState.update { it.copy(favoriteCategories = _viewState.value.favoriteCategories - category) }
        } else {
            _viewState.update { it.copy(favoriteCategories = _viewState.value.favoriteCategories + category) }
        }
    }



    fun saveOnBoardingStatus() {
        viewModelScope.launch {
            saveOnBoardingStatusUseCase(
                OnBoardingStatus(
                    firstTime = false,
                    country = _viewState.value.selectedCountry,
                    categories = _viewState.value.favoriteCategories.map { it.name }.toSet()
                )
            )
        }
    }
}