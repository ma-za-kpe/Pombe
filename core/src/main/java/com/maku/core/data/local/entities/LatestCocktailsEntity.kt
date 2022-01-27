package com.maku.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maku.core.domain.latest.Latest
import com.maku.core.utils.Constants.Companion.LATEST_COCKTAIL_TABLE

@Entity(tableName = LATEST_COCKTAIL_TABLE)
class LatestCocktailsEntity(
    var latest: Latest
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}