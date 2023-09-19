package com.example.data.repositories_impl

import android.content.SharedPreferences
import com.example.domain.model.OnBoardingStatus
import com.example.domain.repositories.SaveOnBoardingStatusRepository
import javax.inject.Inject


class SaveOnBoardingStatusRepositoryImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    SaveOnBoardingStatusRepository {


    override fun saveOnBoardingStatus(
        onBoardingStatus: OnBoardingStatus
    ) {

        sharedPreferences.edit()
            .putBoolean("firstTime", onBoardingStatus.firstTime)
            .putString("country", onBoardingStatus.country)
            .putStringSet("categories", onBoardingStatus.categories)
            .apply()
    }


    override fun getOnBoardingStatus(): OnBoardingStatus {
        val firstTime =  sharedPreferences.getBoolean("firstTime", true)
        val country =  sharedPreferences.getString("country", "")
        val categories =  sharedPreferences.getStringSet("categories", setOf())
        return OnBoardingStatus(firstTime,country!!,categories!!)
    }
}