package com.example.domain.states

import com.example.domain.model.FavArticleModel


data class FavArticlesState (
    val isLoading: Boolean = false,
    val articles : List<FavArticleModel>  = emptyList(),
    val error: String = ""
)