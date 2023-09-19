package com.example.domain.use_cases_impl

import com.example.domain.Resource
import com.example.domain.model.HeadLinesModel
import com.example.domain.repositories.GetHeadLinesRepository
import com.example.domain.use_cases.GetHeadLinesUseCase
 import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetHeadLinesUseCaseImpl @Inject constructor(
    private val getHeadLinesRepository: GetHeadLinesRepository,
) : GetHeadLinesUseCase {


    override fun invoke(country: String, category: String): Flow<Resource<HeadLinesModel>> = flow {
        try {
            emit(Resource.Loading<HeadLinesModel>())
            val genres = getHeadLinesRepository.getRemoteHeadLines(country, category)
            emit(Resource.Success<HeadLinesModel>(genres))
        } catch(e: Exception) {
            emit(Resource.Error<HeadLinesModel>("${e.localizedMessage} : An unexpected error happened"))
        }
    }

}