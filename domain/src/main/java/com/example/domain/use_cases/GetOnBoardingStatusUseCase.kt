package com.example.domain.use_cases

import com.example.domain.model.OnBoardingStatus

interface GetOnBoardingStatusUseCase {

    operator fun invoke(): OnBoardingStatus
}