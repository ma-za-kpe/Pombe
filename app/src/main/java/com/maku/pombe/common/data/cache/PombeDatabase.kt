package com.maku.pombe.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.maku.pombe.common.data.cache.daos.PopularDao
import com.maku.pombe.common.data.cache.model.cachedPopular.CachedPopular
import com.maku.pombe.common.data.cache.typeconvertor.PombeConvertor

@Database(
    entities = [
        CachedPopular::class
    ],
    version = 1
)
@TypeConverters(PombeConvertor::class)
abstract class PombeDatabase : RoomDatabase() {
    abstract fun popularDao(): PopularDao
}