package com.maku.pombe.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maku.pombe.data.models.recent.Recent
import com.maku.pombe.utils.Constants.Companion.RECENT_COCKTAIL_TABLE

@Entity(tableName = RECENT_COCKTAIL_TABLE)
class RecentCocktailsEntity(
    var recent: Recent
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}