package com.example.domain.repositories

import com.example.domain.model.HeadLinesModel

interface GetHeadLinesRepository {

    suspend fun getRemoteHeadLines(country: String, category: String, ): HeadLinesModel


}