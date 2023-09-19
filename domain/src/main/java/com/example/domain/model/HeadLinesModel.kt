package com.example.domain.model


data class HeadLinesModel(

    var status: String? = null,
    var totalResults: Int? = null,
    var articles: List<ArticlesModel> = listOf()

)

data class SourceModel(

    var id: String? = null,
    var name: String? = null

)

data class ArticlesModel(

    var source: SourceModel? = SourceModel(),
    var author: String? = null,
    var title: String? = null,
    var description: String? = null,
    var url: String? = null,
    var urlToImage: String? = null,
    var publishedAt: String? = null,
    var content: String? = null

)