package com.suret.borutoapp.data.repository

import com.suret.borutoapp.data.local.BorutoDatabase
import com.suret.borutoapp.domain.model.Hero
import com.suret.borutoapp.domain.repository.LocalDataSource

class LocalDataSourceImpl(
    borutoDatabase: BorutoDatabase
) : LocalDataSource {

    private val heroDao = borutoDatabase.heroDao()

    override suspend fun getSelectedHero(heroId: Int): Hero {
        return heroDao.getSelectedHero(heroId = heroId)
    }

}