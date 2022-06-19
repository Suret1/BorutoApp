package com.suret.borutoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.suret.borutoapp.data.local.dao.HeroDao
import com.suret.borutoapp.data.local.dao.HeroRemoteKeysDao
import com.suret.borutoapp.domain.model.Hero
import com.suret.borutoapp.domain.model.HeroRemoteKeys

@Database(
    entities = [Hero::class, HeroRemoteKeys::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DatabaseConverter::class)
abstract class BorutoDatabase : RoomDatabase() {

    abstract fun heroDao(): HeroDao
    abstract fun heroRemoteKeysDao(): HeroRemoteKeysDao
}