package com.example.presentation.screens.search

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
import com.example.domain.use_cases.GetFavArticlesUseCase
import com.example.domain.use_cases.GetHeadLinesUseCase
import com.example.domain.use_cases.GetOnBoardingStatusUseCase
import com.example.domain.use_cases.SaveFavArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getOnBoardingStatusUseCase: GetOnBoardingStatusUseCase,
    private val getHeadLinesUseCase: GetHeadLinesUseCase,
    private val getFavArticlesUseCase: GetFavArticlesUseCase,
    private val deleteFavArticlesUseCase: DeleteFavArticlesUseCase,
    private val saveFavArticlesUseCase: SaveFavArticlesUseCase,
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

    val showPlaceHolder = mutableStateOf(true)
    val query = mutableStateOf("")

    fun setSelectedCategory(selectedCategory: String) {
        _selectedCategory.value = selectedCategory
        _selectedCategory.value = _selectedCategory.value
    }


    init {
        getOnBoardingStatus()
        getFavArticles()
    }

    private fun getOnBoardingStatus() {
        viewModelScope.launch {
            val result = getOnBoardingStatusUseCase.invoke()
            Log.d("TAG", " bego getOnBoardingStatusUseCase: ${result.firstTime}")
            Log.d("TAG", " bego getOnBoardingStatusUseCase: ${result.country}")
            _onBoardingStatus.value = result

        }
    }

    fun getSearchedArticles(country: String, category: String, search: String) {
        getHeadLinesUseCase(country, category, search).onEach { result ->
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

}