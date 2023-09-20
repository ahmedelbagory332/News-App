package com.example.presentation.screens.headlines

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Resource
import com.example.domain.model.ArticlesModel
import com.example.domain.model.OnBoardingModel
import com.example.domain.states.FavArticlesState
import com.example.domain.states.HeadLinesState
import com.example.domain.use_cases.DeleteFavArticlesUseCase
import com.example.domain.use_cases.GetCurrentLangUseCase
import com.example.domain.use_cases.GetFavArticlesUseCase
import com.example.domain.use_cases.GetHeadLinesUseCase
import com.example.domain.use_cases.GetOnBoardingStatusUseCase
import com.example.domain.use_cases.SaveCurrentLangUseCase
import com.example.domain.use_cases.SaveFavArticlesUseCase
import com.example.presentation.screens.onboardingScreen.countries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject




@HiltViewModel
class HeadLinesViewModel @Inject constructor(
    private val getOnBoardingStatusUseCase: GetOnBoardingStatusUseCase,
    private val getHeadLinesUseCase: GetHeadLinesUseCase,
    private val getFavArticlesUseCase: GetFavArticlesUseCase,
    private val deleteFavArticlesUseCase: DeleteFavArticlesUseCase,
    private val saveFavArticlesUseCase: SaveFavArticlesUseCase,
    private val saveCurrentLangUseCase: SaveCurrentLangUseCase,
    private val getCurrentLangUseCase: GetCurrentLangUseCase,
) : ViewModel() {

    private val _onBoardingStatus = mutableStateOf(OnBoardingModel())
    val onBoardingStatus = _onBoardingStatus


    private var _selectedCategory = mutableStateOf("")
    val selectedCategory: State<String>
        get() = _selectedCategory


    private val _state = mutableStateOf(HeadLinesState())
    val headLines: State<HeadLinesState>
        get() = _state

    private val _favState = mutableStateOf(FavArticlesState())
    val favState: State<FavArticlesState>
        get() = _favState

    fun setSelectedCategory(selectedCategory: String){
        _selectedCategory.value = selectedCategory
        _selectedCategory.value = _selectedCategory.value
    }



    init {

        getFavArticles()
    }

      fun getOnBoardingStatus() {
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
                        headLines = result.data!!.sortedByDescending { it.publishedAt }
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
   private fun getFavArticles() {
        getFavArticlesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {

                    _favState.value = FavArticlesState(
                        articles = result.data!!.sortedByDescending { it.publishedAt }
                    )

                }
                is Resource.Error -> {
                    _favState.value = FavArticlesState(
                        error = result.message ?: "An unexpected error happened"
                    )
                }
                is Resource.Loading -> {
                    _favState.value = FavArticlesState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

    }

      fun saveFavArticles(fav: ArticlesModel) {
        viewModelScope.launch {
             saveFavArticlesUseCase.invoke(fav)
        }
    }

    fun deleteFavArticles(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
             deleteFavArticlesUseCase.invoke(title)
        }
    }

    fun setLanguagePref(lang: String) {
        viewModelScope.launch {
            saveCurrentLangUseCase(lang)
        }
    }

    fun getLanguagePref():String = getCurrentLangUseCase()

}