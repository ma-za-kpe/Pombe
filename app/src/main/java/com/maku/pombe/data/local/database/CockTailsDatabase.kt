package com.maku.pombe.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.maku.pombe.data.local.Typeconvertors.RecentRecipeConvertor
import com.maku.pombe.data.local.dao.CocktailsDao
import com.maku.pombe.data.local.entities.LatestCocktailsEntity
import com.maku.pombe.data.local.entities.PopularCocktailsEntity
import com.maku.pombe.data.local.entities.RecentCocktailsEntity

@Database(
    entities = [RecentCocktailsEntity::class, PopularCocktailsEntity::class, LatestCocktailsEntity::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(RecentRecipeConvertor::class)
abstract class CockTailsDatabase: RoomDatabase() {
    abstract fun cocktailsDao(): CocktailsDao
}
