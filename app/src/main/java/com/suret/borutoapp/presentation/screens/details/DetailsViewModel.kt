package com.suret.borutoapp.presentation.screens.details


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suret.borutoapp.domain.model.Hero
import com.suret.borutoapp.domain.use_case.UseCases
import com.suret.borutoapp.util.Constants.DETAILS_ARGUMENTS_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _selectedHero = MutableStateFlow<Hero?>(null)
    val selectedHero = _selectedHero.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val heroId = savedStateHandle.get<Int>(DETAILS_ARGUMENTS_KEY)
            _selectedHero.value = heroId?.let { useCases.getSelectedHeroUseCase(heroId = it) }
        }
    }

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _colorPalette = MutableStateFlow<Map<String, String>>(mapOf())
    val colorPalette = _colorPalette.asStateFlow()

    fun generatedColorPalette() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.GeneratedColorPalette)
        }
    }

    fun setColorPalette(colors: Map<String, String>) {
        _colorPalette.value = colors
    }

}

sealed class UiEvent {
    object GeneratedColorPalette : UiEvent()
}