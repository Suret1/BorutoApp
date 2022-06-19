package com.suret.borutoapp.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suret.borutoapp.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashVM @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _onBoardingCompleted = MutableStateFlow(false)
    val onBoardingCompleted = _onBoardingCompleted.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _onBoardingCompleted.emit(
                useCases.readOnBoardingUseCase().stateIn(viewModelScope).value
            )
        }
    }

}
