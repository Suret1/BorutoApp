package com.suret.borutoapp.domain.use_case.search_heroes

import androidx.paging.PagingData
import com.suret.borutoapp.data.repository.Repository
import com.suret.borutoapp.domain.model.Hero
import kotlinx.coroutines.flow.Flow

class SearchHeroesUseCase(
    private val repository: Repository
) {

    operator fun invoke(query: String): Flow<PagingData<Hero>> {
        return repository.searchHeroes(query)
    }
}