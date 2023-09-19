package com.example.domain.use_cases

import com.example.domain.model.OnBoardingStatus

interface SaveOnBoardingStatusUseCase {

    operator fun invoke(onBoardingStatus: OnBoardingStatus)
}