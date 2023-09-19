package com.example.presentation.screens.headlines

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Resource
import com.example.domain.model.OnBoardingModel
import com.example.domain.states.HeadLinesState
import com.example.domain.use_cases.GetHeadLinesUseCase
import com.example.domain.use_cases.GetOnBoardingStatusUseCase
import com.example.presentation.screens.onboardingScreen.countries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject




@HiltViewModel
class HeadLinesViewModel @Inject constructor(
    private val getOnBoardingStatusUseCase: GetOnBoardingStatusUseCase,
    private val getHeadLinesUseCase: GetHeadLinesUseCase
) : ViewModel() {

    private val _onBoardingStatus = mutableStateOf(OnBoardingModel())
    val onBoardingStatus = _onBoardingStatus


    private var _selectedCategory = mutableStateOf("")
    val selectedCategory: State<String>
        get() = _selectedCategory

    private val _state = mutableStateOf(HeadLinesState())
    val headLines: State<HeadLinesState>
        get() = _state

    fun setSelectedCategory(selectedGenre: String){
        _selectedCategory.value = selectedGenre
        _selectedCategory.value = _selectedCategory.value
    }



    init {
        getOnBoardingStatus()
    }

    private fun getOnBoardingStatus() {
        viewModelScope.launch {
            val result = getOnBoardingStatusUseCase.invoke()
            Log.d("TAG", " bego getOnBoardingStatusUseCase: ${result.firstTime}")
            Log.d("TAG", " bego getOnBoardingStatusUseCase: ${result.country}")
            _onBoardingStatus.value = result
            _selectedCategory.value = onBoardingStatus.value.categories.first()


            countries[result.country]?.let { getRemoteHeadLines(it,onBoardingStatus.value.categories.first()) }
        }
    }

    fun getRemoteHeadLines(country: String, category: String) {
        getHeadLinesUseCase(country, category).onEach { result ->
            when (result) {
                is Resource.Success -> {

                    _state.value = HeadLinesState(
                        headLines = result.data!!.articles.sortedByDescending { it.publishedAt }
                    )

                }
                is Resource.Error -> {
                    _state.value = HeadLinesState(
                        error = result.message ?: "An unexpected error happened"
                    )
                }
                is Resource.Loading -> {
                    _state.value = HeadLinesState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

    }

}