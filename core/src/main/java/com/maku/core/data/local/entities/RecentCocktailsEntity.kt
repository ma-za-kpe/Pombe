package com.maku.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maku.core.domain.recent.Recent
import com.maku.core.utils.Constants.Companion.RECENT_COCKTAIL_TABLE

@Entity(tableName = RECENT_COCKTAIL_TABLE)
class RecentCocktailsEntity(
    var recent: Recent
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}