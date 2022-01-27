package com.maku.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maku.core.domain.popular.Popular
import com.maku.core.utils.Constants.Companion.POPULAR_COCKTAIL_TABLE

@Entity(tableName = POPULAR_COCKTAIL_TABLE)
class PopularCocktailsEntity(
    var popular: Popular
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}