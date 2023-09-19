package com.example.newsapp.di

import android.content.Context
import android.content.SharedPreferences
import com.example.data.repositories_impl.SaveOnBoardingStatusRepositoryImpl
import com.example.domain.repositories.SaveOnBoardingStatusRepository
import com.example.domain.use_cases.GetOnBoardingStatusUseCase
import com.example.domain.use_cases.SaveOnBoardingStatusUseCase
import com.example.domain.use_cases_impl.GetOnBoardingStatusUseCaseImpl
import com.example.domain.use_cases_impl.SaveOnBoardingStatusUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun saveOnBoardingStatusUseCase(saveOnBoardingStatusRepository: SaveOnBoardingStatusRepository): SaveOnBoardingStatusUseCase =
        SaveOnBoardingStatusUseCaseImpl(saveOnBoardingStatusRepository)

    @Provides
    @Singleton
    fun GetOnBoardingStatusUseCase(saveOnBoardingStatusRepository: SaveOnBoardingStatusRepository): GetOnBoardingStatusUseCase =
        GetOnBoardingStatusUseCaseImpl(saveOnBoardingStatusRepository)


    @Provides
    @Singleton
    fun saveOnBoardingStatusRepository(sharedPreferences:SharedPreferences): SaveOnBoardingStatusRepository =
        SaveOnBoardingStatusRepositoryImpl(sharedPreferences)

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("onBoardingStatus", Context.MODE_PRIVATE)
    }
}