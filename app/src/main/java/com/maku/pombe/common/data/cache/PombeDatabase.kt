package com.maku.pombe.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.maku.pombe.common.data.cache.daos.LatestDao
import com.maku.pombe.common.data.cache.daos.PopularDao
import com.maku.pombe.common.data.cache.model.cachedLatest.CachedLatest
import com.maku.pombe.common.data.cache.model.cachedPopular.CachedPopular
import com.maku.pombe.common.data.cache.typeconvertor.PombeConvertor

@Database(
    entities = [
        CachedPopular::class,
        CachedLatest::class
    ],
    version = 1
)
@TypeConverters(PombeConvertor::class)
abstract class PombeDatabase : RoomDatabase() {
    abstract fun popularDao(): PopularDao
    abstract fun latestDao(): LatestDao
}

/**
The abstract keyword is a non-access modifier, used for classes and methods: Abstract class: is a
restricted class that cannot be used to create objects (to access it, it must be inherited from
another class). Abstract method: can only be used in an abstract class, and it does not have a body.
 * */