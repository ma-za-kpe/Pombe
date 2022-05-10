package com.maku.pombe.common.data.cache.model.cachedCategory

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryDbModel(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  @ColumnInfo(name = "name") val name: String
) {

  companion object {

    val DEFAULT_CATEGORIES = listOf(
      CategoryDbModel(1, "All"),
      CategoryDbModel(2, "Ordinary Drink"),
      CategoryDbModel(3, "Cocktail"),
      CategoryDbModel(4, "Shake"),
      CategoryDbModel(5, "Other/Unknown"),
      CategoryDbModel(6, "Cocoa"),
      CategoryDbModel(7, "Shot"),
      CategoryDbModel(8, "Coffee / Tea"),
      CategoryDbModel(9, "Homemade Liqueur"),
      CategoryDbModel(10, "Punch / Party Drink"),
      CategoryDbModel(11, "Beer"),
      CategoryDbModel(12, "Soft Drink"),
    )

    val DEFAULT_CATEGORY = DEFAULT_CATEGORIES[0]
  }
}
