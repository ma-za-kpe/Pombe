package com.maku.pombe.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maku.pombe.data.models.latest.Latest
import com.maku.pombe.data.models.recent.Recent
import com.maku.pombe.utils.Constants.Companion.LATEST_COCKTAIL_TABLE
import com.maku.pombe.utils.Constants.Companion.RECENT_COCKTAIL_TABLE

@Entity(tableName = LATEST_COCKTAIL_TABLE)
class LatestCocktailsEntity(
    var latest: Latest
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}