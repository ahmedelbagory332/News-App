package com.example.data.repositories_impl

import android.content.SharedPreferences
import com.example.domain.model.OnBoardingModel
import com.example.domain.repositories.SaveOnBoardingStatusRepository
import javax.inject.Inject


class SaveOnBoardingStatusRepositoryImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    SaveOnBoardingStatusRepository {


    override fun saveOnBoardingStatus(
        onBoardingModel: OnBoardingModel
    ) {

        sharedPreferences.edit()
            .putBoolean("firstTime", onBoardingModel.firstTime)
            .putString("country", onBoardingModel.country)
            .putStringSet("categories", onBoardingModel.categories)
            .apply()
    }


    override fun getOnBoardingStatus(): OnBoardingModel {
        val firstTime =  sharedPreferences.getBoolean("firstTime", true)
        val country =  sharedPreferences.getString("country", "")
        val categories =  sharedPreferences.getStringSet("categories", setOf())
        return OnBoardingModel(firstTime,country!!,categories!!)
    }
}