package com.maku.pombe.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maku.pombe.data.models.popular.Popular
import com.maku.pombe.data.models.recent.Recent
import com.maku.pombe.utils.Constants.Companion.POPULAR_COCKTAIL_TABLE
import com.maku.pombe.utils.Constants.Companion.RECENT_COCKTAIL_TABLE

@Entity(tableName = POPULAR_COCKTAIL_TABLE)
class PopularCocktailsEntity(
    var popular: Popular
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}