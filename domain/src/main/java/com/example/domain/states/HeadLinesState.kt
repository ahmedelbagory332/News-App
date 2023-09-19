package com.example.domain.states

import com.example.domain.model.ArticlesModel


data class HeadLinesState (
    val isLoading: Boolean = false,
    val headLines : List<ArticlesModel>  = emptyList(),
    val error: String = ""
)