package com.maku.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.maku.core.data.local.Typeconvertors.RecentRecipeConvertor
import com.maku.core.data.local.dao.CocktailsDao
import com.maku.core.data.local.entities.LatestCocktailsEntity
import com.maku.core.data.local.entities.PopularCocktailsEntity
import com.maku.core.data.local.entities.RecentCocktailsEntity

@Database(
    entities = [RecentCocktailsEntity::class, PopularCocktailsEntity::class, LatestCocktailsEntity::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(RecentRecipeConvertor::class)
abstract class CockTailsDatabase: RoomDatabase() {
    abstract fun cocktailsDao(): CocktailsDao
}
