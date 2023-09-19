package com.example.domain.use_cases_impl

import com.example.domain.model.OnBoardingStatus
import com.example.domain.repositories.SaveOnBoardingStatusRepository
import com.example.domain.use_cases.SaveOnBoardingStatusUseCase
import javax.inject.Inject

class SaveOnBoardingStatusUseCaseImpl @Inject constructor(
    private val saveOnBoardingStatusRepository: SaveOnBoardingStatusRepository,
) :SaveOnBoardingStatusUseCase {



    override fun invoke(onBoardingStatus: OnBoardingStatus) {
        saveOnBoardingStatusRepository.saveOnBoardingStatus(onBoardingStatus)
    }
}