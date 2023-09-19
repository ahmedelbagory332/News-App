package com.example.domain.repositories

import com.example.domain.model.OnBoardingStatus

interface SaveOnBoardingStatusRepository {

    fun saveOnBoardingStatus(onBoardingStatus: OnBoardingStatus)

    fun getOnBoardingStatus():OnBoardingStatus

}