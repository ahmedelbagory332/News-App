package com.example.presentation.screens.main

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.OnBoardingModel
import com.example.domain.use_cases.GetOnBoardingStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject




@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getOnBoardingStatusUseCase: GetOnBoardingStatusUseCase
) : ViewModel() {

    private val _onBoardingModel = mutableStateOf(OnBoardingModel())
    val onBoardingStatus = _onBoardingModel

    init {
        getOnBoardingStatusUseCase()
    }

    private fun getOnBoardingStatusUseCase() {
        viewModelScope.launch {
            val result = getOnBoardingStatusUseCase.invoke()
            Log.d("TAG", " bego getOnBoardingStatusUseCase: ${result.firstTime}")
            Log.d("TAG", " bego getOnBoardingStatusUseCase: ${result.country}")
            _onBoardingModel.value = result
        }
    }

}