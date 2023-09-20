package com.example.presentation.screens.favorites

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Resource
import com.example.domain.states.FavArticlesState
import com.example.domain.use_cases.DeleteFavArticlesUseCase
import com.example.domain.use_cases.GetFavArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject




@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavArticlesUseCase: GetFavArticlesUseCase,
    private val deleteFavArticlesUseCase: DeleteFavArticlesUseCase,
) : ViewModel() {


    private val _favState = mutableStateOf(FavArticlesState())
    val favState: State<FavArticlesState>
        get() = _favState

    init {
        getFavArticles()
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


    fun deleteFavArticles(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
             deleteFavArticlesUseCase.invoke(title)
        }
    }

}