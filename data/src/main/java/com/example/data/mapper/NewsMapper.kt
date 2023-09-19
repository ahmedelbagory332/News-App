package com.example.data.mapper

import com.example.data.remote.responses.ArticlesJson
import com.example.data.remote.responses.HeadLinesJson
import com.example.data.remote.responses.SourceJson
import com.example.domain.model.ArticlesModel
import com.example.domain.model.HeadLinesModel
import com.example.domain.model.SourceModel

fun HeadLinesJson.toDomain(): HeadLinesModel =
    HeadLinesModel(
        status = status,
        totalResults = totalResults,
        articles = articles.map { it.toDomain() }
    )

fun SourceJson.toDomain(): SourceModel =
    SourceModel(
        id = id,
        name = name
    )

fun ArticlesJson.toDomain(): ArticlesModel =
    ArticlesModel(
        source = source.toDomain(),
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content

    )