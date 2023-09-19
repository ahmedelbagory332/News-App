package com.example.newsapp.di

import android.content.Context
import android.content.SharedPreferences
import com.example.data.remote.NewsApi
import com.example.data.repositories_impl.GetHeadLinesRepositoryImpl
import com.example.data.repositories_impl.SaveOnBoardingStatusRepositoryImpl
import com.example.domain.repositories.GetHeadLinesRepository
import com.example.domain.repositories.SaveOnBoardingStatusRepository
import com.example.domain.use_cases.GetHeadLinesUseCase
import com.example.domain.use_cases.GetOnBoardingStatusUseCase
import com.example.domain.use_cases.SaveOnBoardingStatusUseCase
import com.example.domain.use_cases_impl.GetHeadLinesUseCaseImpl
import com.example.domain.use_cases_impl.GetOnBoardingStatusUseCaseImpl
import com.example.domain.use_cases_impl.SaveOnBoardingStatusUseCaseImpl
import com.example.newsapp.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constant.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi =
        retrofit.create(NewsApi::class.java)


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
    fun getHeadLinesUseCase(getHeadLinesRepository: GetHeadLinesRepository): GetHeadLinesUseCase =
        GetHeadLinesUseCaseImpl(getHeadLinesRepository)


    @Provides
    @Singleton
    fun saveOnBoardingStatusRepository(sharedPreferences:SharedPreferences): SaveOnBoardingStatusRepository =
        SaveOnBoardingStatusRepositoryImpl(sharedPreferences)

    @Provides
    @Singleton
    fun getHeadLinesRepository(api: NewsApi): GetHeadLinesRepository =
        GetHeadLinesRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("onBoardingStatus", Context.MODE_PRIVATE)
    }
}