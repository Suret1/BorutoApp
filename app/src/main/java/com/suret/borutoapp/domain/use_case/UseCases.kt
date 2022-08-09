package com.suret.borutoapp.domain.use_case

import com.suret.borutoapp.domain.use_case.get_all_heroes.GetAllHeroesUseCase
import com.suret.borutoapp.domain.use_case.get_selected_hero.GetSelectedHeroUseCase
import com.suret.borutoapp.domain.use_case.read_onboarding.ReadOnBoardingUseCase
import com.suret.borutoapp.domain.use_case.save_onboarding.SaveOnBoardingUseCase
import com.suret.borutoapp.domain.use_case.search_heroes.SearchHeroesUseCase

data class UseCases(
    val saveOnBoardingUseCase: SaveOnBoardingUseCase,
    val readOnBoardingUseCase: ReadOnBoardingUseCase,
    val getAllHeroesUseCase: GetAllHeroesUseCase,
    val searchHeroesUseCase: SearchHeroesUseCase,
    val getSelectedHeroUseCase : GetSelectedHeroUseCase
)
