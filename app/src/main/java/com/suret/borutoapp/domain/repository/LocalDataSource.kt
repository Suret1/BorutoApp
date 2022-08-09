package com.suret.borutoapp.domain.repository

import com.suret.borutoapp.domain.model.Hero

interface LocalDataSource {
    suspend fun getSelectedHero(heroId: Int): Hero
}