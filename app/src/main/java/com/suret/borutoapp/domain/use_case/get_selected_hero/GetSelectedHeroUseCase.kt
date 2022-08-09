package com.suret.borutoapp.domain.use_case.get_selected_hero

import com.suret.borutoapp.data.repository.Repository
import com.suret.borutoapp.domain.model.Hero

class GetSelectedHeroUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(heroId: Int): Hero {
        return repository.getSelectedHero(heroId = heroId)
    }
}